public class Card {
    private final String holderName ;
    private final String cardNumber;
    private final String  issuingBank;
    private final String currency;
    private double balance;

    public Card(String holderName, String cardNumber, String issuingBank, String currency, double balance) {
        this.holderName = holderName;
        this.cardNumber = cardNumber;
        this.issuingBank = issuingBank;
        this.currency = currency;
        this.balance = balance;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getIssuingBank() {
        return issuingBank;
    }

    public String getCurrency() {
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
        return this.balance>amount ;
    }

}
