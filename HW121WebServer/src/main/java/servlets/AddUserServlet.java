package servlets;

import base.DBService;
import datasets.DataSet;
import datasets.DataSetHelper;
import datasets.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

public class AddUserServlet extends HttpServlet {

    private static final String ADDUSER_PAGE_TEMPLATE = "adduser.html";

    private final TemplateProcessor templateProcessor;
    private final DBService dbService;

    public AddUserServlet(TemplateProcessor templateProcessor, DBService dbService) {
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String value="";
        if (request.getParameterNames().hasMoreElements()) {
            DataSet user=new UserDataSet();
            for (Field field : user.getClass().getDeclaredFields()) {
                DataSetHelper.setDataSetField(user,field.getName(),request.getParameter(field.getName()));
            }
            dbService.save(user);
            value = user.toString();
        }

        String page = templateProcessor.getPage(ADDUSER_PAGE_TEMPLATE, Map.of("user",value));//save to the page

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(page);
    }
}
