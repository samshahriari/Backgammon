import java.util.Stack;

/**
 * Class for board pips holding information about the color and number of checkers on a pip.
 * Allows for adding/removing checkers from pips.
 *
 * @author  Jordan & Sam
 * @version 2021-04-30
 */
public class Pip {

    private int checkerCount; // holds the checkers on the pip
    private Color color;

    /**
     * Create new pip.
     */
    public Pip() {
        checkerCount = 0;
        color = null;
    }

    /**
     * Add a checker to the pip.
     *
     * @param checker The checker to be added
     */
    public void addChecker(Checker checker) {
        checkerStack.push(checker);
    }

    /**
     * Remove a checker form the pip.
     *
     * @return The removed checker
     */
    public Checker removeChecker() {
        return checkerStack.pop();
    }

    /**
     * Check if pip is occupied.
     *
     * @return true if pip does not contain any checkers : else false
     */
    public boolean isOccupied() {
        return !checkerStack.isEmpty();
    }

    /**
     * Check if pip contains more than 1 checker.
     *
     * @return true if pip contains more than 1 checker : else false
     */
    public boolean isStacked() {
        return checkerStack.size() > 1;
    }

    /**
     * Check if the stack is RED or WHITE.
     *
     * @return Color of the checker stack
     */
    public Color getColor() {
        if (checkerStack.isEmpty()) {
            return null;
        }
        return checkerStack.peek().getColor();
    }

    /**
     * Print the checker stack.
     *
     * @return String representation of the checker stack
     */
    @Override
    public String toString() {
        return checkerStack.toString();
    }
}