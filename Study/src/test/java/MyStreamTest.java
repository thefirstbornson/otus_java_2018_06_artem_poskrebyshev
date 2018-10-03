//import junit.framework.TestResult;
//import org.junit.*;
//import org.junit.rules.TestRule;
//import org.junit.rules.Timeout;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//import static org.junit.Assert.*;
//
//@RunWith(Parameterized.class)
//public class MyStreamTest {
//    int a, b, res;
//
//    public MyStreamTest(int a, int b, int res){
//        this.a =a;
//        this.b =b;
//        this.res =res;
//
//    }
//
//    @Rule
//    public TestRule timeout = new Timeout(1000);
//
//    @Parameterized.Parameters
//    public static Collection numbers(){
//        return Arrays.asList(new Object[][]{{1,2,3},{2,9,11},{3,3,6}});
//    }
//
//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//
//    @Test ()
//    public void testadd() throws InterruptedException {
////        int x =1;
////        int y = 5;
////        int result = new MyStream().add(x,y);
//        assertEquals(a+b,res);
//    }
//    @Ignore
//    @Test(expected = ArithmeticException.class)
//    public void testdiv() throws InterruptedException {
//        int x =10;
//        int y = 0;
//        int result = new MyStream().div(x,y);
//        assertEquals(x/y,result);
//    }
//
//
//}