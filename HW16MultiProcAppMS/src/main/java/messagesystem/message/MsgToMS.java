package messagesystem.message;

public abstract class MsgToMS extends Message {
    public MsgToMS(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof MessageSystemContext) {
            exec((MessageSystemContext) addressee);
        }
    }

    public abstract void exec(MessageSystemContext mscontext);
}