public class ATMModelT101 implements ATM{
    private final int DEFAULT_CELLS_COUNT=10;
    private Cell [] cells;

    public ATMModelT101() {
        cells = new Cell[DEFAULT_CELLS_COUNT];

    }


    public void dispenseCash(Card card, int amount) {

    }

    public void acceptsCash(Card card, int amount) {

    }

    public double getBalance(Card card) {
        return 0;
    }

    public void setCell(int amount) {

    }
}
