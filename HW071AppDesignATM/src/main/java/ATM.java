public interface ATM {
    public void dispenseCash(Card card,int amount); //выдача
    public void acceptsCash(Card card);  //получение
    public double getBalance(Card card);
    public void fillBasket(Bill... currency);
}
