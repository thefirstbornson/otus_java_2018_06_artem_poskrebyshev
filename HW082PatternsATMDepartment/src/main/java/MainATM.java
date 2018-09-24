import atm_components.ATMModelT101;
import atm_components.Card;
import atm_components.Cell;
import atm_components.Currency;
import department.*;
import views.ATMConsoleView;
import views.DepartmentConsoleView;
import java.util.Arrays;
import java.util.stream.Collectors;


public class MainATM {
    public static void main(String[] args) {
        Department depart = new CollectionServiceDepartment();

        ATMModelT101 bankomat5359 = new ATMModelT101();
        bankomat5359.setCurrentCur(Currency.Rubles.NAME);
        bankomat5359.fillBasket(Arrays.stream(Currency.Rubles.values())
                                      .map(p->new Cell(bankomat5359.getCurrentCur(),p.getValue()))
                                      .collect(Collectors.toList()));
        depart.add(bankomat5359);
        depart.save(bankomat5359);

        ATMModelT101 bankomat0162 = new ATMModelT101();
        bankomat0162.setCurrentCur(Currency.Rubles.NAME);
        bankomat0162.fillBasket(Arrays.stream(Currency.Rubles.values())
                                      .map(p->new Cell(bankomat5359.getCurrentCur(),p.getValue(),100))
                                      .collect(Collectors.toList()));
        depart.add(bankomat0162);
        depart.save(bankomat0162);

        ATMModelT101 bankomat0139 = new ATMModelT101();
        bankomat0139.setCurrentCur(Currency.Rubles.NAME);
        bankomat0139.fillBasket(Arrays.stream(Currency.Rubles.values())
                                      .map(p->new Cell(bankomat5359.getCurrentCur(),p.getValue(),250))
                                      .collect(Collectors.toList()));
        depart.add(bankomat0139);
        depart.save(bankomat0139);

        Card card=new Card("AP"
                ,"5"
                ,"SB"
                ,"RUB"
                ,10000);

        //new ATMConsoleView(card,bankomat5359).showMenu();
        new DepartmentConsoleView(depart).showMenu();
    }
}
