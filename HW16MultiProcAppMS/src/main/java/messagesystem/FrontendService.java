package messagesystem;

public interface FrontendService extends Addressee {
    void init();
    <T> void handleRequest(T message);
    <T> void sendResult(T message);

}
