import java.util.*;

public class TMain {

    public static void main(String[] args) throws InterruptedException {

        List<Integer> source =  new ArrayList<>();
        TestData.setRandValues(source,100);

        source.forEach(x->System.out.print(" "+x));

        new ThreadSort().sortListByThread(source,4);

        System.out.println();
        source.forEach(x->System.out.print(" "+x));

    }


}

