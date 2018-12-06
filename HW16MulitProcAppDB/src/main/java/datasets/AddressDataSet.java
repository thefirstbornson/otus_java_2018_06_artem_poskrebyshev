package datasets;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@Column(name = "street", nullable = false)
private String street;

@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "userDataSet_id")
private UserDataSet userDataSet;

public AddressDataSet(){
}

public AddressDataSet(String street, UserDataSet userDataSet) {
        this.street = street;
        this.userDataSet = userDataSet;
    }

public String getStreet() {
    return street;
}

public void setStreet(String street) {
    this.street = street;
}
}
