import java.util.ArrayList;

/**
 * Class for creating and initializing a backgammon board.
 * Contains information about pips and prints the board.
 *
 * @author  Jordan & Sam
 * @version 2021-05-20
 */
public class Board {

    private final ArrayList<Pip> pips; // pips 0-25, bars at 0 and 25

    /**
     * Create new board.
     */
    public Board() {

        // Create list of pips
        pips = new ArrayList<>(26);
        for (int i = 0; i < 26; i++) {
            pips.add(i, new Pip());
        }
        setUpCheckers();
    }

    /**
     * Set up the checkers in their start configuration.
     *
     * Initial checker set up
     *
     *      --  --  --  --  --  --           --  --  --  --  --  --
     *      12  11  10  09  08  07   B A R   06  05  04  03  02  01
     *      W               R         < >    R                   W
     *      W               R                R                   W
     *      W               R                R
     *      W                                R
     *      W                                R
     *
     *
     *      R                                W
     *      R                                W
     *      R               W                W
     *      R               W                W                   R
     *      R               W         < >    W                   R
     *      13  14  15  16  17  18   B A R   19  20  21  22  23  24
     *      --  --  --  --  --  --           --  --  --  --  --  --
     */
    public void setUpCheckers() {
        // Set up the WHITE checkers
        pips.get(1).setCheckers(2, Player.WHITE);
        pips.get(12).setCheckers(5, Player.WHITE);
        pips.get(17).setCheckers(3, Player.WHITE);
        pips.get(19).setCheckers(5, Player.WHITE);

        // Set up the RED checkers
        pips.get(6).setCheckers(5, Player.RED);
        pips.get(8).setCheckers(3, Player.RED);
        pips.get(13).setCheckers(5, Player.RED);
        pips.get(24).setCheckers(2, Player.RED);
    }

    /**
     * Getter for pips list.
     *
     * @return pips ArrayList
     */
    public ArrayList<Pip> getPips() {
        return pips;
    }

    /**
     * Sums and returns the count of white checkers at the board (excluding the bar).
     *
     * @return The total number of white checkers at the board
     */
    public int getWhiteCheckerCount() {
        int checkerCount = 0;
        // Iterate across all pip indices and count WHITE checkers
        for (int i = 0; i < 26; i++) {
            if (pips.get(i).getColor() == Player.WHITE) {
            checkerCount += pips.get(i).getCheckerCount();
            }
        }
        return checkerCount;
    }

    /**
     * Sums and returns the count of red checkers at the board (excluding the bar).
     *
     * @return The total number of red checkers at the board
     */
    public int getRedCheckerCount() {
        int checkerCount = 0;
        // Iterate across all pip indices and count RED checkers
        for (int i = 0; i < 26; i++) {
            if (pips.get(i).getColor() == Player.RED) {
            checkerCount += pips.get(i).getCheckerCount();
            }
        }
        return checkerCount;
    }

    /**
     * Get the pip that symbolizes the bar for the given color.
     *
     * @param col The color of the bar
     * @return Bar Pip corresponding to the color.
     */
    public Pip getBar(Player col) {
        int barIndex = 0;  // zero if WHITE
        if (col == Player.RED) {
            barIndex = 25;  // 25 if RED
        }
        return pips.get(barIndex);
    }

    /**
     * Get the index of a pip.
     *
     * @param pip The pip whose index is sought
     * @return int The index of a pip
     */
    public int getPipIndex(Pip pip) {
        return pips.indexOf(pip);
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
}