import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ATMModelT101 implements ATM{
    private final int DEFAULT_CELLS_COUNT=10;
    private ArrayList<Cell>  basket ;
    String currentCur;

    ATMModelT101() {
        this.basket  = new ArrayList<>(DEFAULT_CELLS_COUNT);
    }
    @Override
    public List<Cell> dispenseCash (Card card, String input){
            List<Cell> moneyIssue=null;
                try {
                    if (availableNotes().isEmpty()) throw new NotValidMoneyRequestException("Банкомат работает только на прием наличных!");
                    int correctInt = Integer.parseInt(input);
                    if  (!card.isSufficientFunding(correctInt)) throw new NotValidMoneyRequestException("На вашей карте недостаточно средств!!");
                    moneyIssue = vaildInput(correctInt);
                    reduceBasket(moneyIssue);
                    card.withdraw(Double.parseDouble(input));
                } catch (NotValidMoneyRequestException e) {
                    System.out.println(e.getMessage());
                }
                catch (NumberFormatException e){
                    System.out.println("Недопустимый формат ввода!");
                }
            return moneyIssue;
    }

    @Override
    public int acceptCash(Card card, String input) {
        int validVolumeCash=0;
            try {
                 int bill =Integer.parseInt(input);
                 if (!basket.stream().map(Cell::getDenomiation).anyMatch(x->x.equals(bill)))
                     throw new NotValidMoneyRequestException("Вы не можете внести такую купюру!");
                 validVolumeCash =billToCell(new Bill(currentCur, bill));
                 if (validVolumeCash>0) card.deposit(validVolumeCash);
                 return validVolumeCash;
            }
            catch (NotValidMoneyRequestException e) {
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException e){
                System.out.println("Недопустимый формат ввода!");
            }
        return validVolumeCash;
    }

    @Override
    public double getBalance(Card card) {
        return card.getBalance();
    }

    @Override
    public void fillBasket(List<Cell> bills) {
        this.basket.addAll(bills);
        Collections.sort(this.basket);
        Collections.reverse(this.basket);
    }

    private List<Integer> availableNotes(){
        return this.basket.stream()
                          .filter(x -> x.getCapacity() > 0)
                          .map(Cell::getDenomiation)
                          .collect(Collectors.toList());
    }

    private List<Cell> vaildInput(int validInt) throws NotValidMoneyRequestException {
        String err ="Неверный формат введенных данных!";
        List<Cell> moneyIssue=null;

            if (validInt<0)
                throw new NotValidMoneyRequestException("Запрошенная сумма не может быть отрицательной");

            int sumInBasket =this.basket.stream().mapToInt(x -> x.getDenomiation()*x.getCapacity())
                                                 .sum();
            if (validInt>sumInBasket)
                throw new NotValidMoneyRequestException("Запрошенная сумма превышает допустимый лимит для выдачи");

            int minDenomination = this.basket.stream().map(Cell::getDenomiation)
                                                      .filter(x -> x > 0)
                                                      .min(Integer::min).get();
            if (!(validInt >= minDenomination && validInt % minDenomination == 0))
                throw new NotValidMoneyRequestException( "Введенная сумма должна быть кратна " + minDenomination);

            moneyIssue = this.factorize(validInt,this.basket);
            if (moneyIssue==null)throw new NotValidMoneyRequestException("Невозможно выдать данную сумму");

        return moneyIssue;
    }

    private void reduceBasket(List<Cell> moneyIssue ){
        for (int i = 0; i < this.basket.size(); i++) {
                Cell reduBasket = new Cell (this.basket.get(i).getCurrency()
                                           ,this.basket.get(i).getDenomiation()
                                           ,this.basket.get(i).diff(moneyIssue.get(i)));
                this.basket.set(i,reduBasket);
        }
    }

    private int billToCell(Bill bill){
        for (Cell cell: this.basket){
            if (bill.getDenomination()== cell.getDenomiation() && (bill.getcurrancy().equals(this.currentCur))) {
                if (cell.isCapacious()){
                    cell.addBill();
                    return bill.getDenomination();
                }
                else{
                    System.out.println("Невозможно внести купюру. Ячейка переполнена!");
                    break;
                }
            }
        }
        return 0;
    }

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

class NotValidMoneyRequestException extends Exception {

    public NotValidMoneyRequestException(String message) {
        super(message);
    }
}


