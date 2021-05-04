/**
 * Class for movement of checkers on the board.
 * Manages checker hits and blocks.
 *
 * @author Jordan & Sam
 * @version 2021-04-30
 */
public class Move {

    /**
     * Move a checker from one pip to another using a given distance.
     *
     * @param currentPip The pip from which to move
     * @param distance   The distance to move the checker
     * @param board      The board on which the checker is moved
     * @return True if checker was moved successfully : false if unsuccessful move
     */
    public boolean moveChecker(Color playerColor, Pip currentPip, int distance, boolean bearingOff, Game game, Board board) {

        Color color = currentPip.getColor();
        Pip newPip = findNewPip(getDirection(color), currentPip, distance, board);

        // Don't move checker if new pip is null / movement is out of bounds
        if (newPip == null) {

            if (!bearingOff) {
                return false;
            }

            int currentPipIndex = board.getPipIndex(currentPip);

            if (currentPipIndex == distance - 1 || currentPipIndex == 24 - distance) {
                currentPip.removeChecker();
                game.decreaseCheckersLeft(color);
                return true;
            }

            else if (currentPipIndex < distance - 1) {
                for (int i = currentPipIndex + 1; i < 6; i++) {
                    if (board.getPip(i).getCheckerCount() != 0) {
                        return false;
                    }
                }
            }

            else if (currentPipIndex > 24 - distance) {
                for (int i = currentPipIndex - 1; i > 17; i--) {
                    if (board.getPip(i).getCheckerCount() != 0) {
                        return false;
                    }
                }
            }
            currentPip.removeChecker();
            game.decreaseCheckersLeft(color);
            return true;
        }

        // Do not let WHITE move RED's checkers and vice versa
        if (color != playerColor) {
            return false;
        }
        // Do not let player move from pip with no checkers
        else if (currentPip.getCheckerCount() <= 0) {
            return false;
        }
        // Otherwise, check if checker can be added to the new pip
        else if (newPip.canAdd(color)) {
            if (newPip.getCheckerCount() > 0 && newPip.getColor() != color) {
                // Hit enemy checker and send to bar
                board.increaseBar(newPip.getColor());
                newPip.removeChecker();
            }
            // Move checker to new pip
            currentPip.removeChecker();
            newPip.addChecker(color);
            return true;
        }
        return false;
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
     * @param direction  1 for WHITE movement, -1 for RED movement
     * @param currentPip Pip on which the checker currently stands
     * @param distance   Movement distance as specified by die cast
     * @param board      The board
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
     * Get the movement direction associated with a checker color.
     *
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