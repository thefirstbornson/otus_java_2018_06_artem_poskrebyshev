package messagesystem.message.messageimpl;

import datasets.DataSet;
import datasets.UserDataSet;
import dbService.DBCache;
import dbService.DBService;
import messagesystem.Address;
import messagesystem.message.MsgToDB;

public class MsgGetUserId extends MsgToDB {
    private final String value;

    public MsgGetUserId(Address from, Address to, String value) {
        super(from, to);
        this.value = value;
    }

    @Override
    public void exec(DBService dbService) {
        DataSet user = new UserDataSet();
        try {
            int id = Integer.parseInt(value);
            if (id > 0) {
                    user = dbService.load(id, UserDataSet.class);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        dbService.getMS().sendMessage(new MsgGetUserIdAnswer(getTo(), getFrom(), user));
    }
}
