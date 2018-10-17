package datasets;

import javax.persistence.*;

@Entity
@Table(name = "phone_numbers")
public class PhoneDataSet {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "phone_number", nullable = false)
    private String number;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserDataSet userDataSet;

    public String getNumber() {
        return number;
    }

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number, UserDataSet userDataSet) {
            this.number = number;
            this.userDataSet = userDataSet;
        }
    }
