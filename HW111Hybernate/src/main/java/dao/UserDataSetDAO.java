package dao;

import datasets.UserDataSet;
import org.hibernate.Session;

public class UserDataSetDAO {
    private Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public UserDataSetDAO() {
    }

    public void save(UserDataSet dataSet) {
        session.save(dataSet);
    }

    public UserDataSet load(long id, Class<UserDataSet> clazz) {
        return session.load(clazz, id);
    }
}
