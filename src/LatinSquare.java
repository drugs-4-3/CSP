import java.util.*;

public class LatinSquare {

    public int dimension;
    public int[][] data;
    private boolean changed;
    private boolean is_correct;

    public List<Map<Integer, Integer>> rowMaps;
    public List<Map<Integer, Integer>> columnMaps;
    LinkedList<Integer>[][] domains;


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
                cutDomains(value, x, y);
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
        // wykluczyc wartosc z domains o tym samym x
        // wykluczyc wartosc z domains o tym samym y

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
}
