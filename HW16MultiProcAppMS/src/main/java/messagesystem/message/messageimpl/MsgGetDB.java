package messagesystem.message.messageimpl;

import messagesystem.message.OneWayMessage;

public class MsgGetDB extends OneWayMessage {

    public MsgGetDB(Address address) {
        super(address);
    }

    @Override
    public void exec(Addressee addressee) {
       // sendmessage new MsgGetDBAnswer(); положить в исходящую очередь сокета с id adressee
    }
}
