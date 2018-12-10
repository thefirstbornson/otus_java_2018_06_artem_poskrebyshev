package gsonconverters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class JavaLangClassConverter implements  JsonDeserializer<Class> {
    @Override
    public Class deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
       String stringFromJson = json.getAsString();
        Class act=null;
        try {
            act = Class.forName("datasets."+stringFromJson);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return act;
    }
}
