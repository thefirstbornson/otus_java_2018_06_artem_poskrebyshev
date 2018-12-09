package serversocket;

import java.io.*;
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

    private final BlockingQueue<String> output = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private final BlockingQueue<String> input = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

    private final ExecutorService executor;
    protected final Socket socket;

    public SocketMsgWorker(Socket socket) {
        this.socket = socket;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    @Override
    public void send(String msg) {
        output.add(msg);
    }

    @Override
    public String poll() {
        return input.poll();
    }

    @Override
    public String take() throws InterruptedException {
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
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                final String msg = output.take(); //blocks
                System.out.println("Sending message: " + msg);
                writer.println(msg);
                writer.println();//line with json + an empty line
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }


    private void receiveMessage() {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) { //blocks
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    final String json = stringBuilder.toString();
                    System.out.println("Receiving message: " + json);
                    input.add(json);
                    stringBuilder = new StringBuilder();
                    System.out.println("Message recieved: " );
                }

            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            close();
        }
    }

}
