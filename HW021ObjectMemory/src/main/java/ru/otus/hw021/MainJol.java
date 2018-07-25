package ru.otus.hw021;

import org.openjdk.jol.info.ClassLayout;
import java.util.*;
import java.util.function.Supplier;

public class MainJol {
    public static void main(String[] args){
        System.out.println("String size: "+ClassLayout.parseInstance(new String()).instanceSize());
        System.out.println("HashMap size: "+ClassLayout.parseInstance(new HashMap()).instanceSize());
        System.out.println("ArrayList size: "+ClassLayout.parseInstance(new ArrayList()).instanceSize());
        System.out.println("Object size: "+ClassLayout.parseInstance(new Object()).instanceSize());
        System.out.println("byte[5] size: "+ClassLayout.parseInstance(new byte[5]).instanceSize());
        System.out.println("Byte[5] size: "+ClassLayout.parseInstance(new Byte[5]).instanceSize());
        System.out.println("HashSet size: "+ClassLayout.parseInstance(new HashSet()).instanceSize());

    }
}
