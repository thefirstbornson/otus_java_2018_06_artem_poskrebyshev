public class UserDataSet extends DataSet {
    private long id;
    private String name;
    private int age;

    public UserDataSet(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
