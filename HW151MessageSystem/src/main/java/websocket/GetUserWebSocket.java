package websocket;

import base.DBService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import datasets.DataSet;
import datasets.UserDataSet;
import dbCache.DBCache;
import gsonconverters.UserDataSetConverter;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class GetUserWebSocket {
    private Session session;
    private DBService dbService;
    private DBCache dbCache;

    public GetUserWebSocket(DBService dbService, DBCache dbCache) {
        this.dbService = dbService;
        this.dbCache = dbCache;
    }

    public GetUserWebSocket() {
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        String value="";
        System.out.println("server get: " + data);

        JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
        value = jsonObject.get("id").getAsString();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(UserDataSet.class, new UserDataSetConverter());
        Gson gson = builder.create();

            DataSet user = new UserDataSet();
            try {
                int id = data != null ? Integer.parseInt(value) : 0;
                if (id > 0) {
                    user = dbCache.get(id);
                    if (user==null) {
                        user = dbService.load(id, UserDataSet.class);
                        if (user!=null) dbCache.put(id, user);
                    }
                }
                value = user!=null?gson.toJson(user):gson.toJson("not found");
                session.getRemote().sendString(value);
                System.out.println("server send: "+value);
                value = user.toString();
            }
            catch (Exception e){
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
