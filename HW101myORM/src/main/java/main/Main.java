package main;

import base.DBService;
import base.DBServiceImpl;
import base.UserDataSet;

public class Main {
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {

        DBService dbservice = new DBServiceImpl();
        dbservice.save(new UserDataSet("John", 21));
        System.out.println(dbservice.load(1, UserDataSet.class).toString());

    }
}