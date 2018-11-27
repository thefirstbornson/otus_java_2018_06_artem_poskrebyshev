package websocket;

import base.DBService;
import datasets.UserDataSet;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import servlets.TemplateProcessor;

import java.io.IOException;

@WebSocket
public class UsersCntWebSocket {

    private Session session;
    private  DBService dbService;

    public UsersCntWebSocket( DBService dbService) {
        this.dbService = dbService;
    }

    public UsersCntWebSocket() {
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        System.out.println("server get: " + data);
        Integer usersNum = dbService.readAll(UserDataSet.class).size();
        String value = usersNum!=null?usersNum.toString():"not found";

        try {
             session.getRemote().sendString(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server send: "+value);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
        setSession(session);
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
