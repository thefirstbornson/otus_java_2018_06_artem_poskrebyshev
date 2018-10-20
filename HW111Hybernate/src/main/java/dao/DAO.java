package dao;

import datasets.DataSet;
import datasets.UserDataSet;

public interface DAO {
    <T extends DataSet> void save (T  dataset);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
