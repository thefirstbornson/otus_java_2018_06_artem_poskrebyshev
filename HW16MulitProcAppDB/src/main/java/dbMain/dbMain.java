package dbMain;

import dbService.DBCache;
import dbService.DBCacheInMemory;
import dbService.DBService;
import dbService.DBServiceHibernateImpl;
import dbsockets.DBSocketMsgWorker;
import messagesystem.Address;
import serversocket.SocketMsgWorker;


public class dbMain {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "/public_html";
    private static final String HOST = "localhost";
    private static final int SOCKET_PORT = 5050;

    public static void main(String[] args)  throws Exception {
        new dbMain().start();
    }

    private void start()  throws Exception {
        SocketMsgWorker  db = new DBSocketMsgWorker(HOST, SOCKET_PORT);
        db.init();

        Address dbSocket = new Address("DB");
        DBCache dbCache = new DBCacheInMemory(50, 500, 25);
        DBService dbService = new DBServiceHibernateImpl(dbSocket, dbCache);
//        FrontendContext frontendContext = new FrontendContext(client,clientSocket);
       // db.send(new MsgSetDB(dbService.getSocket(),dbService.getSocket()));
    }
}
