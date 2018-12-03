package messagesystem.adressees.frontimpl;

import messagesystem.Address;
import messagesystem.MessageSystem;
import messagesystem.MessageSystemContext;
import messagesystem.adressees.FrontendService;
import messagesystem.message.Message;
import messagesystem.message.messageimpl.MsgGetUserId;

import java.util.HashMap;
import java.util.Map;

public class FrontendServiceGetUser implements FrontendService {

    private final Address address;
    private final MessageSystemContext context;

    private final Map<Integer, String> users = new HashMap<>();

    public FrontendServiceGetUser(MessageSystemContext context, Address address) {
        this.address = address;
        this.context = context;
    }

    @Override
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void handleRequest(String id) {
        Message message = new MsgGetUserId(getAddress(), context.getDbAddress(), id);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public <T> void sendResult(T message) {

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
