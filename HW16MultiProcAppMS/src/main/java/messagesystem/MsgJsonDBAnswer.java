package messagesystem;

public class MsgJsonDBAnswer implements Message{
    private int addressTo;
    private int addressFrom;
    private String message;

    @Override
    public int getAddressTo() {
        return addressTo;
    }
    @Override
    public int getAddressFrom() {
        return addressFrom;
    }

    public MsgJsonDBAnswer(int addressTo, int addressFrom, String message) {
        this.addressTo = addressTo;
        this.addressFrom = addressFrom;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
