package base;

import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {
    String getMetaData();
    <T extends DataSet> void save(T user);
    <T extends DataSet> T load(long id, Class<T> clazz);

//    void prepareTables() throws SQLException;
//
//    void addUsers(String... names) throws SQLException;
//
//    String getUserName(int id) throws SQLException;
//
//    List<String> getAllNames() throws SQLException;
//
//    List<UserDataSet> getAllUsers() throws SQLException;
//
//    void deleteTables() throws SQLException;
}
