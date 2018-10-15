package datasets;

import javax.persistence.*;

@Entity
@Table(name = "adress")
public class AddressDataSet {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "adress", nullable = false)
    private String street;

    public AddressDataSet(String street) {
        this.street = street;
    }
    public AddressDataSet() {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
