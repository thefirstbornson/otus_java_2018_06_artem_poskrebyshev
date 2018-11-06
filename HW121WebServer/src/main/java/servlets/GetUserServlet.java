package servlets;

import base.DBService;
import datasets.DataSet;
import datasets.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class GetUserServlet extends HttpServlet {

    private static final String GETUSER_PAGE_TEMPLATE = "getuser.html";


    private final TemplateProcessor templateProcessor;
    private final DBService dbService;

    public GetUserServlet(TemplateProcessor templateProcessor, DBService dbService) {
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
            DataSet user = new UserDataSet();
            try {
                int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;
                if (id > 0) user = dbService.load(id, UserDataSet.class);
                value = user.toString();
            } catch (Exception e){
                e.printStackTrace();
                value = "not found";
            }
        }

        String page = templateProcessor.getPage(GETUSER_PAGE_TEMPLATE, Map.of("user", value));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(page);
    }
}
