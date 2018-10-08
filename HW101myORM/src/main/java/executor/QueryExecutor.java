package executor;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class QueryExecutor {
    private final Connection connection;

    public QueryExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query,Object value, ResultHandler<T> handler) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1,value);
            preparedStatement.executeQuery();
            ResultSet result = preparedStatement.getResultSet();
            return handler.handle(result);
        }
    }

    public int execUpdate(String update, Object ... values) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    Connection getConnection() {
        return connection;
    }

}
