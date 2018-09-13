import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ATMModelT101 implements ATM{
    private final int DEFAULT_CELLS_COUNT=10;
    private ArrayList<Cell>  basket ;
    public String currentCur;

    public ATMModelT101() {
        this.basket  = new ArrayList<Cell>(DEFAULT_CELLS_COUNT);
    }

    public void dispenseCash(Card card) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                try {
                    if (!availableNotes().isEmpty()) {
                        System.out.print("Введите сумму необходимую для снятия. Для выхода из меню введите 'q': ");
                        String input = br.readLine();

                            if ("q".equals(input)) {
                            } else {
                                int correctInt = Integer.parseInt(input);
                                if  (card.isSufficientFunding(correctInt)) {
                                    List<Cell> moneyIssue = vaildInput(correctInt);
                                    if (moneyIssue != null) {
                                        reduceBasket(moneyIssue);
                                        card.withdraw(Double.parseDouble(input));
                                        System.out.print("Выдано " + input + " " + currentCur + " следующими купюрами: ");
                                        for (Cell cell : moneyIssue) {
                                            System.out.print(
                                                    cell.getCapacity() > 0 ? cell.getDenomiation() + "-" + cell.getCapacity() + " " : "");
                                        }
                                        System.out.println();
                                        break;
                                    }
                                }else{
                                    System.out.println("На вашей карте недостаточно средств!");
                                }
                            }
                    } else {
                        System.out.println("Банкомат работает только на прием наличных!");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Неверный формат введенных данных!" + "\n" + e);
                }
            }
    }

    public void acceptCash(Card card) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int acceptedCash =0;
        while (true) {
            try {
                System.out.print("Внесите деньги по одной купюре. Для завершения операции введите 'q': ");
                String input = br.readLine();

                if ("q".equals(input)) {
                    break;
                }
                else{
                    int bill =Integer.parseInt(input);
                    if (basket.stream().map(x->x.getDenomiation()).filter(x->x.equals(bill)).findAny().isPresent()){
                        billToCell(new Bill(currentCur, bill));
                        acceptedCash+=bill;
                        System.out.println("Вы внесли "+ bill + " " + this.currentCur);
                    }
                    else {
                        System.out.println("Неверный формат ввода!");
                    };
                }
            }
            catch ( Exception e){
                System.out.println("Неверный ввод суммы");
            }
        }
        if (acceptedCash > 0) {
            card.deposit(acceptedCash);
            System.out.println("Всего внесено на карту: " + acceptedCash + " "+currentCur);
        }
    }

    private List<Cell> vaildInput(int input) {
        String err ="Неверный формат введенных данных!";
        int validInt=input;
        List<Cell> moneyIssue=null;
        try {

            int sumInBasket =this.basket.stream().filter(x -> x.getCapacity() > 0)
                    .mapToInt(x -> x.getDenomiation()*x.getCapacity())
                    .sum();

            if (validInt>sumInBasket) {
                err = "Невозможно выдать данную сумму!";
                throw new Exception();
            }

            int minDenomination = this.basket.stream().map(x -> x.getDenomiation())
                    .filter(x -> x > 0)
                    .min(Integer::min).get();
            if (!(validInt >= minDenomination && validInt % minDenomination == 0)) {
                err = "Введенная сумма должна быть кратна " + minDenomination;
                throw new Exception();
            }

            moneyIssue = this.factorize(validInt,this.basket);
            if (moneyIssue==null){
                    err ="Невозможно выдать данную сумму";
                    throw new Exception();
            }
        }
        catch (Exception e){
            System.out.println(err);
        }
        return moneyIssue;
    }

    @Override
    public void getBalance(Card card) {
        System.out.println("На вашей карте " + card.getBalance() + " " + card.getCurrency());
    }

    private ArrayList<Integer> availableNotes(){
        return new ArrayList<>(this.basket.stream()
                .filter(x -> x.getCapacity() > 0)
                .map(x -> x.getDenomiation())
                .collect(Collectors.toList()));
    };

    public void fillBasket(List<Cell> bills) {
        this.basket.addAll(bills);
        Collections.sort(this.basket);
        Collections.reverse(this.basket);
        }

    private void reduceBasket(List<Cell> moneyIssue ){
        for (int i = 0; i < this.basket.size(); i++) {
            Cell reduBasket = new Cell (this.basket.get(i).getCurrency()
                                          ,this.basket.get(i).getDenomiation()
                                          ,this.basket.get(i).diff(moneyIssue.get(i)));
            this.basket.set(i,reduBasket);
        }
    }

    private void billToCell(Bill bill){
        for (Cell cell: basket){
            if (bill.getDenomination()== cell.getDenomiation() && (bill.getcurrancy()==this.currentCur)) {
                if (cell.isCapacious()){
                    cell.addBill();
                    break;
                }
                else{
                    System.out.println("Невозможно внести купюру. Ячейка переполнена!");
                    break;
                }
            }
        }
    };

    private List<Cell> factorize(int numbers, List<Cell> source) {
        List<Cell> factors = new ArrayList<>();
        for (Cell cell:source) {
            int minCapacity = Integer.min(numbers/cell.getDenomiation(),cell.getCapacity());
            factors.add(new Cell(cell.getCurrency()
                                ,cell.getDenomiation()
                                ,minCapacity));
            numbers -= cell.getDenomiation()*minCapacity;
        }
        if (numbers>0){
           return null;
        }
        return factors;
    }

}
