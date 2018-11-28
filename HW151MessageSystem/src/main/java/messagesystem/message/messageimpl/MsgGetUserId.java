package messagesystem.message.messageimpl;

import datasets.DataSet;
import datasets.UserDataSet;
import dbCache.DBCache;
import dbService.DBService;
import messagesystem.Address;
import messagesystem.message.MsgToDB;

public class MsgGetUserId extends MsgToDB {
    private DBCache dbCache;
    private final String value;

    public MsgGetUserId(Address from, Address to, DBCache dbCache, String value) {
        super(from, to);
        this.dbCache = dbCache;
        this.value = value;
    }

    @Override
    public void exec(DBService dbService) {
        DataSet user = new UserDataSet();
        try {
            int id = Integer.parseInt(value);
            if (id > 0) {
                user = dbCache.get(id);
                if (user==null) {
                    user = dbService.load(id, UserDataSet.class);
                    if (user!=null) dbCache.put(id, user);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        dbService.getMS().sendMessage(new MsgGetUserIdAnswer(getTo(), getFrom(), user));
    }
}
