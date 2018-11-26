package datasets;

import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {
   // @NotBlank
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

    public UserDataSet(String name, int age, List<PhoneDataSet> phones, AddressDataSet address) {
        this.name = name;
        this.age = age;
        this.phones = phones;
        this.address = address;
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
    public void setAge(String age) {
        setAge(Integer.parseInt(age));
    }
    public void setAddress(AddressDataSet address) {
        this.address = address;
    }
    public void setAddress(String address) {
        this.address=new AddressDataSet(address, this);
    }
    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }
    public void setPhones(String phones) {
        List<PhoneDataSet> listPhone = new ArrayList<>();
        for (String s : phones.split(",")) {
            listPhone.add(new PhoneDataSet(s, this));
        }
        setPhones(listPhone);
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public AddressDataSet getAddress() {
        return address;
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
