package websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class AddUserWebSocket    {
    private Session session;;

    public AddUserWebSocket() {


    }



    @OnWebSocketMessage
    public <T> void handleRequest( T msg) {
        String data = (String) msg;

        System.out.println("server get: " + data);

//        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(UserDataSet.class, new UserDataSetConverter());
//        Gson gson = builder.create();
//        DataSet user = new UserDataSet();
//        try {
//             user = gson.fromJson(data, UserDataSet.class);
//        } catch ( JsonSyntaxException e) {
//            e.printStackTrace();
//        }

//            Message message = new MsgAddUser(getSocket(), context.getDbSocket(), user);
//            context.getMessageSystem().sendMessage(message);
    }


    public <T> void sendResult(T user) {
        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(UserDataSet.class, new UserDataSetConverter());
        Gson gson = builder.create();

        String value = user!=null?gson.toJson(user):gson.toJson("not found");
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
