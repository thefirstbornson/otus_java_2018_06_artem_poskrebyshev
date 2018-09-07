public interface ATM {
    public void dispenseCash(Card card,int amount); //выдача
    public void acceptsCash(Card card,int amount);  //получение
    public double getBalance(Card card);
    public void setCell (int amount);
}
