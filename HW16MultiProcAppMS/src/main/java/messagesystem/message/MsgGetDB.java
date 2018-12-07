package messagesystem.message;

import messagesystem.Address;
import messagesystem.MessageSystemContext;

public class MsgGetDB extends MsgToMS {
    public MsgGetDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(MessageSystemContext mscontext) {
//        mscontext.getMsContextworker().send(new MsgGetDBAnswer(getTo()
//                                                ,getFrom(),mscontext.getDbAddress()));
    }

}
