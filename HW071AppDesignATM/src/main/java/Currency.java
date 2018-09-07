public class Currency {
    private final String name;
    private final String [] typesOfBills;


    public Currency(String name, String[] typesOfBill) {
        this.name = name;

        this.typesOfBills = typesOfBill;
    }
    public String getName (){
        return this.name;
    }

    public  String[] getTypeOfBills(){
        return this.typesOfBills;
    }



}
