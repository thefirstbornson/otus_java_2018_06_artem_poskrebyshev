package department;

import atm_components.ATM;

import java.util.List;

public interface Department {
    int collectBalance ();
    List<ATM> getATMList();
    boolean restoreOriginalStates();
    void add(ATM atm);
    void save(ATM atm);
}
