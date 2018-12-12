package serversocket;

import java.io.IOException;
import java.net.Socket;


public class ClientSocketMsgWorker extends SocketMsgWorker{

    public ClientSocketMsgWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private ClientSocketMsgWorker(Socket socket) {
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
