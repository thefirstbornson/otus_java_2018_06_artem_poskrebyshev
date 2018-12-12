package websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import frontsocket.ClientSocketMsgWorker;
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
        logger.log(Level.INFO, "Front socket " +socketGetUser);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    final String result = socketGetUser.take();
                    JsonObject object = new JsonParser().parse(result).getAsJsonObject();
                    String userJson = object.get("dbServiceMethod").getAsString();
                    sendResult(userJson);
                    logger.log(Level.INFO, "Message handled: " + userJson);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        logger.log(Level.INFO, session.getRemoteAddress().getHostString() + " connected!");
        setSession(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        logger.log(Level.INFO, session.getRemoteAddress().getHostString() + " closed!");
    }

    @OnWebSocketMessage
    public void handleRequest(String data) {
        logger.log(Level.INFO, "server get from front: " + data);
        Gson gson = new Gson();

        MsgJsonDBMethodWrapper msg =
                new MsgJsonDBMethodWrapper("load",new String[] {data ,"datasets.UserDataSet"}
                                          ,new String[] {"int","Class"} );
        String jsonmsg = gson.toJson(msg);
        logger.log(Level.INFO, "server send query to DB: " + data);
        socketGetUser.send(jsonmsg);
    }

    public <T> void sendResult(T user) {
        String resultMsg;
        try {
            resultMsg = user.equals("null")?"No such user here": (String)user;
            session.getRemote().sendString(resultMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, "server send to Front: "+user);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }


}
