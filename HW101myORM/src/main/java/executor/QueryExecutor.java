package executor;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class QueryExecutor {
    private final Connection connection;

    public QueryExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        try(Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
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
