package dao;

import org.hibernate.Session;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class DaoFactory {

    public static  <T extends DAO> T getDataSetDAO(Class userClass, Session session) {
        T itemDAO=null;
        try{
            String className=userClass.getName().substring(userClass.getName().lastIndexOf('.')+1);
            Class c = Class.forName("dao." + className + "DAO");
            Constructor kl =  c.getConstructor(Session.class);
            itemDAO = (T) kl.newInstance(session);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException e) {
        e.printStackTrace();
        }
        return itemDAO;
    }
}