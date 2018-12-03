package messagesystem.adressees;

import datasets.DataSet;

public interface DBServiceMS extends Addressee {
    void init();
    <T extends DataSet> T getUser(long id);
}
