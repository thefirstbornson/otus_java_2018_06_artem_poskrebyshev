package ru.otus.hw021;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Supplier;

public class MainFactory {

    public static void main(String[] args) throws InterruptedException {
        calcSize(new StringFactory());
        calcSize(new ArrayListFactory());
        calcSize(new MyClassFactory());
        calcSize(new EmptyObjectFactory());
        calcSize(new ByteArrayFactory()); //byte[5]
        calcSize(new ByteWrapperArrayFactory()); // Byte[5]

        calcSize(new Factory() {
            @Override
            public Object createObject() {
                return new int[5];
            }
        });

        calcSize(() -> new long[5]);
    }



    public static void calcSize(Factory factory) throws InterruptedException {

        int size = 20_000_000;
        long mem = getMem();
        Object[] array = new Object[size];

        long mem2 = getMem();
        System.out.println("Ref size: " + (mem2 - mem) / array.length);

        // заполняем массив объектами
        for (int i = 0; i < array.length; i++) {
            array[i] = factory.createObject();
        }
        long mem3 = getMem();
        System.out.println(factory.createObject().getClass().getSimpleName() + " size: " + (mem3 - mem2) / array.length);
        array = null;
        Thread.sleep(1000); //wait for 1 sec
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}

interface Factory {
    Object createObject();
}

class StringFactory implements Factory {
    @Override
    public Object createObject() {
        return new String();
    }
}

class EmptyObjectFactory implements Factory {
    @Override
    public Object createObject() {
        return new Object();
    }
}

class ArrayListFactory implements Factory {
    @Override
    public Object createObject() {
        return new ArrayList<>();
    }
}

class MyClassFactory implements Factory {
    @Override
    public Object createObject() {
        return new MyClass();
    }
}

class ByteWrapperArrayFactory implements Factory {
    @Override
    public Object createObject() {
        return new Byte[5];
    }
}

class ByteArrayFactory implements Factory {
    @Override
    public Object createObject() {
        return new byte[5];
    }
}

class MyClass {
    private byte b ;     // +1
    private byte b1 = 0;     // +1
    private byte b2= 0;     // +1
    private byte b3 = 0;     // +1
    private byte b4 = 0;     // +1
//    private int i = 0;      // +4
//    private int i1 = 0;      // +4
//    private long l = 1;     // +8
//    private long l1 = 1;     // +8
//    private long l2 = 1;
}



