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

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
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