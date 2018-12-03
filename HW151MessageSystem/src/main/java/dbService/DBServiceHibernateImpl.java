package dbService;

import dao.DAO;
import dao.DaoFactory;
import datasets.AddressDataSet;
import datasets.DataSet;
import datasets.PhoneDataSet;
import datasets.UserDataSet;
import messagesystem.Address;
import messagesystem.MessageSystem;
import messagesystem.message.Message;
import messagesystem.message.MessageSystemContext;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;
    private final Address address;
    private final MessageSystemContext context;
    private final DBCache dbCache;

    public DBServiceHibernateImpl(MessageSystemContext context, Address address, DBCache dbCache) {
        this.context = context;
        this.address = address;
        this.dbCache = dbCache;
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        configuration.addAnnotatedClass(DataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void init() {
        context.getMessageSystem().addAddressee(this);

        Thread thread = new Thread(() -> {
            LinkedBlockingQueue<Message> queue = context.getMessageSystem().getMessagesMap().get(this.getAddress());
            while (true) {
                try {
                    Message message = queue.take();
                    System.out.println("queue --" + message.getFrom().toString() + "-" +message.getTo().toString());
                    message.exec(this);
                } catch (InterruptedException e) {
                    // logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
                    return;
                }
            }
        });
        thread.start();
    }

    @Override
    public <T extends DataSet> void save(T user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            DAO dao = DaoFactory.getDataSetDAO(user.getClass(), session);
            dao.save(user);
            transaction.commit();
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        try (Session session = sessionFactory.openSession()) {
            DAO dao = DaoFactory.getDataSetDAO(clazz, session);
            T dataset = dbCache.get(id);
            if (dataset==null) {
                dataset = (T) Hibernate.unproxy(dao.load(id, clazz));
                if (dataset!=null) dbCache.put(id, dataset);
            }
            return dataset;
        } catch (ObjectNotFoundException e){
            return null;
        }
    }

    @Override
    public <T extends DataSet> List<T> readAll(Class<T> clazz) {
        try (Session session = sessionFactory.openSession()) {
            DAO dao = DaoFactory.getDataSetDAO(clazz, session);
            return dao.readAll(clazz);
        }
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}