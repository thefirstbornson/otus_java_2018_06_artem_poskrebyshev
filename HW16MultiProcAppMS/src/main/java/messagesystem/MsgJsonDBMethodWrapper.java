package messagesystem;

public class MsgJsonDBMethodWrapper {
    private int addressTo;
    private int addressFrom;
    private String dbServiceMethod;
    private String[] dbServiceMethodParams;

    public MsgJsonDBMethodWrapper(int addressTo, int addressFrom, String dbServiceMethod, String[] dbServiceMethodParams) {
        this.addressTo = addressTo;
        this.addressFrom = addressFrom;
        this.dbServiceMethod = dbServiceMethod;
        this.dbServiceMethodParams = dbServiceMethodParams;
    }

    public MsgJsonDBMethodWrapper(String dbServiceMethod, String ... dbServiceMethodParams) {
        this(-1,-1,dbServiceMethod,dbServiceMethodParams);

    }
    public MsgJsonDBMethodWrapper() {
    }

    public int getAddressTo() {
        return addressTo;
    }

    public int getAddressFrom() {
        return addressFrom;
    }

    public String getDbServiceMethod() {
        return dbServiceMethod;
    }

    public String[] getDBServiceMethodParams() {
        return this.dbServiceMethodParams;
    }

    public void setAddressTo(int addressTo) {
        this.addressTo = addressTo;
    }

    public void setAddressFrom(int addressFrom) {
        this.addressFrom = addressFrom;
    }

    public void setDbServiceMethod(String dbServiceMethod) {
        this.dbServiceMethod = dbServiceMethod;
    }

    public void setDbServiceMethodParams(String[] dbServiceMethodParams) {
        this.dbServiceMethodParams = dbServiceMethodParams;
    }
}
