package main;

import base.DBService;
import base.DBServiceImpl;
import base.DataSet;
import base.UserDataSet;

public class Main {
    public static void main(String[] args){
        new Main().run();
    }

    private void run(){

        try(DBService dbservice = new DBServiceImpl()) {
            //create table if not exists user (id bigint(20) auto_increment, name varchar(255), age int(3), primary key (id))
            DataSet usr = new UserDataSet("John", 21);
            dbservice.save(usr);
            //System.out.println(dbservice.load(1, UserDataSet.class).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}