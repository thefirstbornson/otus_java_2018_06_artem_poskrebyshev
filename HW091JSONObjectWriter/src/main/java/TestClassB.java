import java.util.*;

class TestClassB {
    List<TestClassA> numbers;
    byte b;
    short s;
    int	i;
    long l;
    float f;
    double d;
    char c;
    boolean	boo;
    TestClassA testClassA;
    int a[]={1,3,5};;
    String stroka ="stroka1";
    String massiv[] ={"stroka1","str","adsf"};

    public TestClassB() {

        this.b =1;
        this.s = 2;
        this.i = 3;
        this.l = 4;
        this.f = 5;
        this.d = 6;
        this.c = 7588;
        this.boo = true;
        this.testClassA = new TestClassA();
        numbers= Arrays.asList(this.testClassA, this.testClassA);
//        set  = new HashSet<>();
//        set.add(1);
//        set.add(2);
    }
}
