package ru.otus.hw04;
import ru.otus.hw04.annotations.*;

import static java.lang.Math.sqrt;


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
        int x = 6;
        int y = 3;
        assert x + y == 9 : "неверный результат сложения";

    }

    @Test
    public void testTwo(){
        System.out.println("testTwo");
        double x = 9;
        assert sqrt(x) == 3.0 : "неверный результат вычисления квадратного корня";

    }

    @Test
    public void testThree(){
        System.out.println("testThree");
        int x = 6;
        int y = 3;
        assert x - y == 3 : "неверный результат вычитания";
    }

    @After
    public void after(){
        System.out.println("After");
    }

}
