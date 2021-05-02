/**
 * Class for movement of checkers on the board.
 * Manages checker hits and blocks.
 *
 * @author  Jordan & Sam
 * @version 2021-04-30
 */
public class Move {

    /**
     * Move a checker a given distance on the board.
     *
     * @param checker The checker to move
     * @param color The color of the checker
     * @param distance The distance to move the checker
     * @param board The board on which the checker is moved
     * @return True if checker was moved successfully : false if unsuccessful move
     */
    public boolean moveChecker(Pip currentPip, int distance, Board board) {

        Color color = currentPip.getColor();
        // Find new pip
        Pip newPip = findNewPip(getDirection(color), currentPip, distance, board);

        // Don't move checker if new pip is null / movement is out of bounds
        if (newPip == null) {
            return false;
        }

        // Check what kind of interaction the checker will have with the new pip
        int newPipStatus = checkPip(newPip, color);

        // Don't move checker if new pip has enemy stack
        if (newPip.getColor() !=) {
            return false;
        }

        // Hit enemy checker (send to bar)
        if (newPipStatus == 0) {
            Checker enemyChecker = newPip.removeChecker();
            board.increaseBar(enemyChecker.getColor());
        }

        // Move checker to new pip
        newPip.addChecker(currentPip.removeChecker());
        return true;
    }

    /**
     * Check if new pip index is valid.
     *
     * @param newPipIndex Index of new pip
     * @return True if new pip index is outside of the board (not within 0-23)
     */
    private boolean outOfBounds(int newPipIndex) {
        return newPipIndex < 0 || newPipIndex > 23;
    }

    /**
     * Find the new pip.
     *
     * @param direction 1 for WHITE movement, -1 for RED movement
     * @param currentPip Pip on which the checker currently stands
     * @param distance Movement distance as specified by die cast
     * @param board The board
     * @return The new Pip
     */
    private Pip findNewPip(int direction, Pip currentPip, int distance, Board board) {
        int currentPipIndex = board.getPipIndex(currentPip);

        // Find new pip index, taking into account the direction of travel for the specific color of checker
        int newPipIndex = currentPipIndex + (direction * distance);
        if (outOfBounds(newPipIndex)) {
            return null;  // movement goes off board
        }
        return board.getPip(newPipIndex);
    }

    /**
     * Check if pip is empty, occupied by 1 enemy checker, or occupied by more than 1 enemy checkers.
     * If pip contains multiple ally checkers pip is a valid destination.
     *
     * @param pip The pip to check
     * @param color Color of ally checkers
     * @return -1 if pip is empty or ally checker, 0 if lone enemy checker, 1 if blocked by enemy stack
     */
    private int checkPip(Pip pip, Color color) {
        if (!pip.isOccupied() || pip.getColor() == color) { // pip.canAdd
            return -1;
        } else if (!pip.isStacked()) {
            return 0; // is naked and afraid
        } else {
            return 1; // is stacked
        }
    }

    /**
     * Get the movement direction associated with a checker color.
     * @param col The color of the checker
     * @return int 1 for WHITE direction and -1 for RED direction
     */
    public int getDirection(Color col) {
        if (col == Color.WHITE) {
            return 1;
        }
        return -1; // Direction for red
    }
}