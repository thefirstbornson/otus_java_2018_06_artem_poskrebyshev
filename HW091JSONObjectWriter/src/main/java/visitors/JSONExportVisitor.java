package visitors;

import elements.Element;
import elements.PrimitiveOrWrapper;
import javax.json.*;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.apache.commons.lang3.ClassUtils.convertClassesToClassNames;
import static org.apache.commons.lang3.ClassUtils.isPrimitiveOrWrapper;

public class JSONExportVisitor implements JSONVisitor {

    public void  export(List<Element> list) {

      JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();

       for (Element e : list) {
           String[] jsonNode = e.accept(this);
           jsonObjectBuilder.add(jsonNode[0],jsonNode[1]);
       }
        JsonObject js= jsonObjectBuilder.build();
        System.out.println(jsonObjectBuilder.build().toString());
    }

    public List createElementsList(Object object) throws IllegalAccessException {

        Class<?> c = object.getClass();
        Field fields[] = c.getDeclaredFields();
        List <Element> list = new ArrayList<>();

        for (Field field: fields) {
            System.out.println(field.getType());
           if (isPrimitiveOrWrapper(field.getType())){
               list.add(new PrimitiveOrWrapper(field.getName(),field.get(object).toString()));
           }
        }

        return list;
    }

        public String[] visit(PrimitiveOrWrapper primitiveOrWrapper) {
            String[] resultArray = {primitiveOrWrapper.getName(), primitiveOrWrapper.getValue()};
            return resultArray;
                }

    public static void main(String[] args) {
        Object a = new A();
        JSONExportVisitor b = new JSONExportVisitor();

        try {
            List<Element> c = b.createElementsList(a);
//
//            for (PrimitiveOrWrapper el:c) {
//                System.out.println(el.getName() +" "+ el.getValue());
//            }
            b.export(c);
        }
        catch (Exception e){
System.out.println(" error");
        }
    }
}

class A {
    byte b;
    short s;
    int	i;
    long l;
    float f;
    double d;
    char c;
    boolean	boo;

    public A() {
        this.b =1;
        this.s = 2;
        this.i = 3;
        this.l = 4;
        this.f = 5;
        this.d = 6;
        this.c = 7588;
        this.boo = true;
    }
}
//
//Json.createObjectBuilder()
//        .add("firstName", "Duke")
//        .add("age", 28)
//        .add("streetAddress", "100 Internet Dr")
//        .add("phoneNumbers", Json.createArrayBuilder()
//        .add(Json.createObjectBuilder()
//        .add("type", "home")
//        .add("number", "222-222-2222")))
//        .build();