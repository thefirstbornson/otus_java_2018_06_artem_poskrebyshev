package messagesystem;

import java.io.Serializable;

/**
 * @author tully
 */
public interface Addressee extends Serializable {
    void init();
    Address getAddress();
    Address getMSAddress();
    void setMSAddress(Address address);
}
