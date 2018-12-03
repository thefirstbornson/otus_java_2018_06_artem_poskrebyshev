package messagesystem.adressees;

import messagesystem.Address;
import messagesystem.MessageSystem;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}
