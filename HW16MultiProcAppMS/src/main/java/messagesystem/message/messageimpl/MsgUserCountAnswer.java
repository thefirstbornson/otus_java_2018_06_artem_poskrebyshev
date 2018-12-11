package messagesystem.message.messageimpl;

import messagesystem.message.MsgToFrontend;
import websocket.FrontendService;

public class MsgUserCountAnswer extends MsgToFrontend {
    private int count;
    public MsgUserCountAnswer(Address from, Address to, int count) {
        super(from, to);
        this.count = count;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendResult(count);
    }
}
