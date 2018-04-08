import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        //(new LatinSquareFinder(6)).findAllUsingBacktracking();

//        LatinSquare ls = getRandomSquare(6);
//        int index = 1;
//        while (!ls.isCorrect()) {
//            index++;
//            ls = getRandomSquare(4);
//        }
//        System.out.println(ls.toString());
//        System.out.println(ls.isCorrect());
//        System.out.println(index);
//
//

//        LatinSquare ls = (new BackTracking()).findLatinSquare(4);
        LatinSquare ls = (new LatinSquareFinder(8)).findLatinSquare(LatinSquareFinder.METHOD_FORWARDCHECKING);
//        LatinSquare ls = getSquareFromFile("Square.txt");

        for (int i = 0; i < ls.dimension; i++) {
            for (int j = 0; j < ls.dimension; j++) {
                System.out.printf(ls.data[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
        System.out.println(ls.isCorrect());
        System.out.println(ls.rowMaps.size());
        for (int i = 0; i < ls.rowMaps.size(); i++) {
            Map<Integer, Integer> rowMap = ls.rowMaps.get(i);
            for (int key: rowMap.keySet()) {
                System.out.printf(key + ": " + rowMap.get(key) + "   ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }

    public static LatinSquare getSquareFromFile(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int dimension = Integer.valueOf(bufferedReader.readLine().trim()); // read dimension of square from first line
            int[][] result = new int[dimension][dimension];
            bufferedReader.readLine(); // skip empty line

            for (int i = 0; i < dimension; i++) {
                String[] line = bufferedReader.readLine().trim().split("\\s+");
                for (int j = 0; j < dimension; j++) {
                    result[i][j] = Integer.valueOf(line[j]);
                }
            }
            bufferedReader.close();

            return new LatinSquare(dimension, result);
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
            ex.printStackTrace();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            ex.printStackTrace();
        }
        return null;
    }


    static LatinSquare getRandomSquare(int dim) {

        int[][] data = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            data[i] = MyRandom.getRandIntArr(dim);
        }
        return new LatinSquare(dim, data);
    }
}
