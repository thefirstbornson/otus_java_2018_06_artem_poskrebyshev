import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ATMModelT101 implements ATM{
    private final int DEFAULT_CELLS_COUNT=10;
    private ArrayList<Cell>  basket ;
    public String currentCur;

    public ATMModelT101() {
        basket  = new ArrayList<Cell>(DEFAULT_CELLS_COUNT);
    }

    public void dispenseCash(Card card, int amount) {

    }

    public void acceptsCash(Card card) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.print("Внесите деньги по одной купюре. Для завершения операции нажмите 'q': ");
                String input = br.readLine();
//                try{
//                    int i = Integer.parseInt(br.readLine());
//                }catch(NumberFormatException nfe){
//                    System.err.println("Неверный формат");
//                }

                if ("q".equals(input)) {
                    //System.out.println("Exit!");
                    //System.exit(0);
                    break;
                }
                else{
                    int bill =Integer.parseInt(input);
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
    ATM bankomat = new ATMModelT101();
    ((ATMModelT101) bankomat).fillBasket(new Bill("RUB",100),new Bill("RUB",200)
                                        ,new Bill("RUB",300),new Bill("RUB",500)
                                        ,new Bill("RUB",1000),new Bill("RUB",2000)
                                        ,new Bill("RUB",5000));
    ((ATMModelT101) bankomat).currentCur="RUB";
    bankomat.acceptsCash(card);




    }




}
