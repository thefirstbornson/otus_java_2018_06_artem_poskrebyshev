package department;

import atm_components.ATM;

import java.util.List;

public interface Department {
    int collectBalance ();
    List<ATM> getATMList();
    boolean restoreOriginalState();
    void add(ATM atm, ATMOriginator atmOriginator, ATMCaretaker atmCaretaker);
}
