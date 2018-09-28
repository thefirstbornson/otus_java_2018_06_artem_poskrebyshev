import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import static org.apache.commons.lang3.ClassUtils.isPrimitiveOrWrapper;
import static org.apache.commons.lang3.ClassUtils.primitiveToWrapper;
import static org.apache.commons.lang3.ClassUtils.wrapperToPrimitive;

public class Main {



    public static void main(String[] args) {

            Class<?> intClazz = wrapperToPrimitive(Integer.class);
        System.out.println(intClazz);
        JsonObjectBuilder jsonObj;
        Bb object = new Bb();
        String s="a";
        int сount=5;
        Main m = new Main();
        jsonObj = m.export(object);
        JsonObject js= jsonObj.build();
        System.out.println(js.toString());
    }

    public JsonObjectBuilder export(Object object) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        for (Field field : object.getClass().getDeclaredFields()) {
            if (isPrimitiveOrWrapper(field.getType())) {
                try {
                        jsonObjectBuilder.add(field.getName(), field.get(object).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (field.getType().isArray()) {
                try {
                jsonObjectBuilder.add(field.getName(), exportArray(field.get(object)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    jsonObjectBuilder.add(field.getName(), export(field.get(object)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObjectBuilder;
    }

    public JsonArrayBuilder exportArray(Object object) {

        JsonArrayBuilder jsonArraytBuilder = Json.createArrayBuilder();
        int length = Array.getLength(object);
        for (int i = 0; i < length; i++){
//            if (isPrimitiveOrWrapper(Array.get(object, i).getClass())) {
//                jsonArraytBuilder.add((JsonValue) Array.get(object, i));
//            } else
                jsonArraytBuilder.add(export((Object) Array.get(object, i)));
        }
        return jsonArraytBuilder;
    }
}
