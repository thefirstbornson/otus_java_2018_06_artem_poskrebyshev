import com.google.gson.Gson;

public class Main {


    public static void main(String[] args) {

        TestClassA objectA = new TestClassA();
        TestClassB objectB = new TestClassB();
        JsonObjectWriter jow = new JsonObjectWriter();
        Gson gson = new Gson();

        String jsonA = jow.toJson(objectA);
        System.out.println("jsonObjWriter  "+jsonA);
        String gsonA = gson.toJson(objectA);
        System.out.println("gson           "+gsonA);

        String jsonB = jow.toJson(objectB);
        System.out.println("jsonObjWriter -"+jsonB);
        String gsonB = gson.toJson(objectB);
        System.out.println("gson           "+gsonB);
    }
}

