package datasets;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "userDataSet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhoneDataSet> phones = new ArrayList<>();

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        //this.setId(-1);
        this.name = name;
        this.age = age;
    }

    public UserDataSet(long id,String name, int age) {
        this.setId(id);
        this.name = name;
        this.age = age;
    }


    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name= " + name +
                ", age= " + age +
                ", phone numbers = " + phones.stream()
                                             .map(f->f.getNumber())
                                             .collect(Collectors.joining(", "))+'}';
    }
}
