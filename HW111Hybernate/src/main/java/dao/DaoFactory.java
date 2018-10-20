package dao;

import datasets.DataSet;
import org.hibernate.Session;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class DaoFactory {


    public static  <T extends DataSet>  DAO getDataSetDAO(Class<T> userClass, Session session) {
        DAO itemDAO=null;
        try{
            String className=userClass.getName().substring(userClass.getName().lastIndexOf('.')+1);
            Constructor kl =  Class.forName("dao." + className + "DAO").getConstructor(Session.class);
            itemDAO = kl.newInstance(session);
            //dao.getClass().getMethod("save",user.getClass()).invoke(dao,user);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException e) {
        e.printStackTrace();
        }
        return  itemDAO;
    }
}