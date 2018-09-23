package department;

import java.util.ArrayList;

public class ATMCaretaker {
    private ArrayList<ATMMemento> mementos = new ArrayList<>();

    public void addMemento(ATMMemento m) {
        mementos.add(m);
    }

    ATMMemento getOriginalState() {
        return mementos.get(0);
    }

    public ATMMemento getMemento(int num) {
        return mementos.get(num);
    }
}
