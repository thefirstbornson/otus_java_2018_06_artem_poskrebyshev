package messagesystem;

public class MsgJsonDBMethodWrapper implements Message{
    private int addressTo;
    private int addressFrom;
    private String dbServiceMethod;
    private String[] dbServiceMethodParams;
    private String[] dbServiceMethodParamTypes;

    public MsgJsonDBMethodWrapper(int addressTo, int addressFrom, String dbServiceMethod
                                , String[] dbServiceMethodParams, String[]dbServiceMethodParamTypes) {
        this.addressTo = addressTo;
        this.addressFrom = addressFrom;
        this.dbServiceMethod = dbServiceMethod;
        this.dbServiceMethodParams = dbServiceMethodParams;
        this.dbServiceMethodParamTypes =dbServiceMethodParamTypes;
    }

    public MsgJsonDBMethodWrapper(String dbServiceMethod, String [] dbServiceMethodParams, String[]dbServiceMethodParamTypes) {
        this(-1,-1,dbServiceMethod,dbServiceMethodParams,dbServiceMethodParamTypes);
    }

    public MsgJsonDBMethodWrapper() {}

    @Override
    public int getAddressTo() {
        return addressTo;
    }
    @Override
    public int getAddressFrom(){
        return addressFrom;
    }
    @Override
    public String   getMessage() { return getDbServiceMethod();}
    public String   getDbServiceMethod() {
        return dbServiceMethod;
    }
    public String[] getDbServiceMethodParams() {
        return this.dbServiceMethodParams;
    }
    public String[] getDbServiceMethodParamTypes() {
        return dbServiceMethodParamTypes;
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
    public void setDbServiceMethodParams(String[] dbServiceMethodParams) {this.dbServiceMethodParams = dbServiceMethodParams;
    }
}

