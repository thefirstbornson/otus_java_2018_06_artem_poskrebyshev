public class Cell {
    private static final int DEFAULT_CAPACITY = 100;
    private Currency currency;
    private String typeOfBills ;
    private int capacity;


    public Cell(Currency currency, String bill) {
        this.currency = currency;
        this.typeOfBills = bill;
        this.capacity = DEFAULT_CAPACITY;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getTypeOfBills() {
        return typeOfBills;
    }

    public void setTypeOfBills(String typeOfBills) {
        this.typeOfBills = typeOfBills;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addBill(int amount) {
        this.capacity += amount;
    }

    public void takeOffBill(int amount){
        this.capacity -= amount;
    }

    public boolean hasBills(int amount){
        return capacity-amount>0 ? true : false;
    }


    public boolean isCapacious(int amount){
        return capacity+amount<capacity ? true : false;
    }

}
