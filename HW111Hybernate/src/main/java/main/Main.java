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

        UserDataSet usr = new UserDataSet("Peter", 45);
        List<PhoneDataSet> listPhone = new ArrayList<>();
        listPhone.add(new PhoneDataSet("+79062137713", usr));
        listPhone.add(new PhoneDataSet("+79062193063", usr));
        listPhone.add(new PhoneDataSet("+79062198553", usr));
        usr.setPhones(listPhone);
        dbService.save(usr);
        System.out.println(dbService.load(1, UserDataSet.class));
        dbService.close();
    }
}
