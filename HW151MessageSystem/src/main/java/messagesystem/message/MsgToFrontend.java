package messagesystem.message;

import messagesystem.Address;
import messagesystem.Addressee;
import websocket.FrontendService;

public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {

        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        }
    }

    public abstract void exec(FrontendService frontendService);
}