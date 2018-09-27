import javax.json.*;
import java.lang.reflect.Field;
import java.util.Collection;

import static org.apache.commons.lang3.ClassUtils.isPrimitiveOrWrapper;

public class Main {

    public JsonObjectBuilder jsonObjectBuilder;

    public static void main(String[] args) {
        Ad object = new Ad();
        String s="a";
        int сount=5;
        Main m = new Main();
        m.jsonObjectBuilder= Json.createObjectBuilder();
        m.export(object,"root");
        JsonObject js= m.jsonObjectBuilder.build();
        System.out.println(js.toString());

//        JsonObject js= jsonObjectBuilder.build();
//        System.out.println(jsonObjectBuilder.build().toString());
    }

    public void export(Object clazz, String name){


        if (isPrimitiveOrWrapper(clazz.getClass())) {
            jsonObjectBuilder.add(name,clazz.toString());
        } else if (clazz.getClass().equals(String.class)) {
            jsonObjectBuilder.add(name,clazz.toString());
        }  else if (clazz.getClass().isArray()) {
//            for (Object field:clazz) {
//                export(field.getType(), field.getName());
//            }
        }else {

            for (Field field:clazz.getClass().getDeclaredFields()) {
                export(field, field.getName());
            }
        }

       }




}
