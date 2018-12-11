package dbMain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datasets.UserDataSet;
import dbService.DBCache;
import dbService.DBCacheInMemory;
import dbService.DBService;
import dbService.DBServiceHibernateImpl;
import dbsockets.DBSocketMsgWorker;
import gsonconverters.*;
import messagesystem.MsgJsonDBMethodWrapper;
import serversocket.SocketMsgWorker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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

//        SocketMsgWorker dbSocket = new DBSocketMsgWorker(HOST, SOCKET_PORT);
//        dbSocket.init();

        DBCache dbCache = new DBCacheInMemory(50, 500, 25);
        DBService dbService = new DBServiceHibernateImpl(dbCache);


       System.out.println(getDBServiseResult(dbService,"save",new String[]{"{\"name\":\"alex\",\"age\":\"1\",\"phones\":\"1234\",\"address\":\"asdfs\"}"}));


//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(() -> {
//            try {
//                while (true) {
//                    final String msg = dbSocket.take();
//
//                    Gson gson = new GsonBuilder()
//                                        .registerTypeAdapter(UserDataSet.class, new UserDataSetConverter())
//                                        .registerTypeAdapter(Class.class, new JavaLangClassConverter()).create();
//
//                    MsgJsonDBMethodWrapper fromJsonObj =gson.fromJson(msg, MsgJsonDBMethodWrapper.class);
//                    int addressTo = fromJsonObj.getAddressTo();
//                    int addressFrom = fromJsonObj.getAddressFrom();
//                    String dbServiceMethod = fromJsonObj.getDbServiceMethod();
//                    String[] dbServiceMethodParams = fromJsonObj.getDBServiceMethodParams();
//                    String result = getDBServiseResult(dbService,dbServiceMethod,dbServiceMethodParams);
//                    String json=gson.toJson(new MsgJsonDBMethodWrapper(addressFrom,addressTo, result, new String[]{}));
//                    dbSocket.send(json);
//
//                    System.out.println("Message handled: " + json);
//                }
//            } catch (InterruptedException e) {
//                logger.log(Level.SEVERE, e.getMessage());
//            }
//        });
//
//        dbSocket.send("initDB");

//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(UserDataSet.class, new UserDataSetConverter())
//                .registerTypeAdapter(Class.class, new JavaLangClassConverter()).create();
//        try {
//            gson.fromJson("{\"name\":\"alex\",\"age\":\"1\",\"phones\":\"1234\",\"address\":\"asdfs\"}",UserDataSet.class);
//            //gson.fromJson("{\"id\":\"1\"}", UserDataSet.class);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }

//
//        Method m = dbService.getClass().getMethod("load", long.class,Class.class);
//        //Class klazz = gson.fromJson(gson.toJson( "datasets.UserDataSet"), Class.class);
//        String[] s = new String[]{"{\"id\":\"1\"}","datasets.UserDataSet"};


    }

    private String getDBServiseResult (DBService dbService, String methodName, String[] methodsParams){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserDataSet.class, new UserDataSetConverter())
                .registerTypeAdapter(Class.class, new JavaLangClassConverter()).create();
        String json = null;
        Method m=null;
        for (Method mthd: dbService.getClass().getDeclaredMethods ()){
            if (mthd.getName().contains(methodName)){
                Object[] paramObj = new Object[mthd.getParameterCount()];
                int i=0;
                for (Parameter p: mthd.getParameters()){
                    paramObj[i]=gson.fromJson(methodsParams[i],p.getType());
                    i++;
                }
                try {
                    json = gson.toJson(mthd.invoke(dbService, paramObj));
                } catch (IllegalAccessException|InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return json;
    }
}