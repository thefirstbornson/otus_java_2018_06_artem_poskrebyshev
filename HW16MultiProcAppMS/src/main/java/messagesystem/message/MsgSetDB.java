package messagesystem.message;

import messagesystem.Address;
import messagesystem.MessageSystemContext;

public class MsgSetDB extends MsgToMS {

    public MsgSetDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(MessageSystemContext mscontext) {
        mscontext.setDbAddress(getTo());
    }

}
