import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LatinSquare {

    public int dimension;

    public int[][] data;

    public List<Map<Integer, Integer>> rowMaps;
    public List<Map<Integer, Integer>> columnMaps;


    public LatinSquare(int dimension, int[][] data) {
        this.dimension = dimension;
        this.data = data;

        rowMaps = new ArrayList<Map<Integer, Integer>>(dimension);
        columnMaps = new ArrayList<Map<Integer, Integer>>(dimension);
    }

    public boolean isCorrect() {
        boolean result = true;

        // check if dimensions are logically correct
        if (dimension != data[0].length) {
            return false;
        }
        generateMaps();
        return verifyMaps();
    }

    public Map<Integer, Integer> mapOccurances(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int key : arr) {
            if (map.containsKey(key)) {
                int occurrence = map.get(key);
                occurrence++;
                map.put(key, occurrence);
            } else {
                map.put(key, 1);
            }
        }
        return map;
    }

    private void generateMaps() {
        // creating column and row maps

        // check if maps are not exisisting already

        for(int i = 0; i < dimension; i++) {
            Map<Integer, Integer> rowMap = mapOccurances(data[i]);

            int[] col = new int[dimension];
            for (int j = 0; j < dimension; j++) {
                col[j] = data[j][i];
            }
            Map<Integer, Integer> colMap = mapOccurances(col);

            rowMaps.add(rowMap);
            columnMaps.add(colMap);
        }

    }

    private boolean verifyMaps() {
        for (Map<Integer, Integer> rowMap: rowMaps) {
            for (int k: rowMap.keySet()) {
                if (rowMap.get(k) != 1) {
                    return false;
                }
            }
        }
        for (Map<Integer, Integer> colMap: columnMaps) {
            for (int k: colMap.keySet()) {
                if (colMap.get(k) != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append((String)(data[i][j] + " "));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
