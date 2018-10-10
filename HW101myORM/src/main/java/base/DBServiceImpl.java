package base;

import connection.*;
import executor.QueryExecutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBServiceImpl implements DBService {
    private static final String CREATE_TABLE_USER = "create table if not exists user (id bigint(20) auto_increment" +
                                                                ", name varchar(255), age int(3), primary key (id))";
    private static final String ADD_USER = "insert into user (%s) values (%s)";
    private static final String GET_USER = "select * from user where =";
    private final Connection connection;

    public DBServiceImpl() {
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public <T extends DataSet> void save(T user) {
        QueryExecutor exec = new QueryExecutor(getConnection());
        try {
            long userID = exec.execUpdate(insObjFieldsToInsertQuery(ADD_USER,user),getValuesFromField(user));
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

    private String insObjFieldsToInsertQuery(String query, Object object){
        String fields = Arrays.stream(object.getClass().getDeclaredFields())
                              .map((f) -> f.getName())
                              .collect(Collectors.joining(","));
        String values = Arrays.stream(object.getClass().getDeclaredFields())
                              .map((f) -> "?")
                              .collect(Collectors.joining(","));
        return String.format(query,fields,values);
    }

    private String insFirstMethodParamToQuery (String query,  Object obj, String methodName){
        StringBuilder stringBuilder = new StringBuilder(query);
        Method[] declaredMethods =obj.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.getName().equals(methodName)) {
                Parameter[] parameters = method.getParameters();
                stringBuilder.insert(stringBuilder.indexOf("="),parameters[0].getName())
                            .insert(stringBuilder.lastIndexOf("=")+1,"?");
                break;
            }
        }
        return stringBuilder.toString();
    }

    private List <Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz)  {

        String query =insFirstMethodParamToQuery(GET_USER,this,"load");
        QueryExecutor exec = new QueryExecutor(getConnection());
        T dataset=null;
        try {
            dataset= exec.execQuery(query,id,result->{
                if (!result.next()) {
                        throw new DataSetNotFoundException("No such user(id="+id+") found.");
                }

                Constructor datasetDefaultConstructor = null;
                for (Constructor constructor: clazz.getDeclaredConstructors()) {
                    if (constructor.getGenericParameterTypes().length == 0) {
                        datasetDefaultConstructor = constructor;
                        break;
                    }
                }

                if (datasetDefaultConstructor != null) {datasetDefaultConstructor.setAccessible(true);}
                    else {throw new NullPointerException();}

                T dataset0 = (T) datasetDefaultConstructor.newInstance();

                List<Field> clazzAllFields = getAllFields (new ArrayList<>(),clazz);
                    for(Field fieldClazz: clazzAllFields){
                        fieldClazz.setAccessible(true);
                        fieldClazz.set(dataset0,result.getObject(fieldClazz.getName(),fieldClazz.getType()));
                    }
                 return dataset0;
            });
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

