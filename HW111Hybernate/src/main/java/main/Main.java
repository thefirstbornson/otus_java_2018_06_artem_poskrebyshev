package main;

import datasets.PhoneDataSet;
import datasets.UserDataSet;
import dbService.DBServiceHibernateImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DBServiceHibernateImpl dbService = new DBServiceHibernateImpl();
        String status = ((DBServiceHibernateImpl) dbService).getLocalStatus();
        System.out.println("Status: " + status);
        UserDataSet usr = new UserDataSet("Ann", 45);
        List<PhoneDataSet> listPhone = new ArrayList<>();
        listPhone.add(new PhoneDataSet("+79062137717", usr));
        listPhone.add(new PhoneDataSet("+79062193069", usr));
        listPhone.add(new PhoneDataSet("+79062198556", usr));
        usr.setPhones(listPhone);
        dbService.save(usr);
        System.out.println(dbService.load(1, UserDataSet.class));
        dbService.close();
    }
}
