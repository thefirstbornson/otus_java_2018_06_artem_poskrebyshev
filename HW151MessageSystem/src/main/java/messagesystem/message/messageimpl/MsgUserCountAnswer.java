package messagesystem.message.messageimpl;

import messagesystem.Address;
import messagesystem.message.MsgToFrontend;
import websocket.FrontendService;

public class MsgUserCountAnswer extends MsgToFrontend {
    int count;
    public MsgUserCountAnswer(Address from, Address to, int count) {
        super(from, to);
        this.count = count;
    }

    @Override
    public void exec(FrontendService frontendService) {
        System.out.println("MsgUserCountAnswer - Start executing");
        frontendService.sendResult(count);
    }
}
