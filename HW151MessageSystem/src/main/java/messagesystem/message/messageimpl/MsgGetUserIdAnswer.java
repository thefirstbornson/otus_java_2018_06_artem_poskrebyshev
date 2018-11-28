package messagesystem.message.messageimpl;

import datasets.DataSet;
import datasets.UserDataSet;
import messagesystem.Address;
import messagesystem.message.MsgToFrontend;
import org.eclipse.jetty.server.Authentication;
import websocket.FrontendService;

public class MsgGetUserIdAnswer extends MsgToFrontend {
    DataSet user;

    public MsgGetUserIdAnswer(Address from, Address to, DataSet user) {
        super(from, to);
        this.user = user;

    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendMessage(user);
    }
}
