package serversocket;

import messagesystem.message.Message;
import messagesystem.message.MsgToClient;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class SocketMsgWorker implements MsgWorker {
    private static final Logger logger = Logger.getLogger(SocketMsgWorker.class.getName());
    private static final int WORKERS_COUNT = 2;
    private static final int QUEUE_CAPACITY = 10;

    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

    private final ExecutorService executor;
    protected final Socket socket;

    public SocketMsgWorker(Socket socket) {
        this.socket = socket;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    @Override
    public void send(Message msg) {
        output.add(msg);
    }

    @Override
    public Message poll() {
        return input.poll();
    }

    @Override
    public Message take() throws InterruptedException {
        return input.take();
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void close() {
        executor.shutdown();
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }


    private void sendMessage() {
        try (ObjectOutputStream sender = new ObjectOutputStream(socket.getOutputStream())) {
            while (socket.isConnected()) {
                final Message msg = (MsgToClient) output.take(); //blocks
                System.out.println("Sending message: " + msg.toString() + "from socket" + socket.toString());
                sender.writeObject(msg);
                System.out.println("!");
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }


    private void receiveMessage() {
        try (final ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {
            MsgToClient msg;
            try {
                while (true){
                    msg = (MsgToClient) reader.readObject();
                    System.out.println("Receiving message: " + msg.toString() + "to socket" + socket.toString());
                    input.add(msg);
                    System.out.println("!");
                }

            } catch(EOFException eof)
            {
                //end of file reached, do nothing
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            close();
        }
    }

}
