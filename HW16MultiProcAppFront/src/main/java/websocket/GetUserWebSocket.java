package websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import datasets.UserDataSet;
import fcontext.FrontendContext;
import frontsocket.ClientSocketMsgWorker;
import gsonconverters.UserDataSetConverter;
import messagesystem.Address;
import messagesystem.FrontendService;
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
public class GetUserWebSocket implements FrontendService {
    private static final Logger logger = Logger.getLogger(GetUserWebSocket.class.getName());
    private Session session;
//    private Address address;
    private FrontendContext frontendContext;
    SocketMsgWorker socketGetUser;


    public GetUserWebSocket(FrontendContext frontendContext)  {
        this.frontendContext = frontendContext;
        System.out.println("Getuserwebsocket created");
//        try {
//            startGetUserSocket();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public GetUserWebSocket() {

    }

    private void startGetUserSocket () throws IOException {
        socketGetUser = new ClientSocketMsgWorker(frontendContext.HOST, frontendContext.SOCKET_PORT);
        socketGetUser.init();
        System.out.println("Front socket " +socketGetUser);


        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    final Object msg = socketGetUser.take();
                    System.out.println("Message received: " + msg);
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

    @Override
    public void init() {
//        context.getMessageSystem().addAddressee(this);
//        context.getMessageSystem().addWorker(this);
    }

    @Override
    public Address getAddress() {
        return null;
    }

    @Override
    public Address getMSAddress() {
        return null;
    }

    @Override
    public void setMSAddress(Address address) {

    }

    @Override
    @OnWebSocketMessage
    public <T> void handleRequest( T msg) {
        String data = (String) msg;
        System.out.println("server get: " + data);
        JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
        int value=0;
        try {
            value= Integer.parseInt(jsonObject.get("id").getAsString());
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

 //       Message message = new MsgGetUserId(getSocket(), context.getDbSocket(), value);
      //  socketGetUser.send("hello!");
    }

    @Override
    public <T> void sendResult(T user) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(UserDataSet.class, new UserDataSetConverter());
        Gson gson = builder.create();

        String value = user!=null?gson.toJson(user):gson.toJson("not found");
        try {
            session.getRemote().sendString(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server send: "+value);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }


}
