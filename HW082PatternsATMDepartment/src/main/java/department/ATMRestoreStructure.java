package department;

import atm_components.ATM;

 class ATMRestoreStructure {
    private ATM atm;
    private ATMOriginator atmOriginator;
    private ATMCaretaker atmCaretaker;

     ATMRestoreStructure(ATM atm, ATMOriginator atmOriginator, ATMCaretaker atmCaretaker) {
        this.atm = atm;
        this.atmOriginator = atmOriginator;
        this.atmCaretaker = atmCaretaker;

    }
         ATM getAtm() {
        return atm;
    }

     ATMOriginator getAtmOriginator() {
        return atmOriginator;
    }

     ATMCaretaker getAtmCaretaker() {
        return atmCaretaker;
    }








}
