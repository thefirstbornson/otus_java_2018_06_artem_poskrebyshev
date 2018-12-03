package messagesystem.message.messageimpl;

import datasets.UserDataSet;
import dbService.DBService;
import messagesystem.Address;
import messagesystem.message.MsgToDB;

public class MsgUserCount extends MsgToDB {

    public MsgUserCount(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(DBService dbService) {
        int count = dbService.readAll(UserDataSet.class).size();
        dbService.getMS().sendMessage(new MsgUserCountAnswer(getTo(), getFrom(), count));
    }
}
