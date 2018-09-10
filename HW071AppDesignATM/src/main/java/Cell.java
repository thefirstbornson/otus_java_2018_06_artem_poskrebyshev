public class Cell {
    private static final int DEFAULT_CAPACITY = 100;
    private String currency;
    private int denomiation;
    private int capacity;
    public static final int MAX_CAPCITY = 150;


    public Cell(Bill bill) {
        this.currency = bill.getcurrancy();
        this.denomiation = bill.getDenomination();
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
        this.capacity ++;
    }

    public void takeOffBill(int amount){
        this.capacity -= amount;
    }

    public boolean hasBills(int amount){
        return capacity-amount>0 ? true : false;
    }


    public boolean isCapacious(int amount){
        return capacity+amount<MAX_CAPCITY ? true : false;
    }
    public boolean isCapacious(){
        return isCapacious(1);
    }
}
