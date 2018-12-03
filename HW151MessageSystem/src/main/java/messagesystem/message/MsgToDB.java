package messagesystem.message;

import dbService.DBService;
import messagesystem.Address;
import messagesystem.adressees.Addressee;
import messagesystem.adressees.DBServiceMS;

public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBServiceMS) addressee);
        }
    }

    public abstract void exec(DBServiceMS dbServiceMS);
}
