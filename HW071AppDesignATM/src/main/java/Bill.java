public class Bill {
    private final String currancy;
    private final int denomination;


    public Bill(String name, int denomination) {
        this.currancy = name;

        this.denomination = denomination;
    }
    public String getcurrancy (){
        return this.currancy;
    }

    public  int getDenomination(){
        return this.denomination;
    }



}
