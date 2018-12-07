package serversocket;


import java.io.Closeable;
import java.net.Socket;


public interface MsgWorker extends Closeable {
    void send(String msg);

    String poll();

    String take() throws InterruptedException;
    Socket getSocket();


    void close();
}
