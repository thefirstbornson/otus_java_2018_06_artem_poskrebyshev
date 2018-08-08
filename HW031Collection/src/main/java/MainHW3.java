import java.util.*;

public class MainHW3 {
    public static void main(String[] args) {

        MyArrayList<Integer> myArrList= new MyArrayList<>();

        //Collections.addAll
        Collections.addAll(myArrList, 122, 56, 4, 8, 15, 16, 23, 0, 123, 1, 2, 3, 4, 5);
        System.out.println(Arrays.toString(myArrList.toArray()));

        // Collections.copy
        List<Integer> myArrDest = Arrays.asList(new Integer[myArrList.size()]);
        Collections.copy(myArrDest,myArrList);
        System.out.println(Arrays.toString(myArrDest.toArray()));

        //Collections.sort
        Collections.sort(myArrList, Comparator.reverseOrder());
        System.out.println(Arrays.toString(myArrList.toArray()));




    }
}
