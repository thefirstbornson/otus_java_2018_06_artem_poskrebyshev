import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainATM {
    public static void main(String[] args) {
        ATMModelT101 bankomat = new ATMModelT101();

        bankomat.currentCur=Currency.Rubles.NAME;
        bankomat.fillBasket(Arrays.stream(Currency.Rubles.values())
                                  .map(p->new Cell(bankomat.currentCur,p.getValue()))
                                  .collect(Collectors.toList()));
        Card card = new Card("AP","5469200011792412","SB","RUB",15001.25);

        new ConsoleView(card,bankomat).showMenu();
    }
}
