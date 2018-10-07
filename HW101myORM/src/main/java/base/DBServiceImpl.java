package base;

import connection.*;
import executor.QueryExecutor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;


public class DBServiceImpl implements DBService {
    private static final String CREATE_TABLE_USER = "create table if not exists user (id bigint(20) auto_increment" +
                                                                ", name varchar(255), age int(3), primary key (id))";
    private static final String ADD_USER = "insert into user () values ()";
    private static final String GET_USER = "select * from user where id='%d'";
    private final Connection connection;

    public DBServiceImpl() {
        connection = ConnectionHelper.getConnection();
    }


    @Override
    public <T extends DataSet> void save(T user) {
        QueryExecutor exec = new QueryExecutor(getConnection());
        try {
            long userID = exec.execUpdate(modifyQuery(user,ADD_USER),getValuesFromField(user));
            user.setId(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User added.  " + user);
    }

    private Object[] getValuesFromField (Object object){
        int fldCount= object.getClass().getDeclaredFields().length;
        Object[] array= new Object[fldCount];
        try {
            int index=0;
            for(Field f :object.getClass().getDeclaredFields()){
                f.setAccessible(true);
                array[index++]= f.get(object);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return array;
    }

    private String modifyQuery (Object object, String query){
        StringBuilder stringBuilder = new StringBuilder(query);
        int fldCount= object.getClass().getDeclaredFields().length;
        Object[] array= new Object[fldCount];
            int index=0;
            String sprt;
            for(Field f :object.getClass().getDeclaredFields()){
                sprt = index>0 ? "," : "";
                f.setAccessible(true);
                stringBuilder.insert(stringBuilder.indexOf(")"),sprt+f.getName())
                           .insert(stringBuilder.lastIndexOf(")"),sprt+"?");
                index++;
            }
            query =stringBuilder.toString();
        return query;
    }

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
                 return clazz.getDeclaredConstructor(parameterTypes).newInstance(initargs);
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
    }

    protected Connection getConnection() {
        return connection;
    }
}
