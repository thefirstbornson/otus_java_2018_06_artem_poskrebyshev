package dbMain;

import client.EngineDBSocket;



public class dbMain {

    public static void main(String[] args) throws Exception {
        new EngineDBSocket().start();
    }
}