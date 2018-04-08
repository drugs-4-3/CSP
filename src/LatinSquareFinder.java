import sun.awt.image.ImageWatched;

import java.security.InvalidParameterException;
import java.util.LinkedList;

public class LatinSquareFinder {

    public static final int RANDOM_SEARCH = 0;
    public static final int METHOD_BACKTRACKING = 1;
    public static final int METHOD_FORWARDCHECKING= 2;

    private int dimension;
    private int iteration = 0;


    public LatinSquareFinder(int dimension) {
        this.dimension = dimension;
    }

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
     * Finds Latin Square using backtracking algorithm
     *
     * @return
     */
    private LatinSquare findUsingRandomSearch() {
        // todo: not implemented
        return new LatinSquare(dimension);
    }

    private LatinSquare backTrackingIteration(LatinSquare ls, int index, int value) {

        long t1 = System.currentTimeMillis();
        while (true) {

            // if finished - return
            if (index >= dimension * dimension - 1 && ls.isCorrect()) {
                if (getValueAtIndex(ls, (dimension * dimension - 1)) != 0) {
                    break;
                }
            }

            // try this value
            ls = setVariable(ls, index, value);

            if (ls.isCorrect()) {
                // set next variable
                index++;
                value = 1;
            }
            else {
                // check another value for current variable
                if (value < dimension) {
                    value++;
                }
                // if all values checked and still incorrect - go back and try next value for previous variable
                else {
                    ls = setVariable(ls, index, 0); // fall back with previously set value
                    index--;
                    value = getValueAtIndex(ls, index) + 1;
                }
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Execution time: " + (t2 - t1) + " ms. ");
        return ls;
    }

    private LatinSquare findUsingBacktracking() {
        LatinSquare ls = new LatinSquare(dimension);
        return backTrackingIteration(ls, 0, 1);
    }

    private LatinSquare findUsingForwardChecking() {
        long t1 = System.currentTimeMillis();
        LatinSquare result = findByForwardChecking(new LatinSquare(dimension), 0);
        long t2 = System.currentTimeMillis();
        System.out.println("Execution took: " + (t2 - t1) + " ms. !");
        return result;

//        LatinSquare ls = new LatinSquare(dimension);
//        int index = 0;
//        int last_index = dimension*dimension - 1;
//
//        while (index <= last_index) {
//            int x = index%dimension;
//            int y = index/dimension;
//            int value = ls.getDomains()[x][y].getFirst();
//            int old_value = getValueAtIndex(ls, index);
//
//            setVariable(ls, index, value);
//            if (ls.isCorrect()) {
//                index++;
//            }
//            else {
//                if (ls.getDomains()[x][y].size() > 0) {
//                    value = ls.getDomains()[x][y].getFirst();
//                }
//                else {
//                    index --;
//                }
//            }
//        }
//
//        return ls;
    }

    private LatinSquare findByForwardChecking(LatinSquare ls, int index) {
        // conditions for found solution
        if ((index >= dimension*dimension - 1) && (ls.getValueAt(dimension - 1, dimension - 1) != 0) && ls.isCorrect()) {
            return ls;
        }
        int x = index%dimension;
        int y = index/dimension;
        // if there is value in domain left
        if (ls.getDomains()[x][y].size() > 0) {
            ls.setVariable(x, y, ls.getDomains()[x][y].pop());
            return findByForwardChecking(ls, ++index);
        }
        else {
            // cofnac sie
            ls.setVariable(x, y, 0);
            return findByForwardChecking(ls, --index);
        }
    }

    public LinkedList<LatinSquare> findAllUsingBacktracking()
    {
        LinkedList<LatinSquare> result = new LinkedList<>();
        LatinSquare ls = new LatinSquare(dimension);
        int index = 0;
        int value = 1;

        long t1 = System.currentTimeMillis();
        while (true) {

            // what's the condition for stopping further searching?
            if (false) {
                break;
            }

            // if found - return
            if (index >= dimension * dimension - 1 && ls.isCorrect()) {
                if (getValueAtIndex(ls, (dimension * dimension - 1)) != 0) {
                    result.add(ls.copy());
                    value++;
                    System.out.println(result.size() + ":");
                    System.out.println(ls.toString());
                }
            }

            // try this value
            ls = setVariable(ls, index, value);

            if (ls.isCorrect()) {
                // set next variable
                index++;
                value = 1;
            }
            else {
                // check another value for current variable
                if (value < dimension) {
                    value++;
                }
                // if all values checked and still incorrect - go back and try next value for previous variable
                else {
                    ls = setVariable(ls, index, 0); // fall back with previously set value
                    index--;
                    value = getValueAtIndex(ls, index) + 1;
                }
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Execution time: " + (t2 - t1) + " ms. ");
        return result;
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

    private int getValueAtIndex(LatinSquare ls, int index) {
        int x = index%dimension;
        int y = index/dimension;
        return ls.getValueAt(x, y);
    }
}
