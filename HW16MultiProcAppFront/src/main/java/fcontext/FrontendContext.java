package fcontext;

import messagesystem.Address;
import messagesystem.FrontendService;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class FrontendContext implements FrontendService {
    private Address address;
    private Address dbAdress;
    private Address msAdress;
    private Map<String, Address> frontAddressMap;
    public static final String HOST = "localhost";
    public static final int SOCKET_PORT = 5050;

    public Address getMsAdress() {
        return msAdress;
    }

    public void setMsAdress(Address msAdress) {
        this.msAdress = msAdress;
    }

    public FrontendContext(Address address) {
        this.address = address;
        this.frontAddressMap = new HashMap<>();
        this.msAdress = null;
    }

    public Address getDbAdress() {
        return dbAdress;
    }

    public void setDbAdress(Address dbAdress) {
        this.dbAdress = dbAdress;
    }


    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getFrontAddress(String id) {
        return frontAddressMap.get(id);
    }

    public void setFrontAddress(Socket frontSocket) {
//        this.frontAddressMap.put(frontSocket.getId(), frontSocket);
    }

    @Override
    public void init() {
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Address getMSAddress() {
        return this.msAdress;
    }

    @Override
    public void setMSAddress(Address msAdress) {
        this.msAdress=msAdress;
    }

    @Override
    public <T> void handleRequest(T message) {
        setDbAdress((Address)message);
    }

    @Override
    public <T> void sendResult(T message) {

    }


//    @Override
//    public Socket getSocket() {
//        return this.socket;
//    }

}
