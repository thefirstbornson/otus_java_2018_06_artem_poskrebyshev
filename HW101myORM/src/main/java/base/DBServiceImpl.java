package base;

import connection.*;
import executor.QueryExecutor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;


public class DBServiceImpl implements DBService {
    private static final String CREATE_TABLE_USER = "create table if not exists user (id bigint(20) auto_increment" +
                                                                ", name varchar(255), age int(3), primary key (id))";
    private static final String ADD_USER = "insert into user (name,age) values ('%s','%s')";
    private static final String GET_USER = "select * from user where id='%d'";
    private final Connection connection;

    public DBServiceImpl() {
        connection = ConnectionHelper.getConnection();
    }


    @Override
    public <T extends DataSet> void save(T user) {
        QueryExecutor exec = new QueryExecutor(getConnection());
        int rows = 0;
        try {
            rows = exec.execUpdate(String.format(ADD_USER
                                                ,getValueFromField(user,"name")
                                                ,getValueFromField(user,"age")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User added. Rows changed: " + rows);
    }

    private <T> T getValueFromField (Object object, String field){
        T value=null;
        try {
            Field f =object.getClass().getDeclaredField(field);
            f.setAccessible(true);
            value= (T) f.get(object);
        } catch (IllegalAccessException|NoSuchFieldException e) {
            e.printStackTrace();
        }
        return value;
    };

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz)  {

        QueryExecutor exec = new QueryExecutor(getConnection());
        T dataset=null;
        try {
            dataset= exec.execQuery(String.format(GET_USER,id), result->{
                result.next();
                Class<?>[] parameterTypes = new Class<?>[clazz.getDeclaredFields().length];
                Object[]   initargs = new Object[clazz.getDeclaredFields().length];
                int index=0;
                for (Field field: clazz.getDeclaredFields()){
                    parameterTypes[index]= field.getType();
                    initargs[index]= result.getObject(field.getName(),field.getType());
                    index++;
                }
                 return (T) clazz.getDeclaredConstructor(parameterTypes).newInstance(initargs);
            });
        } catch (SQLException e) {
            System.out.println("Не найдет пользователь с id - " + id)  ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }
    @Override
    public void close() throws Exception {
        connection.close();
    }

    protected Connection getConnection() {
        return connection;
    }
}
