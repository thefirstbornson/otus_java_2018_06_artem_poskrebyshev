package datasets;
import javax.persistence.*;

public class PhoneDataSet {

    public PhoneDataSet(String number, UserDataSet userDataSet) {
        this.number = number;
        this.userDataSet = userDataSet;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String number;


    public PhoneDataSet() {
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserDataSet userDataSet;


}
