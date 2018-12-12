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
public class UsersCntWebSocket {
    private static final Logger logger = Logger.getLogger(GetUserWebSocket.class.getName());
    private Session session;
    SocketMsgWorker socketUsersCnt;
    public static final String HOST = "localhost";
    public static final int SOCKET_PORT = 5050;

    public UsersCntWebSocket() {
        try {
            startUsersCntSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startUsersCntSocket() throws IOException {
        socketUsersCnt = new ClientSocketMsgWorker(HOST, SOCKET_PORT);
        socketUsersCnt.init();
        logger.log(Level.INFO, "Front socket " +socketUsersCnt);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    final String result = socketUsersCnt.take();
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

    @OnWebSocketMessage
    public void handleRequest( String data) {
        logger.log(Level.INFO, "server get from front: " + data);

        Gson gson = new Gson();
        MsgJsonDBMethodWrapper msgJsnWrp = new MsgJsonDBMethodWrapper("numberOfUsers"
                                                                        ,new String[]{"datasets.UserDataSet"}
                                                                        ,new String[]{"Class"});
        String jsonmsg = gson.toJson(msgJsnWrp);
        socketUsersCnt.send(jsonmsg);
    }


    public <T> void sendResult(T count) {

        try {
            session.getRemote().sendString((String)count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, "server send to front: "+count);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        logger.log(Level.INFO, session.getRemoteAddress().getHostString() + " connected!");
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
        logger.log(Level.INFO, session.getRemoteAddress().getHostString() + " closed!");
    }
}
