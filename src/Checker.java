/**
 * Class for checkers used on a backgammon board.
 * Contains information about checker color, position, and direction.
 *
 * @author  Jordan & Sam
 * @version 2021-04-30
 */
public class Checker {

    private final Color COLOR;  // color of checker

    private Pip position;  // position of checker

    /**
     * Create new checker.
     *
     * @param c The color of the new checker
     * @param pos The position of the new checker
     */
    public Checker(Color c, Pip pos) {
        COLOR = c;
        position = pos;
    }

    /**
     * @return String representation of the checker (returns the color of the checker)
     */
    @Override
    public String toString() {
        return COLOR.toString();
    }

    /**
     * Get the color of a checker.
     *
     * @return Color of checker: WHITE/RED
     */
    public Color getColor() {
        return COLOR;
    }

    /**
     * Get position of a checker.
     *
     * @return Pip position of checker
     */
    public Pip getPosition() {
        return position;
    }

    /**
     * Get the movement direction associated with a checker color.
     *
     * @return int 1 for WHITE direction and -1 for RED direction
     */
    public int getDirection() {
        if (COLOR == Color.WHITE) {
            return 1;
        }
        return -1; // Direction for red
    }
}