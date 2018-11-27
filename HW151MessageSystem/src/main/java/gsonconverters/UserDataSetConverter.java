package gsonconverters;

import com.google.gson.*;
import datasets.PhoneDataSet;
import datasets.UserDataSet;

import java.lang.reflect.Type;
import java.util.stream.Collectors;

public class UserDataSetConverter  implements JsonSerializer<UserDataSet>, JsonDeserializer<UserDataSet> {
    public JsonElement serialize(UserDataSet src, Type type,
                                 JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("id", src.getId());
        object.addProperty("name", src.getName());
        object.addProperty("age", String.valueOf(src.getAge()));
        JsonArray phones = new JsonArray();
        object.add("phones",phones);
        for (String phone: src.getPhones().stream().map(PhoneDataSet::getNumber).collect(Collectors.toList())){
            phones.add(phone);
        }

        object.addProperty("address", src.getAddress().getStreet());
        return object;
    }

    public UserDataSet deserialize(JsonElement json, Type type,
                              JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        UserDataSet userDataSet = new UserDataSet();

        userDataSet.setName(object.get("name").getAsString());
        userDataSet.setAge(object.get("age").getAsInt());
        userDataSet.setPhones(object.get("phones").getAsString());
        userDataSet.setAddress(object.get("address").getAsString());

        return userDataSet;

    }
}