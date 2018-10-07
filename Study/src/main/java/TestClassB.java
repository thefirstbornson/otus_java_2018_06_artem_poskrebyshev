import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    int[] a;;
    String stroka ="stroka2";
    String massiv[] ={"stroka1","str","adsf"};
    public TestClassB() {
         a = new int[]{1, 3, 5};
        this.b =1;
        this.s = 2;
        this.i = 3;
        this.l = 4;
        this.f = 5;
        this.d = 6;
        this.c = 7588;
        this.boo = true;
        this.testClassA = new TestClassA();
        numbers= new ArrayList<>(Arrays.asList(this.testClassA, this.testClassA));

    }
}
