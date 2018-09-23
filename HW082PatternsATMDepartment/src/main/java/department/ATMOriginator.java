package department;

import atm_components.Cell;

import java.util.List;

public class ATMOriginator {

    public ATMMemento save(List<Cell> basketState ) {
        return new ATMMemento(basketState);
    }
    List<Cell> restore(ATMMemento m) {
        return m.getBasketState();
    }
}
