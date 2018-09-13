import java.util.List;

public interface ATM {
    public void dispenseCash(Card card); //выдача
    public void acceptCash(Card card);  //получение
    public void getBalance(Card card);
    public void fillBasket(List<Cell> bills);
}
