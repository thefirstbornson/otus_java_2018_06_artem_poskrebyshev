package messagesystem;

/**
 * @author tully
 */
public interface Addressee {
    void init();
    Address getAddress();

    MessageSystem getMS();
}
