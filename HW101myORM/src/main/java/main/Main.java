package main;

import base.DBService;
import base.DBServiceImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        DBService dbService = new DBServiceImpl();
        System.out.println(dbService.getMetaData());
    }
}