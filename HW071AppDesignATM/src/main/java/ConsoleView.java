import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

class ConsoleView {
    private Card card;
    private ATM bankomat;
    ConsoleView(Card card, ATM bankomat) {
        this.card = card;
        this.bankomat =bankomat;
    }

    void showMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("-----------------------------------------");
            System.out.println("В банкомате доступны слеюдущие операции:");
            System.out.println("1. Снятие наличных");
            System.out.println("2. Пополнение карты");
            System.out.println("3. Показать баланс карты");
            System.out.println("-----------------------------------------");
            System.out.print("Выберете нужный пункт меню или введите 'q' для возврата карты: ");
            try {
                String menuOption = br.readLine();
                String message;
                switch (menuOption) {
                    case "1":
                        message ="Введите сумму необходимую для снятия. Для выхода из меню введите 'q': ";
                        while (true){
                            List<Cell> moneyIssue;
                            String input =userInput(message);
                            if (!"q".equals(input)) {
                                moneyIssue = bankomat.dispenseCash(card,input);
                            } else{
                                break;
                            }
                            if (moneyIssue!=null){
                                System.out.print("Выдано " + input + " " + card.getCurrency() + " следующими купюрами: ");
                                moneyIssue.forEach(x->System.out.print(x.getCapacity() > 0 ? x.getDenomiation() + "-" + x.getCapacity() + " " : ""));
                                System.out.println();
                                break;
                            }
                        }
                        break;
                    case "2":
                        message = "Внесите деньги по одной купюре. Для завершения операции введите 'q': ";
                        int acceptedCash =0;
                        while (true){
                            String input =userInput(message);
                            if (!"q".equals(input)) {
                                acceptedCash+=bankomat.acceptCash(card,input);
                            } else{
                                break;
                            }
                        }
                        System.out.println("Вы внесли "+ acceptedCash + " " + card.getCurrency());
                        break;
                    case "3":
                        System.out.println("На вашей карте " + bankomat.getBalance(card) + " " + card.getCurrency());
                        break;
                    case "q":
                        System.exit(0);
                        break;
                }
            } catch (IOException e) {
                System.out.println("Неверный ввод");
            }
        }
    }

    private String userInput(String message) {
        String input=null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(message);
            input = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }
}
