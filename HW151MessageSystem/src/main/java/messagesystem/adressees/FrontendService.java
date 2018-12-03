package messagesystem.adressees;

public interface FrontendService extends Addressee {
    void init();
    void handleRequest(String login);
    <T> void sendResult(T message);
}

