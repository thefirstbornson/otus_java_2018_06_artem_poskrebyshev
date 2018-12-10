package dbMain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datasets.UserDataSet;
import dbService.DBCache;
import dbService.DBCacheInMemory;
import dbService.DBService;
import dbService.DBServiceHibernateImpl;
import gsonconverters.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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

//        Class act=null;
//        try {
//            act = Class.forName("datasets.UserDataSet");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(UserDataSet.class, new UserDataSetConverter());
        builder.registerTypeAdapter(Class.class, new JavaLangClassConverter());
        Gson gson = builder.create();

//        SocketMsgWorker dbSocket = new DBSocketMsgWorker(HOST, SOCKET_PORT);
//        dbSocket.init();
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(() -> {
//            try {
//                while (true) {
//                    final String msg = dbSocket.take();
//
//                    final Gson gson = new Gson();
//                    JsonMsgTest fromJsonObj =gson.fromJson(msg, JsonMsgTest.class);
//                    int addressTo = fromJsonObj.getAddressTo();
//                    int addressFrom = fromJsonObj.getAddressFrom();
//                    String messageFromJsonObj = fromJsonObj.getMessage();
//
//                    String json = gson.toJson(new JsonMsgTest(addressFrom,addressTo, "helloFromDB"));
//                    dbSocket.send(json);
//
//                    System.out.println("Message handled: " + json);
//                }
//            } catch (InterruptedException e) {
//                logger.log(Level.SEVERE, e.getMessage());
//            }
//        });


        DBCache dbCache = new DBCacheInMemory(50, 500, 25);
        DBService dbService = new DBServiceHibernateImpl(dbCache);


       // dbSocket.send("initDB");

        Method m = dbService.getClass().getMethod("load", long.class,Class.class);
        Class klazz = gson.fromJson(gson.toJson( "UserDataSet"), Class.class);
        String[] s = new String[]{"1","UserDataSet"};

        for (Method mthd: dbService.getClass().getMethods()){
            if (mthd.getName().contains("load")){
                Object[] paramObj = new Object[mthd.getParameterCount()];
                int i=0;
                for (Parameter p: mthd.getParameters()){
                    paramObj[i]=gson.fromJson(s[i],p.getType());
                    i++;
                    System.out.println(p.getName()+"-"+p.getType());
                }
                String json = gson.toJson(m.invoke(dbService, paramObj));
                System.out.println(json);
                break;
            }
        }



    }
}
