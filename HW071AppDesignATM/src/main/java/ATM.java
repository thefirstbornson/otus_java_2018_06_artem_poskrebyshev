import java.util.List;

public interface ATM {
    List<Cell> dispenseCash (Card card, String input);
    int acceptCash(Card card,String input);
    double getBalance(Card card);
    void fillBasket(List<Cell> bills);
}
