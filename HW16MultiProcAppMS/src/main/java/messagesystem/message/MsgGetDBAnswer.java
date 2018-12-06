package messagesystem.message;

import messagesystem.Address;
import messagesystem.FrontendService;

public class MsgGetDBAnswer extends MsgToFrontend {
    Address db;

    public MsgGetDBAnswer(Address from, Address to, Address db) {
        super(from, to);
        this.db=db;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendResult(db);
    }
}
