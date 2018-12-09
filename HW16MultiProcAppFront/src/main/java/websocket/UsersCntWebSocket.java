package websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import messagesystem.Address;
import messagesystem.FrontendService;
import messagesystem.MessageSystemContext;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class UsersCntWebSocket implements FrontendService {
    private Session session;
    private Address address;
    private MessageSystemContext context;

    public UsersCntWebSocket(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
        init();
    }
    public UsersCntWebSocket() {
    }

    @Override
    @OnWebSocketMessage
    public <T> void handleRequest( T msg) {
        String data = (String) msg;

        System.out.println("server get: " + data);

//
//        Message message = new MsgUserCount(getSocket(), context.getDbSocket());
//        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public <T> void sendResult(T count) {
        GsonBuilder builder = new GsonBuilder();
 //       builder.registerTypeAdapter(UserDataSet.class, new UserDataSetConverter());
        Gson gson = builder.create();

        String value = count!=null?gson.toJson(count):gson.toJson("not found");
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

    @Override
    public void init() {
//        context.getMessageSystem().addAddressee(this);
//        context.getMessageSystem().addWorker(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Address getMSAddress() {
        return null;
    }

    @Override
    public void setMSAddress(Address address) {

    }


//    public MessageSystem getMS() {
//        return context.getMessageSystem();
//    }


}
