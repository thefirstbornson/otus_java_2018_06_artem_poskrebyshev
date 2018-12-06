package datasets;

import javax.persistence.*;

@MappedSuperclass
public abstract class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    public void setId(long id) {
        this.id = id;
    }
}
