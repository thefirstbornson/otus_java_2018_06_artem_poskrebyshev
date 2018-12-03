package messagesystem.message.messageimpl;

import datasets.DataSet;
import messagesystem.Address;
import messagesystem.message.MsgToFrontend;
import websocket.FrontendService;

public class MsgGetUserIdAnswer extends MsgToFrontend {
    DataSet user;

    public MsgGetUserIdAnswer(Address from, Address to, DataSet user) {
        super(from, to);
        this.user = user;

    }

    @Override
    public void exec(FrontendService frontendService) {
        System.out.println("MsgGetUserIdAnswer - Start executing");
//        System.out.println("MsgGetUserIdAnswer - Sending from MS: " + user.toString());
        frontendService.sendResult(user);

    }
}
