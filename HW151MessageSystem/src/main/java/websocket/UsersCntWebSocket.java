package websocket;

import base.DBService;
import datasets.UserDataSet;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import servlets.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebSocket
public class UsersCntWebSocket {

    private static final String GETUSER_PAGE_TEMPLATE = "numusers.html";

    private  TemplateProcessor templateProcessor;
    private  DBService dbService;

//    public UsersCntWebSocket(TemplateProcessor templateProcessor, DBService dbService) {
//    }

//    public void doGet(HttpServletRequest request,
//                      HttpServletResponse response) throws ServletException, IOException {
//        doPost(request, response);
//    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        Integer usersNum = dbService.readAll(UserDataSet.class).size();
        String value = usersNum!=null?usersNum.toString():"not found";

        //String page = templateProcessor.getPage(GETUSER_PAGE_TEMPLATE, Map.of("userNum", value));//save to the page

//        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().println(page);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        System.out.println(session.getRemoteAddress().getHostString() + " closed!");
    }

}
