package servlets;

import dbService.DBService;
import datasets.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class NumberOfUsersServlet extends HttpServlet {
    private static final String GETUSER_PAGE_TEMPLATE = "numusers.html";

    private final TemplateProcessor templateProcessor;
    private final DBService dbService;

    public NumberOfUsersServlet(TemplateProcessor templateProcessor, DBService dbService) {
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        Integer usersNum = dbService.readAll(UserDataSet.class).size();
        String value = usersNum!=null?usersNum.toString():"not found";

        String page = templateProcessor.getPage(GETUSER_PAGE_TEMPLATE, Map.of("userNum", value));//save to the page

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(page);
    }

}
