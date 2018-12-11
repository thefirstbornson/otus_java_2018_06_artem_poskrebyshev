package messagesystem.message.messageimpl;

import datasets.DataSet;
import messagesystem.message.MsgToFrontend;
import websocket.FrontendService;

public class MsgAddUserAnswer extends MsgToFrontend {
    DataSet user;

    public MsgAddUserAnswer(Address from, Address to, DataSet user) {
        super(from, to);
        this.user = user;

    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendResult(user);
    }
}
