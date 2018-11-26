package websocket;

import base.DBService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import datasets.UserDataSet;
import dbCache.DBCache;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class AddUserWebSocket {
    private Session session;
    DBService dbService;

    public AddUserWebSocket(DBService dbService) {
        this.dbService = dbService;
    }


    @OnWebSocketMessage
    public void onMessage(String data) {
        System.out.println("server get: " + data);
        Gson gson = new Gson();
        try {
            UserDataSet userDataSet = gson.fromJson(data, UserDataSet.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
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
