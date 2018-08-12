package ru.otus.hw04;

public class TestRunner {
    public static void main(String[] args) {
        TestFramework testFramework = new TestFramework();
        testFramework.run(AnnotationsTest.class);
    }
}
