package messagesystem.message;

public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
//        if (addressee instanceof DBService) {
//            exec((DBService) addressee);
//        }
    }

    //public abstract void exec(DBService dbService);
}
