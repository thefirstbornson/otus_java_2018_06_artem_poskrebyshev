package ru.otus.hw021;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Supplier;

public class MainSupplier {

    public static void main(String[] args) throws InterruptedException {

        calcSize(new Supplier<ArrayList>() {
            public ArrayList get () {
                return new ArrayList();
            }
        });
        calcSize(HashSet::new);
        calcSize(()->new String());
        calcSize(()->new MyClass());
        calcSize(()->new Object());
        calcSize(()->new String(new byte[0]));
        calcSize(()->new byte[5]);
        calcSize(()->new Byte[5]);
        calcSize(()->new HashSet());
    }



    public static void calcSize(Supplier supplier) throws InterruptedException {

        int size = 20_000_000;
        long mem = getMem();
        Object[] array = new Object[size];

        long mem2 = getMem();
        System.out.println("Ref size: " + (mem2 - mem) / array.length);

        // заполняем массив объектами
        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }
        long mem3 = getMem();
        System.out.println(supplier.get().getClass().getSimpleName()+" size: "+(mem3 - mem2) / array.length);
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




