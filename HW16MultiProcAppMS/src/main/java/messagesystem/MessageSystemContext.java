package messagesystem;

import serversocket.MsgWorker;

public class MessageSystemContext implements Addressee{
    private Address msAddress;
    private Address dbAddress;

    public MsgWorker getMsContextworker() {
        return msContextworker;
    }

    private MsgWorker msContextworker;
   // private Map<String, Socket> frontAddressMap;


    public void setMsContextworker(MsgWorker msContextworker) {
        this.msContextworker = msContextworker;
    }

    public MessageSystemContext(Address msAddress) {
        this.msContextworker = msContextworker;
        this.msAddress = msAddress;
    }

    public Address getMsAddress() {
        return msAddress;
    }

//
//    public Socket getFrontAddress(String id) {
//        return frontAddressMap.get(id);
//    }

//    public void setFrontAddress(Socket frontAddress) {
//        this.frontAddressMap.put(frontAddress.getId(),frontAddress);
//    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }

    @Override
    public void init() {

    }

    @Override
    public Address getAddress() {
        return msAddress;
    }

    @Override
    public Address getMSAddress() {
        return null;
    }

    @Override
    public void setMSAddress(Address address) {

    }
}
