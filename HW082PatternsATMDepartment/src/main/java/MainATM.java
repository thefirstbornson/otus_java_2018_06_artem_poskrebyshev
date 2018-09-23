import atm_components.ATMModelT101;
import atm_components.Cell;
import atm_components.Currency;
import department.*;
import views.DepartmentConsoleView;
import java.util.Arrays;
import java.util.stream.Collectors;


public class MainATM {
    public static void main(String[] args) {
        Department depart = new CollectionServiceDepartment();

        ATMModelT101 bankomat5359 = new ATMModelT101();
        ATMCaretaker caretaker5359 = new ATMCaretaker();
        ATMOriginator orginator5359 = new ATMOriginator();
        bankomat5359.currentCur= Currency.Rubles.NAME;
        bankomat5359.fillBasket(Arrays.stream(Currency.Rubles.values())
                                      .map(p->new Cell(bankomat5359.currentCur,p.getValue()))
                                      .collect(Collectors.toList()));
        caretaker5359.addMemento(orginator5359.save(bankomat5359.getBasket()));
        depart.add(bankomat5359,  orginator5359,caretaker5359);

        ATMModelT101 bankomat0162 = new ATMModelT101();
        ATMCaretaker caretaker0162 = new ATMCaretaker();
        ATMOriginator orginator0162 = new ATMOriginator();
        bankomat0162.currentCur= Currency.Rubles.NAME;
        bankomat0162.fillBasket(Arrays.stream(Currency.Rubles.values())
                                      .map(p->new Cell(bankomat5359.currentCur,p.getValue(),100))
                                      .collect(Collectors.toList()));
        caretaker0162.addMemento(orginator0162.save(bankomat0162.getBasket()));
        depart.add(bankomat0162,  orginator0162,caretaker0162);

        ATMModelT101 bankomat0139 = new ATMModelT101();
        ATMCaretaker caretaker0139 = new ATMCaretaker();
        ATMOriginator orginator0139 = new ATMOriginator();
        bankomat0139.currentCur= Currency.Rubles.NAME;
        bankomat0139.fillBasket(Arrays.stream(Currency.Rubles.values())
                                      .map(p->new Cell(bankomat5359.currentCur,p.getValue(),250))
                                      .collect(Collectors.toList()));
        caretaker0139.addMemento(orginator0139.save(bankomat0139.getBasket()));
        depart.add(bankomat0139,  orginator0139,caretaker0139);

        new DepartmentConsoleView(depart).showMenu();
    }
}
