package datasets;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {
    @NotBlank
    @Column(name = "name", nullable= false)
    private String name;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "userDataSet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhoneDataSet> phones = new ArrayList<>();

    @OneToOne(
            mappedBy = "userDataSet",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private AddressDataSet address;

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserDataSet(long id,String name, int age) {
        this.setId(id);
        this.name = name;
        this.age = age;
    }

    public void setId(long id){
        this.id=id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }
    public long getId() {
        return id;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }



    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name= " + name +
                ", age= " + age +
                ", phone numbers = " + phones.stream()
                                             .map(PhoneDataSet::getNumber)
                                             .collect(Collectors.joining(", "))+
                ", address = " + address.getStreet()+'}';
    }
}
