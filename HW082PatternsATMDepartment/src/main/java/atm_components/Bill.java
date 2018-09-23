package atm_components;

class Bill {
    private final String currancy;
    private final int denomination;


     Bill(String name, int denomination) {
        this.currancy = name;

        this.denomination = denomination;
    }
     String getcurrancy (){
        return this.currancy;
    }

      int getDenomination(){
        return this.denomination;
    }



}
