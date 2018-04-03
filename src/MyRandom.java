import java.util.Random;

public class MyRandom {

    private static Random random = new Random(123123135);


    /**
     * Returns random int in range [min, max], both including
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandomInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    /**
     * Returns int array filled with random integers in range (1, dimension)
     *
     * @param dimension size of desired array
     * @return int[]
     */
    public static int[] getRandIntArr(int dimension) {
        int[] data = new int[dimension];
        int index = 0;
        while (index < dimension) {
            int rand = MyRandom.getRandomInt(1, dimension);
            data[index] = rand;
            index++;
        }
        return data;
    }

    /**
     * Returns int array filled with random integers in range (1, dimension) with all unique elements
     *
     * @param dimension size of desired array
     * @return int[]
     */
    public static int[] getRandIntNotRepeatableArr(int dimension) {
        int[] data = new int[dimension];
        int index = 0;
        while (index < dimension) {
            int rand = MyRandom.getRandomInt(1, dimension);
            data[index] = rand;
            if (!intArrayContains(data, rand, index)) {
                index++;
            }
        }
        return data;
    }

    /**
     * Returns true if int array contains value under any index exclusive of exclude_position
     *
     * @param arr
     * @param value
     * @param exclude_position
     * @return
     */
    private static boolean intArrayContains(int[] arr, int value, int exclude_position) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value && i != exclude_position) {
                return true;
            }
        }
        return false;
    }
}
