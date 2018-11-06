import java.util.Arrays;

public class Main {
    int[] a = {2, 3, 1};
    int[] b = {4, 6, 5};
    int[] c = {9,8,9,2,7,2,1,5,3,4,1,0,3};

    public static void main(String[] args) throws InterruptedException {

//        int[] both = IntStream.concat(Arrays.stream(a), Arrays.stream(b)).toArray();
//        for (int i:both) {
//            System.out.print(i);
//        }
        Main m = new Main();
        Thread t1 = new Thread(()->{m.sort();});
        Thread t2 = new Thread(()->{m.sort();});

        t1.start();
        t2.start();

        t1.join();
        t2.join();
       for (int i:m.c) {
            System.out.print(i);
        }
    }

    synchronized void sort(){
        Arrays.sort(c);
    }
}