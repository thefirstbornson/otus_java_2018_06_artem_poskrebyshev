package dbService;

import base.DBService;
import dao.UserDataSetDAO;
import datasets.DataSet;
import datasets.PhoneDataSet;
import datasets.UserDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;


public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {

        Configuration configuration = new Configuration()
                .configure(new File("HW111Hybernate/src/main/resources/hibernate.cfg.xml"));

        configuration.addAnnotatedClass(DataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public <T extends DataSet> void save(T user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save((UserDataSet)user);
            transaction.commit();
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            UserDataSet object = dao.load(id, (Class<UserDataSet>) clazz);
            return (T) object;
        }

    }

    public String getLocalStatus() {
        HibernateExecutor executor = new HibernateExecutor();
        return executor.getFromSession(sessionFactory
                                      ,session -> session.getTransaction().getStatus().name());
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }
}