package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import messagesystem.MsgJson;
import messagesystem.Message;
import messagesystem.MsgJsonDBMethodWrapper;
import serversocket.MsgWorker;
import serversocket.SocketMsgWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class EchoSocketMsgServer {
    private static final Logger logger = Logger.getLogger(EchoSocketMsgServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;
    private int db_id;
    private int id = 0;

    private final ExecutorService executor;
    private final Map<Integer, MsgWorker> addresseeMap;
    private Gson gson;

    public EchoSocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        addresseeMap = new ConcurrentHashMap<>();
        gson = new Gson();

    }

    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                System.out.println("Socket opened " + socket.toString());
                SocketMsgWorker worker = new SocketMsgWorker(socket);
                worker.init();
                addresseeMap.put(id++, worker);
                System.out.println(addresseeMap);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            for (Map.Entry<Integer, MsgWorker> workerMapElement : addresseeMap.entrySet()) {
                String msg = workerMapElement.getValue().poll();
                if (msg != null) {
                    if (msg.equals("initDB")) {
                        db_id = workerMapElement.getKey();
                        System.out.println("DB Socket ID - " + db_id);
                    } else {
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse(msg).getAsJsonObject();
                        int addressTo = object.get("addressTo").getAsInt();
//                        MsgJsonDBMethodWrapper fromJsonObj =gson.fromJson(msg, MsgJsonDBMethodWrapper.class);
//                        int addressTo = fromJsonObj.getAddressTo();
//                        String messageFromJsonObj = fromJsonObj.getDbServiceMethod();
                        if (addressTo<0){
                            MsgJsonDBMethodWrapper fromJsonObj =gson.fromJson(msg, MsgJsonDBMethodWrapper.class);
                            fromJsonObj.setAddressTo(db_id);
                            fromJsonObj.setAddressFrom(workerMapElement.getKey());
                            addresseeMap.get(db_id).send(gson.toJson(fromJsonObj));

                        } else{
                            addresseeMap.get(addressTo).send(msg);
                        }
                    }
                }
            }
        }
    }
}
