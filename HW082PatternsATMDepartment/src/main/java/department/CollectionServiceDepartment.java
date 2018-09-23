package department;

import atm_components.ATM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionServiceDepartment implements Department{
    private List<ATMRestoreStructure> observers = new ArrayList<>();
    private int state;

    public void add(ATM atm, ATMOriginator atmOriginator, ATMCaretaker atmCaretaker) {
        observers.add(new ATMRestoreStructure(atm, atmOriginator,atmCaretaker));
    }

    public List<ATM> getATMList(){
        return observers.stream().map(ATMRestoreStructure::getAtm).collect(Collectors.toList());
    }

    @Override
    public int collectBalance() {
        return getATMList().stream().mapToInt(ATM::getTotalAmount).sum();
    }

    @Override
    public boolean restoreOriginalState() {
        try {
            for (ATMRestoreStructure observer : observers) {
                observer.getAtm().fillBasket(observer.getAtmOriginator()
                        .restore(observer.getAtmCaretaker()
                                .getOriginalState()));
            }
            return true;
        } catch (Exception e){
        }

        return false;
    }
}
