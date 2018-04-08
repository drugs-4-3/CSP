import sun.awt.image.ImageWatched;

import java.util.*;

public class LatinSquare {

    public int dimension;
    public int[][] data;
    private boolean changed;
    private boolean is_correct;

    /**
     * For each column and rows contains map, such that:
     * key is value in range (1, dimension)
     * value is how many times this value appears in given column/row
     */
    public List<Map<Integer, Integer>> rowMaps;
    public List<Map<Integer, Integer>> columnMaps;

    /**
     * This contains lists of available values for each variable specified by x, y
     */
    LinkedList<Integer>[][] domains;

    /**
     * Contains already tried values for each variable - need to keep track of them to avoid loops
     */
    LinkedList<Integer>[][] tried_values;


    /**
     * Returns square filled with zeros
     *
     * @param dimension
     */
    public LatinSquare(final int dimension) {
        this.dimension = dimension;
        this.data = new int[dimension][dimension];

        rowMaps = new ArrayList<Map<Integer, Integer>>(dimension);
        columnMaps = new ArrayList<Map<Integer, Integer>>(dimension);
        for(int i = 0; i < dimension; i++) {
            rowMaps.add(new HashMap<Integer, Integer>());
            columnMaps.add(new HashMap<Integer, Integer>());
        }
        domains = generateDomains();
        tried_values = generateTriedValues();

        changed = true;
        is_correct = false;
    }

    /**
     * Returns square filled with data specified in parameter
     *
     * @param dimension
     * @param data
     */
    public LatinSquare(final int dimension, final int[][] data) {
        this(dimension);
        this.data = data;
    }

    /**
     * Returns true if instance fulfills latin square constraints
     *
     * @return
     */
    public boolean isCorrect() {

        if (changed) { // check constraints from the beginning
            // check if dimensions are logically correct
            if (dimension != data[0].length) {
                return false;
            }
            generateMaps();
            changed = false;
            is_correct = verifyMaps();
        }
        return is_correct;
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

        for(int i = 0; i < dimension; i++) {
            Map<Integer, Integer> rowMap = mapOccurances(data[i]);

            int[] col = new int[dimension];
            for (int j = 0; j < dimension; j++) {
                col[j] = data[j][i];
            }
            Map<Integer, Integer> colMap = mapOccurances(col);

            rowMaps.set(i, rowMap);
            columnMaps.set(i, colMap);
        }
    }

    /**
     * Checks for errors in columns and rows of latin square and returns false if latin square is incorrect.
     * This check accepts not fully filled latin squares and doesn't take into account 0 values - they can repeat
     *
     * @return boolean - false if some variables of latin square are in conflict
     */
    private boolean verifyMaps() {
        for (Map<Integer, Integer> rowMap: rowMaps) {
            for (int k: rowMap.keySet()) {
                if (k != 0) { // 0 means not set value in array
                    if (k > dimension) {
                        return false;
                    }
                    if (rowMap.get(k) != 1) {
                        return false;
                    }
                }
            }
        }
        for (Map<Integer, Integer> colMap: columnMaps) {
            for (int k: colMap.keySet()) {
                if (k != 0) { // 0 means not set value in array
                    if (colMap.get(k) != 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String toString() {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append((data[i][j] + " "));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public LatinSquare setVariable(int x, int y, int value) {
        if (x >= 0 && x <= dimension-1 && y >= 0 && y <= dimension-1) {
            data[x][y] = value;
            removeFromDomain(value, x, y);
            tried_values[x][y].add(value);
            changed = true;
        }
        return this;
    }

    public LatinSquare copy() {
        return new LatinSquare(dimension, data.clone());
    }

    /**
     * Returns value int latin square at (x, y)
     *
     * @param x dimension
     * @param y dimension
     * @return int value
     */
    public int getValueAt(int x, int y) {
        return data[x][y];
    }

    /**
     * Initially generates domain sets for variables
     *
     * @return LinkedList[][] domains list
     */
    private LinkedList<Integer>[][] generateDomains() {
        domains = new LinkedList[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                LinkedList<Integer> domain = new LinkedList<>();
                for (int k = 1; k <= dimension; k++) {
                    domain.add(k);
                }
                domains[i][j] = domain;
            }
        }
        return domains;
    }

    /**
     * Generates and returns domain sets for Latin Square that has already defined some of the variables.
     * Thus, domains have some values excluded.
     *
     * @return LinkedList[][] - domains
     */
    private LinkedList<Integer>[][] generateDomainsForGivenData() {
        domains = generateDomains();
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                int value = data[x][y];
                removeFromDomain(value, x, y);
                cutDomains(value, x, y);

                // remove already tried values from domain:
                LinkedList<Integer> already_used = this.tried_values[x][y];
                for (Integer i: already_used) {
                    removeFromDomain(i, x, y);
                }
            }
        }
        return domains;
    }

    /**
     * Excludes values from domain sets according to given value placed in given variable (specified by x and y coords)
     *
     * @param value int
     * @param x int
     * @param y int
     */
    private void cutDomains(int value, int x, int y) {

        for (int i = 0; i < dimension; i++) {
            removeFromDomain(value, x, i);
            removeFromDomain(value, i, y);
        }
    }

    /**
     * Removes specified value from domain for variable specified by X and Y cords
     *
     * @param value
     * @param x
     * @param y
     */
    private void removeFromDomain(int value, int x, int y) {
        LinkedList<Integer> single_domain = domains[x][y];
        int index = single_domain.indexOf(value);
        if (index > -1) {
            single_domain.remove(index);
        }
    }

    /**
     * Returns 2-dimensional array for each variable with list containing all possible values for it.
     *
     * @return LinkedList<Integer>[][]
     */
    public LinkedList<Integer>[][] getDomains() {
        if (changed) {
            domains = generateDomainsForGivenData();
        }
        return domains;
    }

    /**
     * Generates empty LinkedList<Integer>[dimension][dimension]
     *
     * @return empty initial tried_values
     */
    public LinkedList<Integer>[][] generateTriedValues() {
        LinkedList<Integer>[][] result = new LinkedList[dimension][dimension];
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                result[x][y] = new LinkedList<>();
            }
        }
        return result;
    }

    public LinkedList<Integer>[][] getTriedValues() {
        return tried_values;
    }
}
