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
     * --  --  --  --  --  --           --  --  --  --  --  --
     * 12  11  10  09  08  07   B A R   06  05  04  03  02  01
     * W               R         < >    R                   W
     * W               R                R                   W
     * W               R                R
     * W                                R
     * W                                R
     *
     *
     * R                               W
     * R                               W
     * R               W               W
     * R               W               W                   R
     * R               W        < >    W                   R
     * 13  14  15  16  17  18  B A R   19  20  21  22  23  24
     * --  --  --  --  --  --          --  --  --  --  --  --
     */
    public void setUpCheckers() {

        // Set up the WHITE checkers
        pips.get(1).setCheckers(2, Color.WHITE);
        pips.get(12).setCheckers(5, Color.WHITE);
        pips.get(17).setCheckers(3, Color.WHITE);
        pips.get(19).setCheckers(5, Color.WHITE);

        // Set up the RED checkers
        pips.get(6).setCheckers(5, Color.RED);
        pips.get(8).setCheckers(3, Color.RED);
        pips.get(13).setCheckers(5, Color.RED);
        pips.get(24).setCheckers(2, Color.RED);
    }

    public int getWhiteCheckerCount() {
        int checkerCount = 0;
        for (int i = 1; i < 25; i++) {
            if (pips.get(i).getColor() == Color.WHITE) {
            checkerCount += pips.get(i).getCheckerCount();
            }
        }
        return checkerCount;
    }

    public int getRedCheckerCount() {
        int checkerCount = 0;
        for (int i = 1; i < 25; i++) {
            if (pips.get(i).getColor() == Color.RED) {
            checkerCount += pips.get(i).getCheckerCount();
            }
        }
        return checkerCount;
    }

    public Pip getBar(Color color) {
        int barIndex = 0;  // zero if WHITE
        if (color == Color.RED) {
            barIndex = 25;  // otherwise 25 if RED
        }
        return pips.get(barIndex);
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
     * Get the pip at a given index.
     *
     * @param index The index of a pip
     * @return Pip at given index
     */
    public Pip getPip(int index) {
        return pips.get(index);
    }

    public void displayBoard(Game game) {

        System.out.println("\nWBO: " + game.getBearingOffStatus(Color.WHITE));
        System.out.println("RBO: " + game.getBearingOffStatus(Color.RED));

        System.out.println("\n12 11 10 09 08 07         06 05 04 03 02 01");
        System.out.println("-- -- -- -- -- --  B A R  -- -- -- -- -- --");
        for (int i = 12; i >= 1; i--) {
            System.out.print(pips.get(i) + " ");
            if (i == 7) {
                System.out.print("  " + Pip.ANSI_WHITE_BACKGROUND + Pip.ANSI_BLACK + "<");
                System.out.print(pips.get(0).getCheckerCount());
                System.out.print(">"+ Pip.ANSI_RESET + "   ");
            }
        }
        System.out.println("\n\n\n");
        for (int i = 13; i <= 24; i++) {
            System.out.print(pips.get(i) + " ");
            if (i == 18) {
                System.out.print("  " + Pip.ANSI_RED_BACKGROUND + Pip.ANSI_BLACK + "<");
                System.out.print(pips.get(25).getCheckerCount());
                System.out.print(">"+ Pip.ANSI_RESET + "   ");
            }
        }
        System.out.println();
        System.out.println("-- -- -- -- -- --  B A R  -- -- -- -- -- --");
        System.out.println("13 14 15 16 17 18         19 20 21 22 23 24");
    }
}