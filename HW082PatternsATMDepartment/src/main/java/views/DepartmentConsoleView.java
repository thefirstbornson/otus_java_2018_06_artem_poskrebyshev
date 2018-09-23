package views;

import atm_components.Card;
import department.Department;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DepartmentConsoleView {
    private Card card;
    private Department department;
    public DepartmentConsoleView(Department department) {
        this.department =department;
    }

    public void showMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("-----------------------------------------");
            System.out.println("Приложение ATM Department содержит слеюдущие операции:");
            System.out.println("1. Показать сумму остатков всех обслуживаемых банктоматов");
            System.out.println("2. Имитировать снятие наличных с банкоматов");
            System.out.println("3. Восстановить исходное состояние всех банкоматов");
            System.out.println("-----------------------------------------");
            System.out.print("Выберете нужный пункт меню или введите 'q' для завершения работы: ");
            try {
                String menuOption = br.readLine();
                switch (menuOption) {
                    case "1":
                        System.out.println("Сумма остатков всех банкоматов: "+department.collectBalance());
                        break;
                    case "2":
                        card=new Card("AP"
                                ,"5469200011792412"
                                ,"SB"
                                ,"RUB"
                                ,150001.25);
                        String sumToDispense ="10000";
                        department.getATMList().forEach(x->x.dispenseCash(card,sumToDispense));
                        System.out.println("Со всех банкоматов снято: "
                                + department.getATMList().size()*Integer.parseInt(sumToDispense)
                                +" " + card.getCurrency() +" ."
                        );
                        break;
                    case "3":
                        if (department.restoreOriginalState()){
                            System.out.println("Исходное состоятние восстановлено!");
                        }else {
                            System.out.println("Исходное состоятние не восстановлено!");
                        }
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
}

