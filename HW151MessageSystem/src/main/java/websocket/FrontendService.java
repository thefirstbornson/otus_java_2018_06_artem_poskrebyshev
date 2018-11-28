package websocket;

import messagesystem.Addressee;

public interface FrontendService extends Addressee {
    void init();
    void handleRequest(String login);
    <T> void sendMessage(T message);
}

