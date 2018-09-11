import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;

public class ATMModelT101 implements ATM{
    private final int DEFAULT_CELLS_COUNT=10;
    private ArrayList<Cell>  basket ;
    public String currentCur;

    public ATMModelT101() {
        this.basket  = new ArrayList<Cell>(DEFAULT_CELLS_COUNT);
    }

    public void dispenseCash(Card card) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HashMap<Integer,Integer> cash = new HashMap();
        while (true) {
            try {
                System.out.print("Введите сумму необходимую для снятия. Для выхода из меню нажмите 'q': ");
                String input = br.readLine();

                if ("q".equals(input)) {
                    break;
                }
                else {
                    int amountRequested =Integer.parseInt(input);//обработать по-лучше
                    int minDenomination = this.basket.stream().map(x->x.getDenomiation())
                                                         .filter(x->x>0)
                                                         .min(Integer::min).get();
                    if (amountRequested>=minDenomination && amountRequested%minDenomination==0){
                        for (Cell cell:this.basket) {
                            if (amountRequested>=cell.getDenomiation()&&cell.hasBills(1)) {
                                int cashPortion = (int) amountRequested/cell.getDenomiation();
                                cell.takeOffBill(cashPortion);
                                amountRequested -= cashPortion*cell.getDenomiation();
                                cash.put(cashPortion,cell.getDenomiation());
                            }
                            if (amountRequested==0) {
                                break;
                            }
                        }
                    }
                }

            }
            catch (Exception e){

            }
            System.out.println(cash);
        }

    }

    public void acceptsCash(Card card) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.print("Внесите деньги по одной купюре. Для завершения операции нажмите 'q': ");
                String input = br.readLine();

                if ("q".equals(input)) {
                    break;
                }
                else{
                    int bill =Integer.parseInt(input); //обработать по-лучше
                    if (basket.stream().map(x->x.getDenomiation()).filter(x->x.equals(bill)).findAny().isPresent()){
                        billToCell(new Bill(currentCur, bill));
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
        System.out.println("Конец!");
    }


    public double getBalance(Card card) {
        return card.getBalance();
    }


    public void fillBasket(Bill ... bills) {
        for (Bill bill:bills){
                this.basket.add(new Cell(bill));
            }
        Collections.sort(this.basket);
        Collections.reverse(this.basket);
        }

    private void billToCell(Bill bill){
        for (Cell cell: basket){
            if (bill.getDenomination( )== cell.getDenomiation() && (bill.getcurrancy()==this.currentCur)) {
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

    public static void main(String[] args)  {
        Card card = new Card("AP","5469200011792412","SB","RUB",15001.25);
        ATMModelT101 bankomat = new ATMModelT101();
        bankomat.fillBasket(new Bill("RUB",100),new Bill("RUB",200)
                                            ,new Bill("RUB",300),new Bill("RUB",500)
                                            ,new Bill("RUB",1000),new Bill("RUB",2000)
                                            ,new Bill("RUB",5000));
        bankomat.currentCur="RUB";
        //bankomat.acceptsCash(card);
        bankomat.dispenseCash(card);

    }
}
