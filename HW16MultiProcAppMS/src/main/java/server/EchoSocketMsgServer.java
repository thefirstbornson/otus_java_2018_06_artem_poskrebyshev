package server;

import messagesystem.Address;
import messagesystem.MessageSystemContext;
import messagesystem.message.Message;
import messagesystem.message.MsgToClient;
import messagesystem.message.MsgToMS;
import serversocket.MsgWorker;
import serversocket.SocketMsgWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class EchoSocketMsgServer  {
    private static final Logger logger = Logger.getLogger(EchoSocketMsgServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;

    private final ExecutorService executor;
    private final List<MsgWorker> workers;
    private final Map<Socket, MsgWorker> addresseeMap;
    MessageSystemContext mscontext;

    public EchoSocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workers = new CopyOnWriteArrayList<>();
        addresseeMap = new HashMap<>();
        MessageSystemContext  mscontext;

    }

    private void startMSContextWorker () {
        System.out.println("worker - 1");
        SocketMsgWorker client = null;
        try {
            client = new SocketMsgWorker(new Socket("localhost", PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.init();
        mscontext.setMsContextworker(client);
    }

    public void start() throws Exception {
        //executor.submit(this::echo);
        mscontext = new MessageSystemContext(new Address("MSAddress"));
        executor.submit(this::startMSContextWorker);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                System.out.println("Socket opened " +socket.toString());
                SocketMsgWorker worker = new SocketMsgWorker(socket);
                worker.init();
                worker.send(new MsgToClient(mscontext.getAddress(),mscontext.getAddress()));
                workers.add(worker);
                System.out.println(workers);
            }
        }
    }

    public void addAddressee (MsgWorker worker){
        addresseeMap.put(worker.getSocket(),worker);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            for (MsgWorker worker : workers) {

                Message msg = worker.poll();
                while (msg != null) {
                    System.out.println("worker - " + worker+" handle message " +msg);
                    if (msg instanceof MsgToMS){
                       // mscontext.setDbAddress(worker.getSocket());
                        System.out.println("DB Socket - " +mscontext.getDbAddress() );
                    }
//                    System.out.println("Mirroring the message: " + msg.toString());
                    worker.send(msg);
                    msg = worker.poll();
               }

            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
    }

}
