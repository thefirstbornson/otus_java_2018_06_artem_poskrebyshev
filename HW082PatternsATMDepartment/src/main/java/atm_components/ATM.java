package atm_components;

import java.util.ArrayList;
import java.util.List;

public interface ATM {
   List<Cell> dispenseCash(Card card, int input);
   int acceptCash(Card card, int input);
   double getBalance(Card card);
   void fillBasket(List<Cell> bills);
   int getTotalAmount();
   ATMMemento save() ;
   void restore(ATMMemento m);

   class ATMMemento {
        private final List<Cell> basketState;

        ATMMemento(List<Cell> state) {
            this.basketState = new ArrayList<>(state) ;
        }

        List<Cell> getBasketState() {
            return basketState;
        }
   }
}
