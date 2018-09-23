package atm_components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ATMModelT101 extends ATM{
    private final int DEFAULT_CELLS_COUNT=10;
    private List<Cell>  basket ;
    public String currentCur;

    public ATMModelT101() {
        this.basket  = new ArrayList<>(DEFAULT_CELLS_COUNT);
    }

    @Override
    public int getTotalAmount() {
        return this.basket.stream().mapToInt(x->x.getDenomiation()*x.getCapacity()).sum();
    }

    public List<Cell> getBasket(){
        return this.basket;
    }

    @Override
    public List<Cell> dispenseCash(Card card, String input) {
                try {
                    if (!availableNotes().isEmpty()) {
                        int correctInt = Integer.parseInt(input);
                        if  (card.isSufficientFunding(correctInt)) {
                            List<Cell> moneyIssue = vaildInput(correctInt);
                            if (moneyIssue != null) {
                                reduceBasket(moneyIssue);
                                card.withdraw(Double.parseDouble(input));
                                return moneyIssue;
                            }
                            }else{
                                System.out.println("На вашей карте недостаточно средств!");
                            }

                    } else {
                        System.out.println("Банкомат работает только на прием наличных!");
                                            }
                } catch (Exception e) {
                    System.out.println("Неверный формат введенных данных!");
                }
                return null;
    }

    @Override
    public int acceptCash(Card card, String input) {
            try {
                    int bill =Integer.parseInt(input);
                    if (basket.stream().map(Cell::getDenomiation).anyMatch(x->x.equals(bill))){
                        int validVolumeCash =billToCell(new Bill(currentCur, bill));
                        if (validVolumeCash>0) {
                            card.deposit(validVolumeCash);
                        }
                        return validVolumeCash;
                    }
                    else {
                        System.out.println("Вы не можете внести такую купюру!");
                    }
            }
            catch ( Exception e){
                System.out.println("Неверный ввод суммы");
            }
        return 0;
    }

    @Override
    public double getBalance(Card card) {
        return card.getBalance();
    }

    @Override
    public void fillBasket(List<Cell> bills) {
        this.basket.clear();
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

    private List<Cell> vaildInput(int validInt) {
        String err ="Неверный формат введенных данных!";
        List<Cell> moneyIssue=null;
        try {

            int sumInBasket =this.basket.stream().filter(x -> x.getCapacity() > 0)
                    .mapToInt(x -> x.getDenomiation()*x.getCapacity())
                    .sum();

            if (validInt>sumInBasket&&validInt<0) {
                err = "Невозможно выдать данную сумму!";
                throw new Exception();
            }

            int minDenomination = this.basket.stream().map(Cell::getDenomiation)
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

    void reduceBasket(List<Cell> moneyIssue ){
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
