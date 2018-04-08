import java.security.InvalidParameterException;
import java.util.LinkedList;

public class LatinSquareFinder {

    public static final int RANDOM_SEARCH = 0;
    public static final int METHOD_BACKTRACKING = 1;
    public static final int METHOD_FORWARDCHECKING= 2;

    private int dimension;

    public LatinSquareFinder(int dimension) {
        this.dimension = dimension;
    }

    /**
     * Searches for latin square using algorithm specified by method parameter
     * @param method - algorithm code
     * @return LatinSquare
     */
    public LatinSquare findLatinSquare(final int method) {
        switch (method) {
            case RANDOM_SEARCH:
                return findUsingRandomSearch();
            case METHOD_BACKTRACKING:
                return findUsingBacktracking();
            case METHOD_FORWARDCHECKING:
                return findUsingForwardChecking();
            default:
                throw new InvalidParameterException("Method of code " + method + " is not available");
        }
    }

    /**
     * Finds latin square using random search algorithm
     *
     * @return LatinSquare
     */
    private LatinSquare findUsingRandomSearch() {
        // todo: not implemented
        return new LatinSquare(dimension);
    }

    /**
     * Recursively searches for LatinSquare using backtracking algorithm
     *
     * @param ls LatinSquare
     * @param index index of currently focused variable
     * @param value value to set on current variable
     * @return correct LatinSquare
     */
    private LatinSquare findByBackTracking(LatinSquare ls, int index, int value) {
        // if finished - return
        if (index >= dimension * dimension - 1 && ls.isCorrect() && getValueAtIndex(ls, (dimension * dimension - 1)) != 0) {
            return ls;
        }

        // try current value
        ls = setVariable(ls, index, value);

        if (ls.isCorrect()) {
            // set next variable
            return findByBackTracking(ls, ++index, 1);
        }
        else {
            // check another value for current variable
            if (value < dimension) {
                return findByBackTracking(ls, index, ++value);
            }
            // if all values checked and still incorrect - go back and try next value for previous variable
            else {
                ls = setVariable(ls, index, 0); // fall back with previously set value
                index--;
                value = getValueAtIndex(ls, index) + 1;
                return findByBackTracking(ls, index, value);
            }
        }
    }

    private LatinSquare findUsingBacktracking() {
        return findByBackTracking(new LatinSquare(dimension), 0, 1);
    }

    /**
     * Returns latin square found using forward checking
     *
     * @return LatinSquare
     */
    private LatinSquare findUsingForwardChecking() {
        return findByForwardChecking(new LatinSquare(dimension), 0);
    }

    /**
     * Recursively searches for latin square solution using forward checking algorithm
     *
     * @param ls - LatinSquare object on which we're working
     * @param index - int which variable is currently considered
     * @return LatinSquare - correct solution
     */
    private LatinSquare findByForwardChecking(LatinSquare ls, int index) {
//        System.out.println("index: " + index);
        if ((index >= dimension*dimension - 1) && (ls.getValueAt(dimension - 1, dimension - 1) != 0) && ls.isCorrect()) {
            return ls;
        }
        int x = index%dimension;
        int y = index/dimension;
        // if there is value in domain left
        LinkedList domain = ls.getDomains()[x][y];
        if (domain.size() > 0) {
            ls.setVariable(x, y, ls.getDomains()[x][y].pop());
            return findByForwardChecking(ls, ++index);
        }
        else {
            // step back
            // wyczyscic liste wyprobowanych zmiennych dla tego indexu!!!
            ls.setVariable(x, y, 0);
            ls.getTriedValues()[x][y].clear();
            return findByForwardChecking(ls, --index);
        }
    }

    /**
     * Sets variable in latinsquare specified by index value.
     * Latinsquare variables are indexed from left to right, top-down.
     * Index value represents one variable, range from 0 to (dimension^2 - 1).
     * Index is converted to values x and y in latinsquare.
     *
     * @param ls LatinSquare to operate on
     * @param index number in range (0, dimension*dimension - 1), specifies one variable
     * @param value what to set in variable
     *
     * @return LatinSquare after changes
     */
    private LatinSquare setVariable(LatinSquare ls, final int index, final int value) {

        int x = index%dimension;
        int y = index/dimension;
        return ls.setVariable(x, y, value);
    }

    /**
     * Converts incremental index to x and y coords and returns latin square value at (x, y)
     *
     * @param ls LatinSquare
     * @param index range (0, dim^2 - 1)
     * @return int value of specified variable
     */
    private int getValueAtIndex(LatinSquare ls, int index) {
        int x = index%dimension;
        int y = index/dimension;
        return ls.getValueAt(x, y);
    }
}
