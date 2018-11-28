package websocket;

import dbService.DBService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import datasets.UserDataSet;
import gsonconverters.UserDataSetConverter;
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

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(UserDataSet.class, new UserDataSetConverter());
        Gson gson = builder.create();
        try {
            UserDataSet user = gson.fromJson(data, UserDataSet.class);
            dbService.save(user);
            String value = user!=null?gson.toJson(user):"not found";
            session.getRemote().sendString(value);
            System.out.println("server send: "+value);
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

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
