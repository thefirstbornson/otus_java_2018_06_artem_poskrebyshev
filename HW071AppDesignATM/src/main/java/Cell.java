public class Cell implements Comparable<Cell>  {
    private static final int DEFAULT_CAPACITY = 1;
    private String currency;
    private int denomiation;
    private int capacity;
    public static final int MAX_CAPCITY = 20;


    public Cell(String currency,int denomiation,int capacity ) {
        this.currency = currency;
        this.denomiation = denomiation;
        this.capacity =  capacity;
    }

    public Cell(String currency,int denomiation ) {
        this.currency = currency;
        this.denomiation = denomiation;
        this.capacity = DEFAULT_CAPACITY;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getDenomiation() {
        return denomiation;
    }

    public void setDenomiation(int denomiation) {
        this.denomiation = denomiation;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addBill(int amount) {
        this.capacity += amount;
    }

    public void addBill() {
        addBill(1);
    }

    public void takeOffBill(int amount){
        this.capacity -= amount;
    }

    public boolean hasBills(int amount){
        return capacity-amount>=0 ? true : false;
    }


    public boolean isCapacious(int amount){
        return capacity+amount<=MAX_CAPCITY ? true : false;
    }
    public boolean isCapacious(){
        return isCapacious(1);
    }

    @Override
    public int compareTo(Cell compareCell) {
        int compareDenomination=((Cell)compareCell).getDenomiation();
        return this.denomiation-compareDenomination;
    }

    public int diff(Cell cell) {
        if (this.getCurrency()==cell.getCurrency() && this.getDenomiation()==cell.getDenomiation()) {
            return this.getCapacity()-cell.getCapacity();
        }
        else return-1;
    }
}

