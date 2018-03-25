import java.util.Random;

public class MyRandom {

    private static Random random = new Random(123123135);


    public static int getRandomInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    public static int[] getRandIntArr(int dimension) {
        int[] data = new int[dimension];
        int index = 0;
        while (index < dimension) {
            int rand = MyRandom.getRandomInt(1, dimension);
            data[index] = rand;
            index++;
//            if (!intArrayContains(data, rand, index)) {
//                index++;
//            }
        }
        return data;
    }
}
