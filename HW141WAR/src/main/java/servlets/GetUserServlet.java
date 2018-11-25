package servlets;

import base.DBService;
import datasets.DataSet;
import datasets.UserDataSet;
import dbCache.DBCache;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class GetUserServlet extends HttpServlet {

    private static final String GETUSER_PAGE_TEMPLATE = "getuser.html";


    private TemplateProcessor templateProcessor;
    private DBService dbService;
    private DBCache dbCache;

    public GetUserServlet(){}

    public GetUserServlet(TemplateProcessor templateProcessor, DBService dbService, DBCache dbCache) {
    }


    public void init(ServletConfig config) throws ServletException {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        templateProcessor = (TemplateProcessor) context.getBean("templateProcessor");
        dbService = (DBService) context.getBean("dbServiceHibernate");
        dbCache = (DBCache) context.getBean("dBCacheInMemory");
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
                if (id > 0) {
                    user = dbCache.get(id);
                    if (user==null) {
                        user = dbService.load(id, UserDataSet.class);
                        if (user!=null) dbCache.put(id, user);
                    }
                }
                value = user.toString();

            }
            catch (Exception e){
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
