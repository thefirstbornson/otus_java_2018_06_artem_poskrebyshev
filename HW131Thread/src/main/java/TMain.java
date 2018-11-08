import java.util.*;

public class TMain {
    private final int sourceSize = 11;
    private final int  countOfPartitions = 5;
    private final int  lenOfSubList ;

    public TMain() {
        lenOfSubList = sourceSize % countOfPartitions == 0
                ? sourceSize / countOfPartitions
                : sourceSize / countOfPartitions + 1;
    }

    public int getSourceSize() {
        return sourceSize;
    }

    public int getCountOfPartitions() {
        return countOfPartitions;
    }

    public int getLenOfSubList() {
        return lenOfSubList;
    }

    public List<Integer> createAndSetRandValues(int sizeOfList){
        List<Integer> list = new ArrayList<>(sizeOfList);
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < sizeOfList ; i++) {
            Integer r = rand.nextInt() % 256;
            list.add(r);
        }
        return list;
    }

    public List<List<Integer>> splitList (List<Integer> list,int chunk){
        List<List<Integer>> subLists =new ArrayList<>(chunk);
        int i = 0;
        int j = this.getLenOfSubList();
        while (this.getSourceSize() > i) {
            if (chunk - i > this.getLenOfSubList()) {
                subLists.add(list.subList(i, j));
                i +=  this.getLenOfSubList();
                j +=  this.getLenOfSubList();
            } else {
                subLists.add(list.subList(i, list.size()));
                i = list.size();
            }
        }
        return subLists;
    }

    public void sortListByThread (List<Integer> list) throws InterruptedException {
        Thread t = new Thread(() -> {
            Collections.sort(list);
        });
        t.start();
        t.join();
    }

    List<Integer> merge(List<List<Integer>> subLists) {
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

    public static void main(String[] args) throws InterruptedException {
        TMain m = new TMain();

        List<Integer> source = m.createAndSetRandValues(m.getSourceSize());
        source.forEach(x->System.out.print(" "+x));
        List<List<Integer>> subLists =m.splitList(source,m.getCountOfPartitions());

        System.out.println();

        for (List<Integer> list : subLists) {
            m.sortListByThread(list);
        }

        System.out.println();

        m.merge(subLists).forEach(x->System.out.print(" "+x));

    }


}

