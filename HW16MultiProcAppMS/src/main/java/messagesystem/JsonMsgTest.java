package messagesystem;

public class JsonMsgTest {
    private int addressTo;
    private int addressFrom;
    private String message;

    public JsonMsgTest(String message) {
        this(-1,-1,message);
    }

    public int getAddressTo() {
        return addressTo;
    }

    public int getAddressFrom() {
        return addressFrom;
    }

    public JsonMsgTest(int addressTo, int addressFrom, String message) {
        this.addressTo = addressTo;
        this.addressFrom = addressFrom;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
