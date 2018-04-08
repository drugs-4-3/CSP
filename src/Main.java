import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        int dimension = 12;
        LatinSquareFinder lsf = new LatinSquareFinder(dimension);

        long t1 = System.currentTimeMillis();
        LatinSquare lsbt = lsf.findLatinSquare(LatinSquareFinder.METHOD_BACKTRACKING);
        long t2 = System.currentTimeMillis();


        long t3 = System.currentTimeMillis();
        LatinSquare lsfc = lsf.findLatinSquare(LatinSquareFinder.METHOD_FORWARDCHECKING);
        long t4 = System.currentTimeMillis();

        System.out.println("### BACKTRACKING ###");
        System.out.println(lsbt.toString());
        System.out.println("is correct? " + lsbt.isCorrect());
        System.out.println("Execution time: " + (t2 - t1) + "ms.");
        System.out.printf("\n\n");

        System.out.println("### FORWARDCHECKING ###");
        System.out.println(lsfc.toString());
        System.out.println("is correct? " + lsfc.isCorrect());
        System.out.println("Execution time: " + (t4 - t3) + "ms.");
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
