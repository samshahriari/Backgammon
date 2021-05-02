import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for creating and initializing a backgammon board.
 * Contains information about pips, bars, [...]
 *
 * @author  Jordan & Sam
 * @version 2021-04-30
 */
public class Board {

    private ArrayList<Pip> pips; // pips 0-23

    private Map<Color, Integer> bar;

    /**
     * Create new board.
     */
    public Board() {

        // Create list of pips
        pips = new ArrayList<>(24);
        for (int i = 0; i < 24; i++) {
            pips.add(i, new Pip());
        }

        // Create bar and assign initial values 0 for RED and WHITE
        bar = new HashMap<>();
        bar.put(Color.WHITE, 0);
        bar.put(Color.RED, 0);

        setUpCheckers();
    }

    /**
     * Set up the checkers in their start configuration.
     *
     * Initial checker set up
     *
     *      11  10  9   8   7   6   |   5   4   3   2   1   0
     *      W               R           R                   W
     *      W               R           R                   W
     *      W               R           R
     *      W                           R
     *      W                           R
     *
     *
     *      R                           W
     *      R                           W
     *      R               W           W
     *      R               W           W                   R
     *      R               W           W                   R
     *      12  13  14  15  16  17  |   18  19  20  21  22  23
     */
    public void setUpCheckers() {

        // Set up the WHITE checkers
        pips.get(0).setCheckers(2, Color.WHITE);
        pips.get(11).setCheckers(5, Color.WHITE);
        pips.get(16).setCheckers(3, Color.WHITE);
        pips.get(18).setCheckers(5, Color.WHITE);

        // Set up the RED checkers
        pips.get(5).setCheckers(5, Color.RED);
        pips.get(7).setCheckers(3, Color.RED);
        pips.get(12).setCheckers(5, Color.RED);
        pips.get(23).setCheckers(2, Color.RED);
    }

    /**
     * Get the index of a pip.
     *
     * @param pip the pip whose index is sought
     * @return int The index of a pip
     */
    public int getPipIndex(Pip pip) {
        return pips.indexOf(pip);
    }

    /**
     * Get the list of all the pips.
     *
     * @return ArrayList containing pips
     */
    public ArrayList<Pip> getPips() {
        return pips;
    }

    /**
     * Get the pip at a given index.
     *
     * @param index The index of a pip
     * @return Pip at given index
     */
    public Pip getPip(int index) {
        return pips.get(index);
    }

    /**
     * Indicate a checker being hit and ending up on the bar by increasing the count of the bar corresponding
     * to the color of the checker that was hit.
     *
     * @param color The color of the hit checker
     */
    public void increaseBar(Color color) {
        bar.put(color, bar.get(color) + 1);
    }

    public String displayBoard() {

        String board = pips.toString();

        return board;
    }
}