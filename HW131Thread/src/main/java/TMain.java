import java.util.*;

public class TMain {
    int[] a = {2, 3, 1};
    int[] b = {4, 6, 5};
    int[] c = {9,8,9,2,7,2,1,5,3,4,1,0,3};

    public static void main(String[] args) throws InterruptedException {

//        int[] both = IntStream.concat(Arrays.stream(a), Arrays.stream(b)).toArray();
//        for (int i:both) {
//            System.out.print(i);
//        }
       TMain m = new TMain();
//        Thread t1 = new Thread(()->{m.sort();});
//        Thread t2 = new Thread(()->{m.sort();});
//
//        t1.start();
//        t2.start();
//
//        t1.join();
//        t2.join();
//       for (int i:m.c) {
//            System.out.print(i);
//        }
        int sourceSize =101;
        int countOfPartitions = 4;
        int lenOfSubList = sourceSize%countOfPartitions==0
                           ? sourceSize/countOfPartitions
                           : sourceSize/countOfPartitions+1;

        ArrayList source = new ArrayList(sourceSize);
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i=0; i<sourceSize; i++)
        {
            Integer r = rand.nextInt() % 256;
            source.add(r);
        }
       // source.stream().forEach(System.out::print);

       // Collections.sort(source);

        ArrayList<List> subLists = new ArrayList(countOfPartitions);
        int i=0;
        int j=lenOfSubList;
        while (sourceSize>i){
            if (source.size()-i>lenOfSubList){
                subLists.add(source.subList(i,j));
                i += lenOfSubList;
                j += lenOfSubList;
            }
            else {
                subLists.add(source.subList(i,source.size()));
                i=source.size();
            }
        }

        System.out.println();
        for (List list: subLists) {
            Thread t = new Thread(()->{Collections.sort(list);});
            t.start();
            t.join();
            list.stream().forEach(System.out::print);
            System.out.println();
        }
        System.out.println();
        List l = m.merge(subLists);

    }

     List merge(ArrayList<List> subLists){
        List res = new ArrayList(subLists.get(0));
        List src = new ArrayList(subLists.get(1));
         //for(int i=0;i<res.size();i++){
             int j=0;
             while (j<res.size()){
                if ( (int)res.get(j) > (int) src.get(0)) {
                    res.add(j,src.get(0));
                    src.remove(0);
                }
                j++;
             }
             res.addAll(src);
            // for(int j=1;j<subLists.size();j++){

            //}
       // }


       return null;
    }
}