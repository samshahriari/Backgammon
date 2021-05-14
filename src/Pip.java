/**
 * Class for board pips holding information about the color and number of checkers on a pip.
 * Allows for adding/removing checkers from pips.
 *
 * @author Jordan & Sam
 * @version 2021-05-14
 */
public class Pip {

    private int checkerCount; // holds the checkers on the pip
    private Player checkerColor;

    // Color codes for printing to the terminal
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    /**
     * Create new pip.
     */
    public Pip() {
        checkerCount = 0;
        checkerColor = null;
    }

    /**
     * Set the color and number of checkers for a pip.
     * Overwrites the current configuration.
     *
     * @param cc  The number of checkers
     * @param col The color of the checkers
     */
    public void setCheckers(int cc, Player col) {
        checkerCount = cc;
        checkerColor = col;
    }

    /**
     * Add a checker to the pip.
     *
     * @param col The color of checker to be added
     */
    public void addChecker(Player col) {
        checkerCount++;
        checkerColor = col;
    }

    /**
     * Check if a checker can be added to the pip.
     *
     * A checker can be added if one of these criterion is met:
     *      - the pip is empty
     *      - the pip contains one checker
     *      - the pip contains checkers of the same color as the one to be added
     *
     * @param col The color of the checker to be moved
     * @return True if the checker can be added to the pip : False otherwise
     */
    public boolean canAdd(Player col) {
        return (checkerCount < 2 || checkerColor == col);
    }

    /**
     * Remove a checker form the pip.
     */
    public void removeChecker() {
        checkerCount--;
        if (checkerCount <= 0) {
            checkerCount = 0;
            checkerColor = null;
        }
    }

    /**
     * Get the checker count of the pip.
     *
     * @return the number of checkers in the pip
     */
    public int getCheckerCount() {
        return checkerCount;
    }

    /**
     * Check if the stack is RED or WHITE.
     *
     * @return Color of the checker stack
     */
    public Player getColor() {
        return checkerColor;
    }

    /**
     * Check if the pip has no checkers.
     *
     * @return True if there are no checkers on the pip : false if contains checkers
     */
    public boolean containsCheckers() {
        return checkerCount > 0;
    }

    /**
     * Print the number and color of checkers in the pip.
     *
     * @return String representation of the pip
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(checkerCount);
        if (String.valueOf(checkerColor).equals("WHITE")) {
            sb.insert(0, ANSI_WHITE_BACKGROUND); // change the background color to white
            sb.insert(0, ANSI_BLACK);
            sb.append("W");
            sb.append(ANSI_RESET);
        } else if (String.valueOf(checkerColor).equals("RED")) {
            sb.insert(0, ANSI_RED_BACKGROUND); // change the background color to red
            sb.insert(0, ANSI_BLACK);
            sb.append("R");
            sb.append(ANSI_RESET);
        } else {
            return "  "; // return a string with whitespace if the pip is empty
        }
        return sb.toString();
    }
}