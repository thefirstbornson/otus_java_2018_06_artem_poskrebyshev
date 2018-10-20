package main;

import base.DBService;
import datasets.AddressDataSet;
import datasets.PhoneDataSet;
import datasets.UserDataSet;
import dbService.DBServiceHibernateImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBServiceHibernateImpl();

        UserDataSet usr = new UserDataSet("Peter", 45);
        List<PhoneDataSet> listPhone = new ArrayList<>();
        listPhone.add(new PhoneDataSet("+79062137713", usr));
        listPhone.add(new PhoneDataSet("+79062193063", usr));
        listPhone.add(new PhoneDataSet("+79062198553", usr));
        usr.setPhones(listPhone);
        usr.setAddress(new AddressDataSet("Mayskiy per",usr));

        dbService.save(usr);
        System.out.println(dbService.load(1, UserDataSet.class));
        dbService.close();
    }
}
