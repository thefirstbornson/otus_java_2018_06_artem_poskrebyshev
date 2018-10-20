package dao;

import datasets.DataSet;
import datasets.UserDataSet;
import org.hibernate.Session;

public class UserDataSetDAO implements DAO {
    private Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public UserDataSetDAO() {
    }

    @Override
    public <T extends DataSet> void save(T dataset) {
        session.save(dataset);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return session.load(clazz, id);
    }
}
