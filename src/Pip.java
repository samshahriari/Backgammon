import java.util.Stack;

/**
 * Class for board pips holding information about the color and number of checkers on a pip.
 * Allows for adding/removing checkers from pips.
 *
 * @author Jordan & Sam
 * @version 2021-05-02
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
     * Set the color and number of checkers for a pip.
     * Overwrites the current configuration.
     *
     * @param cc  The number of checkers
     * @param col The color of the checkers
     */
    public void setCheckers(int cc, Color col) {
        checkerCount = cc;
        color = col;
    }

    /**
     * Add a checker to the pip.
     *
     * @param col The color of checker to be added
     */
    public void addChecker(Color col) {
        checkerCount++;
        color = col;
    }

    /**
     * TODO: ADD JAVADOC
     *
     * @param col
     * @return
     */
    public boolean canAdd(Color col) {
        return (checkerCount <= 1 || color == col || color == null);
    }

    /**
     * Remove a checker form the pip.
     */
    public void removeChecker() {
        checkerCount--;
        if (checkerCount <= 0) {
            checkerCount = 0;
            color = null;
        }
    }

    /**
     * TODO: ADD JAVADOC
     *
     * @return
     */
    public int getCheckerCount() {
        return checkerCount;
    }

    /**
     * Check if the stack is RED or WHITE.
     *
     * @return Color of the checker stack
     */
    public Color getColor() {
        return color;
    }

    /**
     * Print the checker stack.
     *
     * @return String representation of the checker stack
     */
    @Override
    public String toString() {
        return checkerCount + color.toString();
    }
}