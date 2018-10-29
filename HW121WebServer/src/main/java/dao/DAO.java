package dao;

import datasets.DataSet;
import datasets.UserDataSet;

import java.util.List;

public interface DAO {
    <T extends DataSet> void save (T  dataset);
    <T extends DataSet> T load(long id, Class<T> clazz);
    <T extends DataSet> List<T> readAll(Class<T> clazz);
}
