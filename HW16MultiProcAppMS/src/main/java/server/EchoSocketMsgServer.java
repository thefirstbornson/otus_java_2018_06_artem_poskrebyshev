package server;

import messagesystem.Address;
import messagesystem.MessageSystemContext;
import serversocket.MsgWorker;
import serversocket.SocketMsgWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class EchoSocketMsgServer  {
    private static final Logger logger = Logger.getLogger(EchoSocketMsgServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;
    private int db_id;
    private int id =0;

    private final ExecutorService executor;
    private final List<MsgWorker> workers;
    private final Map<Integer, MsgWorker> addresseeMap;
    MessageSystemContext mscontext;

    public EchoSocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workers = new CopyOnWriteArrayList<>();
        addresseeMap = new ConcurrentHashMap<>();
        MessageSystemContext  mscontext;

    }

    public void start() throws Exception {
        executor.submit(this::echo);
        mscontext = new MessageSystemContext(new Address("MSAddress"));

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                System.out.println("Socket opened " +socket.toString());
                SocketMsgWorker worker = new SocketMsgWorker(socket);
                worker.init();
                //workers.add(worker);
                addresseeMap.put(id++,worker);
                System.out.println(addresseeMap);
            }
        }
    }
//    public void addAddressee (MsgWorker worker){
//        addresseeMap.put(worker.getSocket(),worker);
//    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
           // System.out.println("-");
            for (Map.Entry<Integer, MsgWorker> pair : addresseeMap.entrySet()) {
               // System.out.println(pair.getKey() + "-"+pair.getValue());
                String msg = pair.getValue().poll();
               // System.out.println(msg);
                if (msg!=null){
                    if (msg.equals("db")) {
                        db_id=pair.getKey();
                        System.out.println("DB Socket - " + db_id);
                    }
                }
            }
        }
    }

}
