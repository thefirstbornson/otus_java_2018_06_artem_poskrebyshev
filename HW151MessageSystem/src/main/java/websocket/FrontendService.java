package websocket;

import messagesystem.Addressee;

public interface FrontendService extends Addressee {
    void init();
    void handleRequest(String data);
    <T> void sendResult(T message);
}

