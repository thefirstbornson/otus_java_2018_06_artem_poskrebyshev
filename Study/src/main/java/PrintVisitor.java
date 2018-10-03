import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class PrintVisitor implements Visitor {
    public static void main(String[] args) {
        List list = new ArrayList(Arrays.asList(1f,3f,5f,66f,6f,6f));
        TestClassA a = new TestClassA();
        TestClassB b = new TestClassB();
        PrintVisitor pv =new PrintVisitor();
        pv.visit(b);
//        String s = "[[Ljava.lang.Integer";
//        System.out.println(s.lastIndexOf("[["));
//        System.out.println(s.substring(s.lastIndexOf("[4[")));
    }

    public void visitFloat(Float _float) {
            System.out.println(_float.toString()+"f");
    }

    public void visitInteger(Integer _int) {
        System.out.println(_int.toString());
    }

    public void visitByte(Byte _byte) {
        System.out.println(_byte.toString());
    }

    public void visitShort(Short _short) {
        System.out.println(_short.toString());
    }

    public void visitLong(Long _long) {
        System.out.println(_long.toString());
    }

    public void visitDouble(Double _double) {
        System.out.println(_double.toString());
    }

    public void visitCharacter(Character _char) {
        System.out.println(_char.toString());
    }

    public void visitBoolean(Boolean _boolean) {
        System.out.println(_boolean.toString());
    }

    public void visitString(String string) {
        System.out.println("'"+string+"'");
    }

    public void visitArray (Object array){
     List list = Arrays.asList(array);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            visit(o);
        }
    }

    public void visitList(List list) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            visit(o);
        }
    }

    public void visitObject(Object obj)
    {
        for (Field field: obj.getClass().getDeclaredFields()){
            try {
                visit(field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void visit(Object object) {
        try {
            Method method = getMethod(object.getClass());
            method.invoke(this, new Object[] {object});
        } catch (NullPointerException e) {
            System.out.println("null");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Method getMethod(Class c) {
        Class newc = c;
        Method m = null;
        while (m == null && newc != Object.class) {
            String method = newc.getName();
            if (method.lastIndexOf("[[")<0) {
                method = "visit" + method.substring(method.lastIndexOf('.') + 1);
            } else {
                method = "visitArray";
                try {
                    m = getClass().getMethod(method, new Class[] {Object.class});
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            }
            try {
                m = getClass().getMethod(method, new Class[] {newc});
            } catch (NoSuchMethodException e) {
                newc = newc.getSuperclass();
            }
        }
        if (newc == Object.class) {
            Class[] interfaces = c.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                try {
                    String method = interfaces[i].getName();
                    method = "visit" + method.substring(method.lastIndexOf('.') + 1);
                    m = getClass().getMethod(method, new Class[] {interfaces[i]});
                    break;
                } catch (NoSuchMethodException e) {}
            }
        }
        if (m == null) {
            try {
                m = getClass().getMethod("visitObject", new Class[] {Object.class});
            } catch (Exception e) {
            }
        }
        return m;
    }
}