package ru.otus.hw05;

import java.lang.management.ManagementFactory;

/*
-Xms512m
-Xmx512m
-XX:MaxMetaspaceSize=25m
-Xlog:gc*:file=./HW051GC/logs/gc_pid_%p.log

 -XX:+UseSerialGC
 -XX:+UseParallelGC  -XX:+UseParallelOldGC
 -XX:+UseConcMarkSweepGC
 */

    public class Main {
        public static void main(String... args) {
            System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

            int size = 5 * 1000 * 1000;

            Benchmark benchmark = new Benchmark();

            benchmark.setSize(size);
            benchmark.run();
        }

    }


