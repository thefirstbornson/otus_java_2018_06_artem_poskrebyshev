package department;

import atm_components.Cell;

import java.util.ArrayList;
import java.util.List;

 class ATMMemento {
    private final List<Cell> basketState;

     ATMMemento(List<Cell> state) {
        this.basketState = new ArrayList<>(state) ;
    }

     List<Cell> getBasketState() {
        return basketState;
    }

}
