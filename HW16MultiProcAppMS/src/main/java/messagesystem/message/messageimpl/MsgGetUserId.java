package messagesystem.message.messageimpl;

import datasets.DataSet;
import datasets.UserDataSet;
import dbService.DBService;
import messagesystem.message.MsgToDB;

public class MsgGetUserId extends MsgToDB {
    private final int userId;

    public MsgGetUserId(Address from, Address to, int userId) {
        super(from, to);
        this.userId = userId;
    }

    @Override
    public void exec(DBService dbService) {
        DataSet user = new UserDataSet();
        if (userId > 0)  user = dbService.load(userId, UserDataSet.class);
        dbService.getMS().sendMessage(new MsgGetUserIdAnswer(getTo(), getFrom(), user));
    }


}
