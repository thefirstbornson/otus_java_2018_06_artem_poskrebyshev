package executor;

import base.DataSetNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException, NoSuchMethodException, IllegalAccessException
            , InvocationTargetException, InstantiationException, DataSetNotFoundException;
}
