package messagesystem.message.messageimpl;

import messagesystem.message.MsgToFrontend;
import websocket.FrontendService;

public class MsgGetDBAnswer extends MsgToFrontend {
    private Address dbAdress;
    public MsgGetDBAnswer(Address from, Address to, Address dbAdress) {
        super(from, to);
        this.dbAdress=dbAdress;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.handleRequest(dbAdress);
    }
}
