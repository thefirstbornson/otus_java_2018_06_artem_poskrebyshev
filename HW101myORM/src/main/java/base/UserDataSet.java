package base;

class UserDataSet extends DataSet {
    private long id;
    private String name;
    private int age;

    public UserDataSet(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
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

    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name='" + name +
                ", age" + age +'\'' +
                '}';
    }
}
