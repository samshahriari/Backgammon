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

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

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
        return (checkerCount < 2 || color == col);
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
        StringBuilder sb = new StringBuilder();
        sb.append(checkerCount);
        if (String.valueOf(color).equals("WHITE")) {
            sb.insert(0, ANSI_WHITE_BACKGROUND);
            sb.insert(0, ANSI_BLACK);
            sb.append("W");
            sb.append(ANSI_RESET);
        } else if (String.valueOf(color).equals("RED")) {
            sb.insert(0, ANSI_RED_BACKGROUND);
            sb.insert(0, ANSI_BLACK);
            sb.append("R");
            sb.append(ANSI_RESET);
        } else {
            return "  ";
        }
        return sb.toString();
    }
}