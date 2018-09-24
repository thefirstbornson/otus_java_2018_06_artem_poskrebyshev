package atm_components;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ATMModelT101 implements ATM{
    private List<Cell>  basket ;
    private String currentCur;

    public ATMModelT101() {
        int DEFAULT_CELLS_COUNT = 10;
        this.basket  = new ArrayList<>(DEFAULT_CELLS_COUNT);
    }

    public String getCurrentCur() {
        return currentCur;
    }

    public void setCurrentCur(String currentCur) {
        this.currentCur = currentCur;
    }

    @Override
    public int getTotalAmount() {
        return this.basket.stream().mapToInt(x->x.getDenomiation()*x.getCapacity()).sum();
    }

    @Override
    public List<Cell> dispenseCash (Card card, int input){
        List<Cell> moneyIssue=null;
        try {
            if (availableNotes().isEmpty()) throw new NotValidMoneyRequestException("Банкомат работает только на прием наличных!");
            if (!card.isSufficientFunding(input)) throw new NotValidMoneyRequestException("На вашей карте недостаточно средств!!");
            moneyIssue = vaildInput(input);
            reduceBasket(moneyIssue);
            card.withdraw(input);
        } catch (NotValidMoneyRequestException e) {
            System.out.println(e.getMessage());
        }
        return moneyIssue;
    }

    @Override
    public int acceptCash(Card card, int input) {
        int validVolumeCash=0;
        try {
            int bill =input;
            if (!basket.stream().map(Cell::getDenomiation).anyMatch(x->x.equals(bill)))
                throw new NotValidMoneyRequestException("Вы не можете внести такую купюру!");
            validVolumeCash =billToCell(new Bill(currentCur, bill));
            if (validVolumeCash>0) card.deposit(validVolumeCash);
            return validVolumeCash;
        }
        catch (NotValidMoneyRequestException e) {
            System.out.println(e.getMessage());
        }
        return validVolumeCash;
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

    @Override
    public ATMMemento save() {
        return new ATMMemento(this.basket);
    }

    @Override
    public void restore(ATMMemento m) {
        fillBasket(m.getBasketState());
    }
}

class NotValidMoneyRequestException extends Exception {

    public NotValidMoneyRequestException(String message) {
        super(message);
    }
}


