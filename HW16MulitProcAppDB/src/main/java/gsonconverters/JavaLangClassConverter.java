package gsonconverters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class JavaLangClassConverter implements  JsonDeserializer<Class> {
    @Override
    public Class deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
       String classFromJson = json.getAsString();
        Class klazz=null;
        try {
            klazz = Class.forName(classFromJson);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return klazz;
    }
}
