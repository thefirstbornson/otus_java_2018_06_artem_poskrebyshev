package messagesystem;

public class MsgJson implements Message {
    private int addressTo;
    private int addressFrom;
    private String message;

    public MsgJson(String message) {
        this(-1,-1,message);
    }

    @Override
    public int getAddressTo() {
        return addressTo;
    }
    @Override
    public int getAddressFrom() {
        return addressFrom;
    }

    public MsgJson(int addressTo, int addressFrom, String message) {
        this.addressTo = addressTo;
        this.addressFrom = addressFrom;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
