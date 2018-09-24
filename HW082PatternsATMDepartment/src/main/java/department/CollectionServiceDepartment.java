package department;

import atm_components.ATM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionServiceDepartment implements Department{
    private List<ATM> observers = new ArrayList<>();
    private int state;

    public void add(ATM atm) {
        observers.add(atm);
    }

    private HashMap<ATM, ATM.ATMMemento> originalStates =new HashMap<>();

    @Override
    public void save(ATM atm) {
        originalStates.put(atm, atm.save());
    }

    public List<ATM> getATMList(){
        return observers;
    }

    @Override
    public int collectBalance() {
        return observers.stream().mapToInt(ATM::getTotalAmount).sum();
    }


    public void restoreOriginalState(ATM atm) {
            atm.restore(originalStates.get(atm));
    }

    @Override
    public boolean restoreOriginalStates() {
        for (ATM atm:observers) {
            restoreOriginalState(atm);
        }
        return true;
    }
}
