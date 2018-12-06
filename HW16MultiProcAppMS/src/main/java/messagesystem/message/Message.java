package messagesystem.message;

import messagesystem.Address;
import messagesystem.Addressee;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final Address from;
    private final Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);
}
