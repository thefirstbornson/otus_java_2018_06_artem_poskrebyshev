package dbsockets;

import serversocket.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

public class DBSocketMsgWorker extends SocketMsgWorker{

    public DBSocketMsgWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private DBSocketMsgWorker(Socket socket) {
        super(socket);
    }

    @Override
    public void close() {
        try {
            super.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
