import java.util.List;
import java.util.Random;

public class TestData {

    public static void setRandValues(List<Integer> list, int sizeOfList){

        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < sizeOfList ; i++) {
            Integer r = rand.nextInt() % 256;
            list.add(r);
        }
    }

}
