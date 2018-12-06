package serversocket;


import messagesystem.message.Message;

import java.io.Closeable;
import java.net.Socket;


public interface MsgWorker extends Closeable {
    void send(Message msg);

    Message poll();

    Message take() throws InterruptedException;
    Socket getSocket();


    void close();
}
