package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    public static final String USER_PARAMETER_NAME = "addname";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";


    private final TemplateProcessor templateProcessor;

    @SuppressWarnings("WeakerAccess")
    public AdminServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String requestUser = request.getParameter(USER_PARAMETER_NAME);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String page = getPage(requestUser); //save to the page
        response.getWriter().println(page);
    }

    private String getPage(String user) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("user", user == null ? "" : user);
        return templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables);
    }


}
