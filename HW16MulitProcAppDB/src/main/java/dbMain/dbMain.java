package dbMain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dbService.DBCache;
import dbService.DBCacheInMemory;
import dbService.DBService;
import dbService.DBServiceHibernateImpl;
import dbsockets.DBSocketMsgWorker;
import messagesystem.JsonMsgTest;
import serversocket.SocketMsgWorker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class dbMain {
    private static final Logger logger = Logger.getLogger(dbMain.class.getName());
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "/public_html";
    private static final String HOST = "localhost";
    private static final int SOCKET_PORT = 5050;

    public static void main(String[] args)  throws Exception {
        new dbMain().start();
    }

    private void start()  throws Exception {
        GsonBuilder builder = new GsonBuilder();
        // Gson gson builder.create();

        SocketMsgWorker dbSocket = new DBSocketMsgWorker(HOST, SOCKET_PORT);
        dbSocket.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    final String msg = dbSocket.take();

                    final Gson gson = new Gson();
                    JsonMsgTest fromJsonObj =gson.fromJson(msg, JsonMsgTest.class);
                    int addressTo = fromJsonObj.getAddressTo();
                    int addressFrom = fromJsonObj.getAddressFrom();
                    String messageFromJsonObj = fromJsonObj.getMessage();

                    String json = gson.toJson(new JsonMsgTest(addressFrom,addressTo, "helloFromDB"));
                    dbSocket.send(json);

                    System.out.println("Message handled: " + json);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });


        DBCache dbCache = new DBCacheInMemory(50, 500, 25);
        DBService dbService = new DBServiceHibernateImpl(dbCache);
        dbSocket.send("initDB");

    }
}
