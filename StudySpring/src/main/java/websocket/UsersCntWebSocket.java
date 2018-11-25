package websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class UsersCntWebSocket {
    private Session session;

    private static final String GETUSER_PAGE_TEMPLATE = "numusers.html";

//    private  TemplateProcessor templateProcessor;
//    private  DBService dbService;

//    public UsersCntWebSocket(TemplateProcessor templateProcessor, DBService dbService) {
//        this.templateProcessor = templateProcessor;
//        this.dbService = dbService;
//    }

//    public void doGet(HttpServletRequest request,
//                      HttpServletResponse response) throws ServletException, IOException {
//        doPost(request, response);
//    }

//    public void doPost(HttpServletRequest request,
//                       HttpServletResponse response) throws ServletException, IOException {
//
//        Integer usersNum = dbService.readAll(UserDataSet.class).size();
//        String value = usersNum!=null?usersNum.toString():"not found";
//
//        //String page = templateProcessor.getPage(GETUSER_PAGE_TEMPLATE, Map.of("userNum", value));//save to the page
//
//        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().println(page);
//    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        System.out.println("server get: " + data);
        try {
            Thread.sleep(1000);
            session.getRemote().sendString("pong");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("server send: "+"pong");
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
        setSession(session);
//        this.templateProcessor = templateProcessor;
//        this.dbService = dbService;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        System.out.println(session.getRemoteAddress().getHostString() + " closed!");
    }

}
