public interface ATM {
    public void dispenseCash(Card card); //выдача
    public void acceptsCash(Card card);  //получение
    public double getBalance(Card card);
    public void fillBasket(Bill... currency);
}
