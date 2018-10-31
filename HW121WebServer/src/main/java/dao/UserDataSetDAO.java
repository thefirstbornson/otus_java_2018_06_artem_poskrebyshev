package dao;

import datasets.DataSet;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

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

    @Override
    public <T extends DataSet> List<T> readAll(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).list();
    }
}
