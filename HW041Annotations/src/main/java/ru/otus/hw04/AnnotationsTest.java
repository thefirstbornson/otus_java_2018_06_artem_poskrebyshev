package ru.otus.hw04;
import ru.otus.hw04.annotations.*;


public class AnnotationsTest {

    public AnnotationsTest() {
        System.out.println("Call of the constructor");
    }

    @Before
    public void before(){
        System.out.println("Before");
    }

    @Test
    public void testOne(){
        System.out.println("testOne");
    }

    @Test
    public void testTwo(){
        System.out.println("testTwo");
    }

    @Test
    public void testThree(){
        System.out.println("testThree");
    }

    @After
    public void after(){
        System.out.println("After");
    }

}
