package dbService;

import base.DBService;
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
import java.lang.reflect.InvocationTargetException;


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

            String className= user.getClass().getName().substring(user.getClass().getName().lastIndexOf('.')+1);
            Object dao = Class.forName("dao."+className+"DAO").newInstance();
            dao.getClass().getMethod("setSession",Session.class).invoke(dao,session);
            dao.getClass().getMethod("save",user.getClass()).invoke(dao,user);

            transaction.commit();

        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        Object dataset=null;
        try (Session session = sessionFactory.openSession()) {

            String className= clazz.getName().substring(clazz.getName().lastIndexOf('.')+1);
            Object dao = Class.forName("dao."+className+"DAO").newInstance();
            dao.getClass().getMethod("setSession",Session.class).invoke(dao,session);
            dataset = dao.getClass().getMethod("load",long.class, Class.class).invoke(dao,id,clazz);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
        return (T) dataset;
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }
}