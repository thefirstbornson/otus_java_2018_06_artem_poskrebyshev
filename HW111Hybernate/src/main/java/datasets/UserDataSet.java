package datasets;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

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

    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name= " + name +
                ", age= " + age + '}';
    }
}
