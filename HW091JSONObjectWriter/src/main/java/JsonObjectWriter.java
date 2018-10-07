import com.google.gson.Gson;

import javax.json.*;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import static org.apache.commons.lang3.ClassUtils.isPrimitiveOrWrapper;
import static org.apache.commons.lang3.ClassUtils.wrapperToPrimitive;

public class JsonObjectWriter {

    public String toJson(Object obj){
        JsonObjectBuilder jsonObj = export(obj);
        JsonObject jsonst= jsonObj.build();
        return writeToString((JsonObject) jsonst);
    }

    private JsonObjectBuilder export(Object object) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for ( Field field : object.getClass().getDeclaredFields()) {
            try {
                if (field.get(object) != null) {
                    if        (isPrimitiveOrWrapper(field.getType())) {
                              jsonPrimObjBuilder(jsonObjectBuilder, field.get(object), field.getName());
                    } else if (field.get(object) instanceof String) {
                              jsonObjectBuilder.add(field.getName(), field.get(object).toString());
                    } else if (field.getType().isArray()) {
                              jsonObjectBuilder.add(field.getName(), exportArray(field.get(object)));
                    } else if (field.get(object) instanceof Collection) {
                              Collection sourceList = (Collection) field.get(object);
                              jsonObjectBuilder.add(field.getName(),
                                      exportArray(sourceList.toArray(new Object[sourceList.size()])));
                    } else {
                              jsonObjectBuilder.add(field.getName(), export(field.get(object)));
                    }
            }
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return jsonObjectBuilder;
    }

    private JsonArrayBuilder exportArray(Object object) {
        JsonArrayBuilder jsonArraytBuilder = Json.createArrayBuilder();
        int length = Array.getLength(object);
        for (int i = 0; i < length; i++) {
            if (Array.get(object, i) != null) {
                if        (isPrimitiveOrWrapper(Array.get(object, i).getClass())) {
                          jsonPrimArrayBuilder(jsonArraytBuilder, Array.get(object, i));
                } else if (Array.get(object, i).getClass() == String.class) {
                          jsonArraytBuilder.add(Array.get(object, i).toString());
                } else if (Array.get(object, i).getClass().isArray()) {
                          jsonArraytBuilder.add(exportArray(Array.get(object, i)));
                } else if (Array.get(object, i) instanceof Collection) {
                          Collection sourceList = (Collection) Array.get(object, i);
                          jsonArraytBuilder.add(exportArray(sourceList.toArray(new Object[sourceList.size()])));
                } else
                          jsonArraytBuilder.add(export(Array.get(object, i)));
            }
        }
        return jsonArraytBuilder;
    }

    private void jsonPrimArrayBuilder(JsonArrayBuilder jArrBld,Object object){
        Class<?> clazz = wrapperToPrimitive(object.getClass());
        if       (clazz == int.class )    { jArrBld.add( (int) object);
        }else if (clazz == byte.class)    { jArrBld.add( (byte) object);
        }else if (clazz == short.class)   { jArrBld.add( (int)(short) object);
        }else if (clazz == char.class)    { jArrBld.add( (char) object);
        }else if (clazz == long.class)    { jArrBld.add( (long) object);
        }else if (clazz == float.class)   { jArrBld.add( new BigDecimal(Double.toString((double)(float) object)).setScale(6, RoundingMode.HALF_UP).stripTrailingZeros());
        }else if (clazz == double.class)  { jArrBld.add( (double) object);
        }else if (clazz == boolean.class) { jArrBld.add( (boolean) object);
        }
    }
    private void jsonPrimObjBuilder(JsonObjectBuilder jObjBld,Object object, String fieldName){
        Class<?> clazz = wrapperToPrimitive(object.getClass());
        if       (clazz == int.class )    { jObjBld.add(fieldName, (int) object);
        }else if (clazz == byte.class)    { jObjBld.add(fieldName, (byte) object);
        }else if (clazz == short.class)   { jObjBld.add(fieldName, (int)(short) object);
        }else if (clazz == char.class)    { jObjBld.add(fieldName, String.valueOf((char) object));
        }else if (clazz == long.class)    { jObjBld.add(fieldName, (long) object);
        }else if (clazz == float.class)   { jObjBld.add(fieldName, new BigDecimal(Double.toString((double)(float) object)).setScale(6, RoundingMode.HALF_UP).stripTrailingZeros());
        }else if (clazz == double.class)  { jObjBld.add(fieldName, (double) object);
        }else if (clazz == boolean.class) { jObjBld.add(fieldName, (boolean) object);
        }
    }

    private String writeToString(JsonObject jsonst) {
        StringWriter stWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
            jsonWriter.writeObject(jsonst);
        }
        return stWriter.toString();
    }

}



