package messagesystem.adressees.dbimpl;

import datasets.DataSet;
import datasets.UserDataSet;
import dbService.DBService;
import messagesystem.Address;
import messagesystem.MessageSystem;
import messagesystem.MessageSystemContext;
import messagesystem.adressees.DBServiceMS;

public class DbServiceMSimpl implements DBServiceMS {
    private final Address address;
    private final MessageSystemContext context;
    private final DBService dbService;

    public DbServiceMSimpl(MessageSystemContext context, Address address, DBService dbService) {
        this.context = context;
        this.address = address;
        this.dbService = dbService;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    @Override
    public DataSet getUser(long id) {
        return dbService.load(id, UserDataSet.class);
    }
}
