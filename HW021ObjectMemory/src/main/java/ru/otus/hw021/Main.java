package ru.otus.hw021;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * VM options -Xmx512m -Xms512m
 * -XX:+UseCompressedOops //on
 * -XX:-UseCompressedOops //off
 * <p>
 * Runtime runtime = Runtime.getRuntime();
 * long mem = runtime.totalMemory() - runtime.freeMemory();
 * <p>
 * System.gc()
 * <p>
 * jconsole, connect to pid
 */
@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {
    public static void main(String... args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        int size = 20_000_000;

        long mem = getMem();
        System.out.println("Mem: " + mem);

        Object[] array = new Object[size];

        long mem2 = getMem();
        System.out.println("Ref size: " + (mem2 - mem) / array.length);


        for (int i = 0; i < array.length; i++) {
            Supplier<ObjectFactory> objectFactory =  ObjectFactory::new;
            array[i] = objectFactory.get().getObject("ARRAYLIST");
        }

        long mem3 = getMem();
        System.out.println("Element size: " + (mem3 - mem2) / array.length);

        array = null;
        System.out.println("Array is ready for GC");

        Thread.sleep(1000); //wait for 1 sec
//        }
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    static class MyClass {
        private boolean b = true;
        private boolean b1 = true;
        private boolean b2 = true;
        private boolean b3 = true;
        private boolean b4 = true;
    }
}

class ObjectFactory {
    final static Map<String, Supplier<Object>> map = new HashMap<>();
    static {
        map.put("EMPTYSTRING", String::new);
        map.put("MYCLASS", Main.MyClass::new);
        map.put("ARRAYLIST", ArrayList<Double>::new);
        map.put("MAP", HashMap<String,Integer>::new);

    }
    public Object getObject(String ObjectType){
        Supplier<Object> obj = map.get(ObjectType.toUpperCase());
        if(obj != null) {
            return obj.get();
        }
        throw new IllegalArgumentException("No such Object " + ObjectType.toUpperCase());
    }
}
