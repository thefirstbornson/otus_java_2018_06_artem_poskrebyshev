package vistor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class ObjectVisitor implements Visitor {

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

    public void visitDouble(Double _double) { System.out.println(_double.toString()); }

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
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            visit(Array.get(array, i));
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
            if (object.getClass().isArray()) {
                visitArray(object);
            } else {
                Method method = getMethod(object.getClass());
                method.invoke(this, new Object[] {object});
            }
        } catch (NullPointerException e) {
            System.out.println("null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Method getMethod(Class c) {
        Class newc = c;
        Method m = null;
        while (m == null && newc != Object.class) {
            String method = newc.getName();
            method = "visit" + method.substring(method.lastIndexOf('.') + 1);
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