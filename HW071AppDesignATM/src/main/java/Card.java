public class Card {
    private final String holderName ;
    private final long cardNumber;
    private final String  issuingBank;
    private final Currency currency;
    private double balance;

    public Card(String holderName, long cardNumber, String issuingBank, Currency currency, double balance) {
        this.holderName = holderName;
        this.cardNumber = cardNumber;
        this.issuingBank = issuingBank;
        this.currency = currency;
        this.balance = balance;
    }

    public String getHolderName() {
        return holderName;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public String getIssuingBank() {
        return issuingBank;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw (double amount){
        this.balance -= amount;
    }
    public boolean isSufficientFunding(double amount){
        return this.balance<amount  ? false : true;
    }

}
