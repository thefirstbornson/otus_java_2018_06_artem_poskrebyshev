package websocket;

import base.DBService;
import datasets.UserDataSet;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import servlets.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebSocket
public class UsersCntWebSocket {

    private static final String GETUSER_PAGE_TEMPLATE = "numusers.html";

    private final TemplateProcessor templateProcessor;
    private final DBService dbService;

    public UsersCntWebSocket(TemplateProcessor templateProcessor, DBService dbService) {
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

//    public void doGet(HttpServletRequest request,
//                      HttpServletResponse response) throws ServletException, IOException {
//        doPost(request, response);
//    }
//
//    public void doPost(HttpServletRequest request,
//                       HttpServletResponse response) throws ServletException, IOException {
//
//        Integer usersNum = dbService.readAll(UserDataSet.class).size();
//        String value = usersNum!=null?usersNum.toString():"not found";
//
//        String page = templateProcessor.getPage(GETUSER_PAGE_TEMPLATE, Map.of("userNum", value));//save to the page
//
//        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().println(page);
//    }
}
