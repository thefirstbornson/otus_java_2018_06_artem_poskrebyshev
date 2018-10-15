package main;

import datasets.UserDataSet;
import dbService.DBServiceHibernateImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        DBServiceHibernateImpl dbService = new DBServiceHibernateImpl();
        String status = ((DBServiceHibernateImpl) dbService).getLocalStatus();
        System.out.println("Status: " + status);
        UserDataSet usr = new UserDataSet("Ann", 45);
        dbService.save1(usr);
        System.out.println(dbService.load(3, UserDataSet.class));
        dbService.close();
    }
}
