package messagesystem.message.messageimpl;

import datasets.DataSet;
import dbService.DBService;
import messagesystem.Address;
import messagesystem.message.MsgToDB;

public class MsgAddUser extends MsgToDB {
    private final DataSet dataSet;
    public MsgAddUser(Address from, Address to,DataSet dataSet) {
        super(from, to);
        this.dataSet = dataSet;
    }

    @Override
    public void exec(DBService dbService) {
        dbService.save(dataSet);
        dbService.getMS().sendMessage(new MsgAddUserAnswer(getTo(), getFrom(), dataSet));
    }
}
