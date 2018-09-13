import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MainATM {
    public static void main(String[] args) {
        Card card = new Card("AP","5469200011792412","SB","RUB",15001.25);
        ATMModelT101 bankomat = new ATMModelT101();
        bankomat.fillBasket(Arrays.asList(new Cell("RUB",100),new Cell("RUB",200)
                ,new Cell("RUB",500),new Cell("RUB",1000)
                ,new Cell("RUB",2000),new Cell("RUB",5000)));
        bankomat.currentCur="RUB";

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("-----------------------------------------");
            System.out.println("В банкомате доступны слеюдущие операции:");
            System.out.println("1. Снятие наличных");
            System.out.println("2. Пополнение карты");
            System.out.println("3. Показать баланс карты");
            System.out.println("-----------------------------------------");
            System.out.print("Выберете нужный пункт меню или введите 'q' для возврата карты: ");
            try {
                String menuOption = br.readLine();
                switch (menuOption){
                    case "1":  bankomat.dispenseCash(card);
                        break;
                    case "2":  bankomat.acceptCash(card);
                        break;
                    case "3":  bankomat.getBalance(card);
                        break;
                    case "q":  System.exit(0);
                        break;
                }
            }
            catch (IOException e) {
            }
        }
    }
}
