package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datasets.UserDataSet;
import dbMain.dbMain;
import dbService.DBCache;
import dbService.DBCacheInMemory;
import dbService.DBService;
import dbService.DBServiceHibernateImpl;
import gsonconverters.JavaLangClassConverter;
import gsonconverters.UserDataSetConverter;
import messagesystem.MsgJsonDBAnswer;
import messagesystem.MsgJsonDBMethodWrapper;
import org.apache.commons.lang3.ClassUtils;
import serversocket.ClientSocketMsgWorker;
import serversocket.SocketMsgWorker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EngineDBSocket {
    private static final Logger logger = Logger.getLogger(dbMain.class.getName());
    private static final String HOST = "localhost";
    private static final int SOCKET_PORT = 5050;
    private SocketMsgWorker dbSocket;
    private DBCache dbCache;
    private DBService dbService;

    public EngineDBSocket() throws Exception {
        dbSocket = new ClientSocketMsgWorker(HOST, SOCKET_PORT);
        dbSocket.init();

        dbCache = new DBCacheInMemory(50, 500, 25);
        dbService = new DBServiceHibernateImpl(dbCache);
    }

    public void start(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    final String msg = dbSocket.take();
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(UserDataSet.class, new UserDataSetConverter())
                            .registerTypeAdapter(Class.class, new JavaLangClassConverter()).create();

                    MsgJsonDBMethodWrapper fromJsonObj =gson.fromJson(msg, MsgJsonDBMethodWrapper.class);
                    String result = getDBServiseResult(dbService
                            ,fromJsonObj.getDbServiceMethod()
                            ,fromJsonObj.getDbServiceMethodParams()
                            ,fromJsonObj.getDbServiceMethodParamTypes());
                    String json=gson.toJson(new MsgJsonDBAnswer(fromJsonObj.getAddressFrom()
                            ,fromJsonObj.getAddressTo()
                            ,result));
                    logger.log(Level.INFO, "Message handled: " + json);
                    dbSocket.send(json);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });

        dbSocket.send("initDB");
    }

    private String getDBServiseResult (DBService dbService, String methodName
            ,String[] methodsParams, String[] typesOfMethodParams){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserDataSet.class, new UserDataSetConverter())
                .registerTypeAdapter(Class.class, new JavaLangClassConverter()).create();

        Object[] paramObj = new Object[typesOfMethodParams.length];
        Class[]  typesObj = new Class[typesOfMethodParams.length];

        for (int i=0;i<typesOfMethodParams.length;i++){
            try {
                typesObj[i]= typesOfMethodParams[i].toUpperCase().contains("CLASS")
                        ? java.lang.Class.class
                        : ClassUtils.getClass(typesOfMethodParams[i])    ;
                paramObj[i]=gson.fromJson(methodsParams[i],typesObj[i]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        String json = "";
        for (Method mthd: dbService.getClass().getDeclaredMethods ()){
            if (mthd.getName().contains(methodName)){
                try {
                    json = gson.toJson(mthd.invoke(dbService, paramObj));
                } catch (IllegalAccessException| InvocationTargetException e) {
                    json=null;
                    e.printStackTrace();
                }
                break;
            }
        }
        return json;
    }
}
