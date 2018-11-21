package datasets;

import java.util.ArrayList;
import java.util.List;

public class DataSetHelper {

    public static void setDataSetField (DataSet dataSet, String fieldName, String value){

    if (dataSet instanceof UserDataSet){
        UserDataSet data = (UserDataSet) dataSet;
        switch (fieldName) {
            case "name":
                data.setName(value);
                break;
            case "age":
                try {
                    data.setAge(Integer.parseInt(value));
                }catch ( NumberFormatException e){
                    e.printStackTrace();
                    data.setAge(0);
                }
                break;
            case "address":
                data.setAddress(new AddressDataSet(value, data));
                break;
            case "phones":
                List<PhoneDataSet> listPhone = new ArrayList<>();
                for (String s : value.split(",")) {
                    listPhone.add(new PhoneDataSet(s, data));
                }
                data.setPhones(listPhone);
                break;
            }
    }
    }
}
