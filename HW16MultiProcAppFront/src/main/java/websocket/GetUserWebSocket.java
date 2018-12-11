package websocket;

import com.google.gson.*;
import frontsocket.ClientSocketMsgWorker;
import messagesystem.Message;
import messagesystem.MsgJson;
import messagesystem.MsgJsonDBMethodWrapper;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import serversocket.SocketMsgWorker;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebSocket
public class GetUserWebSocket  {
    private static final Logger logger = Logger.getLogger(GetUserWebSocket.class.getName());
    private Session session;
    SocketMsgWorker socketGetUser;
    public static final String HOST = "localhost";
    public static final int SOCKET_PORT = 5050;


    public GetUserWebSocket()  {
        try {
            startGetUserSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startGetUserSocket () throws IOException {
        socketGetUser = new ClientSocketMsgWorker(HOST, SOCKET_PORT);
        socketGetUser.init();
        System.out.println("Front socket " +socketGetUser);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    final String result = socketGetUser.take();
                    JsonObject object = new JsonParser().parse(result).getAsJsonObject();
                    String userJson = object.get("dbServiceMethod").getAsString();
                    sendResult(userJson);
                    System.out.println("Message handled: " + userJson);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
        setSession(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        System.out.println(session.getRemoteAddress().getHostString() + " closed!");
    }

    @OnWebSocketMessage
    public void handleRequest(String data) {
        System.out.println("server get: " + data);
        Gson gson = new Gson();

        MsgJsonDBMethodWrapper msg = new MsgJsonDBMethodWrapper("load",data,"datasets.UserDataSet");
        String jsonmsg = gson.toJson(msg);
        socketGetUser.send(jsonmsg);
    }

    public <T> void sendResult(T user) {
        try {
            session.getRemote().sendString((String)user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server send: "+user);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }


}
