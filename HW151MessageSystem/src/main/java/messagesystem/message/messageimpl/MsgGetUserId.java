package messagesystem.message.messageimpl;

import datasets.DataSet;
import datasets.UserDataSet;
import messagesystem.Address;
import messagesystem.adressees.DBServiceMS;
import messagesystem.message.MsgToDB;

public class MsgGetUserId extends MsgToDB {
    private final String value;

    public MsgGetUserId(Address from, Address to, String value) {
        super(from, to);
        this.value = value;
    }

    @Override
    public void exec(DBServiceMS dbServiceMS) {
        DataSet user = new UserDataSet();
        try {
            int id = Integer.parseInt(value);
            if (id > 0) {
                    user = dbServiceMS.getUser(id);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        dbServiceMS.getMS().sendMessage(new MsgGetUserIdAnswer(getTo(), getFrom(), user));
    }
}
