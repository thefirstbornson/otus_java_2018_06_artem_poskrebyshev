import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadSort {

    private int getLenOfSubList(int size, int chunk) {
        return    size % chunk == 0
                ? size / chunk
                : size / chunk + 1;
    }

    private List<List<Integer>> splitList(List<Integer> list, int chunk){
        List<List<Integer>> subLists =new ArrayList<>(chunk);
        int i = 0;
        int j = getLenOfSubList(list.size(), chunk);
        while (list.size() > i) {
            if (chunk - i > getLenOfSubList(list.size(), chunk)) {
                subLists.add(list.subList(i, j));
                i +=  getLenOfSubList(list.size(), chunk);
                j +=  getLenOfSubList(list.size(), chunk);
            } else {
                subLists.add(list.subList(i, list.size()));
                i = list.size();
            }
        }
        return subLists;
    }

    private List<Integer> merge(List<List<Integer>> subLists) {
        List<Integer> res = new ArrayList<>(subLists.get(0));
        for (int k = 1; k < subLists.size(); k++) {
            List<Integer> src = new ArrayList<>(subLists.get(k));
            int j = 0;
            while (j < res.size() && src.size() > 0) {
                if (res.get(j) > src.get(0)) {
                    res.add(j, src.get(0));
                    src.remove(0);
                }
                j++;
            }
            res.addAll(src);
        }
        return res;
    }

    void sortListByThread(List<Integer> list, int chunk) throws InterruptedException {

        List<List<Integer>> subLists= splitList(list, chunk);
        List<Thread> threadList= new ArrayList<>();

        for (List<Integer> subList:subLists){
            threadList.add(new Thread(() -> {
                Collections.sort(subList);}));
        }

        for (Thread thread:threadList) {
            thread.start();
        }

        for (Thread thread:threadList) {
            thread.join();
        }
        list = merge(subLists);
    }


}
