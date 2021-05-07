import java.util.ArrayList;

/**
 * Class that manages checker movement between pips on a backgammon board.
 * Manages checker hits and blocks.
 *
 * @author  Jordan & Sam
 * @version 2021-05-07
 */
public class Move {

    /**
     * Move a checker from one pip to another according to the given steps.
     *
     * @param playerColor The color of the player making the move
     * @param currentPip  The pip from which to move
     * @param steps       The number of steps to move the checker
     * @param bearingOff  Whether or not the player is currently bearing off or not
     * @param game        The active game
     * @param board       The board on which the checker is moved
     * @return True if checker was moved successfully : false if unsuccessful move
     */
    public boolean moveChecker(Color playerColor, Pip currentPip, int steps, boolean bearingOff, Game game, Board board) {
        // Get the movement type (-1, 0, 1, 2) to determine how the checker will interact with other checkers or the board
        int moveType = checkMoveType(playerColor, currentPip, steps, bearingOff, board);

        // Find the new pip
        Pip newPip = findNewPip(getDirection(playerColor), currentPip, steps, board);

        switch (moveType) {
            case 0:
                // Move checker to new pip
                currentPip.removeChecker();
                newPip.addChecker(playerColor);
                return true;
            case 1:
                Color newColor = newPip.getColor();
                // Send enemy checker to bar
                Pip bar = board.getBar(newColor);
                bar.addChecker(newColor);
                // Remove hit checker
                newPip.removeChecker();
                // Move checker to new pip
                currentPip.removeChecker();
                newPip.addChecker(playerColor);
                return true;
            case 2:
                // Bear off the checker and decrease the counter of overall checkers for that player
                currentPip.removeChecker();
                game.decreaseCheckersLeft(playerColor);
                return true;
        }
        return false;  // for moveType == -1
    }

    /**
     * Check move types for all dice values (calls checkMoveType).
     *
     * @param playerColor The current player's color
     * @param currentPip  The pip that a checker is being moved from
     * @param diceValues  All dice values for a player
     * @param bearingOff  Whether a player is bearing off or not
     * @param board       The backgammon board being played on
     * @return True if any of the dice values are playable : false if no moves are possible
     */
    public boolean canMove(Color playerColor, Pip currentPip, ArrayList<Integer> diceValues, boolean bearingOff, Board board) {
        for (Integer die : diceValues) {
            if (checkMoveType(playerColor, currentPip, die, bearingOff, board) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Identify the type of move being played (i.e. invalid move, regular move, hit, or bearing off).
     *
     * @param playerColor The current player's color
     * @param currentPip  The pip that a checker is being moved from
     * @param steps       The number of steps to move the checker
     * @param bearingOff  Whether a player is bearing off or not
     * @param board       The backgammon board being played on
     * @return -1 if no movement possible, 0 for regular move, 1 for hit, and 2 for bearing off
     */
    private int checkMoveType(Color playerColor, Pip currentPip, int steps, boolean bearingOff, Board board) {

        // Find the new pip
        Pip newPip = findNewPip(getDirection(playerColor), currentPip, steps, board);

        if (newPip == null) {
            // If new pip is null/out of bounds and the player is not bearing off, disallow movement
            if (!bearingOff) {
                return -1;  // no move possible
            }
            // Find the current pip index
            int currentPipIndex = board.getPipIndex(currentPip);

            // Check if current pip matches dice throw/steps exactly
            // I.e. standing on the second pip from the edge of the board and rolling a 2
            if (currentPipIndex == steps || currentPipIndex == 25 - steps) {
                return 2;  // move bears checker off
            }
            // Check if there are any checkers behind the selected pip that might stop movement (for RED bear-off)
            else if (currentPipIndex < steps) {
                // Iterate over RED's 6 home quadrant pips/indices
                for (int i = currentPipIndex + 1; i <= 6; i++) {
                    // If there is an owned checker behind the selected pip, return false to stop movement
                    if (board.getPip(i).getCheckerCount() != 0 && board.getPip(i).getColor() == playerColor) {
                        return -1;  // no move possible
                    }
                }
            }
            // Check if there any checkers behind the selected pip that might stop movement (for RED bear-off)
            else if (currentPipIndex > 24 - steps) {
                // Iterate over WHITE's 6 home quadrant pips/indices
                for (int i = currentPipIndex - 1; i >= 19; i--) {
                    // If there is an owned checker behind the selected pip, return false to stop movement
                    if (board.getPip(i).getCheckerCount() != 0 && board.getPip(i).getColor() == playerColor) {
                        return -1;  // no move possible
                    }
                }
            }
            return 2;  // move bears checker off
        }

        // Check if a checker can be added to the new pip (compare colors)
        if (newPip.canAdd(playerColor)) {

            Color newColor = newPip.getColor();

            if (newPip.getCheckerCount() > 0 && newColor != playerColor) {
                return 1;  // move hits enemy
            }
            return 0;  // regular move *no hit*
        }
        return -1;  // no move possible
    }

    /**
     * Check if new pip index is valid.
     *
     * @param newPipIndex Index of new pip
     * @return True if new pip index is outside of the board (not within 0-23)
     */
    private boolean outOfBounds(int newPipIndex) {
        return newPipIndex < 1 || newPipIndex > 24;  // check if pip is outside of the interval 1-24
    }

    /**
     * Find the new pip.
     *
     * @param direction  1 for WHITE movement, -1 for RED movement
     * @param currentPip Pip on which the checker currently stands
     * @param steps      Movement steps as specified by die cast
     * @param board      The board
     * @return The new Pip
     */
    private Pip findNewPip(int direction, Pip currentPip, int steps, Board board) {
        // Get index of current pip
        int currentPipIndex = board.getPipIndex(currentPip);
        // Find index of new pip, taking into account the direction of travel for the specific color of checker
        int newPipIndex = currentPipIndex + (direction * steps);
        // Check if new pip is out of bounds
        if (outOfBounds(newPipIndex)) {
            return null;  // because movement goes off board
        }
        // Return the Pip object corresponding to the new pip index
        return board.getPip(newPipIndex);
    }

    /**
     * Get the movement direction associated with a checker color.
     *
     * @param col The color of the checker
     * @return int 1 for WHITE direction and -1 for RED direction
     */
    public int getDirection(Color col) {
        if (col == Color.WHITE) {
            return 1;  // WHITE has direction factor 1
        }
        return -1; // RED has direction factor -1
    }
}