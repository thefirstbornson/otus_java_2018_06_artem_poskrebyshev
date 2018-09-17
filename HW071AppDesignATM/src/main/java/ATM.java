import java.util.List;

public interface ATM {
    void dispenseCash(Card card);
    int acceptCash(Card card,String input);
    double getBalance(Card card);
    void fillBasket(List<Cell> bills);
}
