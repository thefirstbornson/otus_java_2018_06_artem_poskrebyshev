package messagesystem;

import java.util.HashMap;
import java.util.Map;

public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address dbAddress;
    private Map<String, Address> frontAddressMap;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        this.frontAddressMap = new HashMap<>();
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress(String id) {
        return frontAddressMap.get(id);
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddressMap.put(frontAddress.getId(),frontAddress);
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }
}
