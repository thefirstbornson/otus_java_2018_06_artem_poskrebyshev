package messagesystem.message;

public class MsgToClient extends Message {
    public MsgToClient(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
     addressee.setMSAddress(getFrom());
    }
}
