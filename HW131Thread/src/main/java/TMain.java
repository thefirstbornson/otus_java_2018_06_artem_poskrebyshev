import java.util.*;

public class TMain {
    int[] a = {2, 3, 1};
    int[] b = {4, 6, 5};
    int[] c = {9, 8, 9, 2, 7, 2, 1, 5, 3, 4, 1, 0, 3};

    public static void main(String[] args) throws InterruptedException {
        TMain m = new TMain();

        int sourceSize = 10;
        int countOfPartitions = 4;
        int lenOfSubList = sourceSize % countOfPartitions == 0
                ? sourceSize / countOfPartitions
                : sourceSize / countOfPartitions + 1;

        ArrayList source = new ArrayList(sourceSize);
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < sourceSize; i++) {
            Integer r = rand.nextInt() % 256;
            source.add(r);
        }
        source.stream().forEach(System.out::print);


        ArrayList<List> subLists = new ArrayList(countOfPartitions);
        int i = 0;
        int j = lenOfSubList;
        while (sourceSize > i) {
            if (source.size() - i > lenOfSubList) {
                subLists.add(source.subList(i, j));
                i += lenOfSubList;
                j += lenOfSubList;
            } else {
                subLists.add(source.subList(i, source.size()));
                i = source.size();
            }
        }

        System.out.println();
        for (List list : subLists) {
            Thread t = new Thread(() -> {
                Collections.sort(list);
            });
            t.start();
            t.join();
            list.stream().forEach(System.out::print);
            System.out.println();
        }
        System.out.println();
        m.merge(subLists).stream().forEach(System.out::println);

    }

    List merge(ArrayList<List> subLists) {
        List res = new ArrayList(subLists.get(0));
        for (int k = 1; k < subLists.size(); k++) {
            List src = new ArrayList(subLists.get(k));
            int j = 0;
            while (j < res.size() && src.size() > 0) {
                if ((int) res.get(j) > (int) src.get(0)) {
                    res.add(j, src.get(0));
                    src.remove(0);
                }
                j++;
            }
            res.addAll(src);
        }
        return res;
    }
}

