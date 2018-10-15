package dbService;

import base.DBService;
import dao.UserDataSetDAO;
import datasets.DataSet;
import datasets.UserDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;


public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {

//        Configuration configuration = new Configuration();



//        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
//        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/users?allowPublicKeyRetrieval=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
//        configuration.setProperty("hibernate.connection.username", "dbuser");
//        configuration.setProperty("hibernate.connection.password", "T2Y42ZWG72y859N");
//        configuration.setProperty("hibernate.show_sql", "true");
//        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
//        configuration.setProperty("hibernate.connection.useSSL", "false");
//        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        Configuration configuration = new Configuration()
                .configure(new File("HW111Hybernate/src/main/resources/hibernate.cfg.xml"));

        configuration.addAnnotatedClass(DataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void save1(UserDataSet user) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save(user);
        }
    }

    @Override
    public <T extends DataSet> void save(T user) {
        System.out.println("implemented save");
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