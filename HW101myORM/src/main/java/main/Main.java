package main;

import base.DBService;
import base.DBServiceImpl;
import base.UserDataSet;

public class Main {
    public static void main(String[] args){
        new Main().run();
    }

    private void run(){

        DBService dbservice = new DBServiceImpl();
        //create table if not exists user (id bigint(20) auto_increment, name varchar(255), age int(3), primary key (id))
        dbservice.save(new UserDataSet("John", 21));
        System.out.println(dbservice.load(1, UserDataSet.class).toString());

    }
}