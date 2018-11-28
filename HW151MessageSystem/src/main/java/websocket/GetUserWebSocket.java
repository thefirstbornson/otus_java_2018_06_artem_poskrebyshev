package websocket;

import dbService.DBService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import datasets.DataSet;
import datasets.UserDataSet;
import dbCache.DBCache;
import gsonconverters.UserDataSetConverter;
import messagesystem.Address;
import messagesystem.MessageSystem;
import messagesystem.message.Message;
import messagesystem.message.MessageSystemContext;
import messagesystem.message.messageimpl.MsgGetUserId;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class GetUserWebSocket implements FrontendService {
    private Session session;
    private Address address;
    private MessageSystemContext context;
    private final DBService dbService;
    private final DBCache dbCache;

    public GetUserWebSocket(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
        this.dbService = dbService;
        this.dbCache = dbCache;
    }

    public GetUserWebSocket() {
    }

    @OnWebSocketMessage
    public void onMessage(String data) {

        System.out.println("server get: " + data);
        JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
        String value=value = jsonObject.get("id").getAsString();

        Message message = new MsgGetUserId(getAddress(), context.getDbAddress(), value);
        context.getMessageSystem().sendMessage(message);

//            DataSet user = new UserDataSet();
//            try {
//                int id = Integer.parseInt(value);
//                if (id > 0) {
//                    user = dbCache.get(id);
//                    if (user==null) {
//                        user = dbService.load(id, UserDataSet.class);
//                        if (user!=null) dbCache.put(id, user);
//                    }
//                }
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
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
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void handleRequest(String login) {
    }

    @Override
    public <T> void sendMessage(T user) {
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

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
