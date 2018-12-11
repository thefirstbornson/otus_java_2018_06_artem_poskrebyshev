package gsonconverters;

import com.google.gson.*;
import datasets.DataSet;
import datasets.UserDataSet;
import org.reflections.Reflections;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class  DataSetConverter implements JsonDeserializer<DataSet> {
    @Override
    public DataSet deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserDataSet.class, new UserDataSetConverter()).create();

        Reflections reflections = new Reflections();
        Set<Class<? extends List>> classes = reflections.getSubTypesOf(java.util.List.class);
        for (Class<? extends List> aClass : classes) {
            System.out.println(aClass.getName());
        try {
            gson.fromJson(object,aClass);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
//            if(aClass == ArrayList.class) {
//                List list = null;
//                try {
//                    list = aClass.newInstance();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                list.add("test");
//                System.out.println(list.getClass().getName() + ": " + list.size());
//            }
        }

        return null;
    }
}
