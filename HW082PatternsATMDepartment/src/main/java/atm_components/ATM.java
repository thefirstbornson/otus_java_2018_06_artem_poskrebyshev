package atm_components;

import java.util.ArrayList;
import java.util.List;

public abstract class ATM {
    private final int DEFAULT_CELLS_COUNT=100;
    private ArrayList<Cell> basket ;
    public String currentCur;
    abstract public List<Cell> dispenseCash(Card card, String input);
    abstract public int acceptCash(Card card, String input);
    abstract public double getBalance(Card card);
    abstract public void fillBasket(List<Cell> bills);
    abstract public int getTotalAmount();
}
