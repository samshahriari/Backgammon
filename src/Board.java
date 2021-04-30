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
     * Construct new board.
     */
    public Board() {

        // Create list of pips
        pips = new ArrayList<>(24);
        for (int i = 0; i < 24; i++) {
            pips.set(i, new Pip());
        }

        // Create bar and assign initial values 0 for RED and WHITE
        bar = new HashMap<>();
        bar.put(Color.WHITE, 0);
        bar.put(Color.RED, 0);
    }

    /**
     * Set up the board in its start configuration.
     */
    public void setUpBoard() {

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
}