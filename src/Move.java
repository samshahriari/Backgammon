public class Move {

    public boolean moveChecker(Checker checker, Color color, int distance, Board board) {

        Pip currentPip = checker.getPosition();
        int currentPipIndex = board.getPipIndex(currentPip);
        int newPipIndex = currentPipIndex + (checker.getDirection() * distance);
        if (outOfBounds(newPipIndex)) {
            return false;
        }

        Pip newPip = board.getPip(newPipIndex);

        int newPipStatus = checkPip(newPip, color);

        if (newPipStatus == 1) {
            return false;
        }

        if (newPipStatus == 0) {
            Checker hitChecker = newPip.removeChecker();
                board.increaseBar(hitChecker.getColor());
        }
        // Move checker
        newPip.addChecker(currentPip.removeChecker());
        return true;
    }

    private boolean outOfBounds(int newPipIndex) {
        return newPipIndex < 0 || newPipIndex > 23;
    }

    /**
     * @param pip
     * @param color
     * @return -1 if pip is empty or ally checker, 0 if lone enemy checker, 1 if blocked by enemy stack
     */
    private int checkPip(Pip pip, Color color) {
        if (!pip.isOccupied() || pip.getColor() == color) {
            return -1;
        } else if (!pip.isStacked()) {
            return 0; // is naked and afraid
        } else {
            return 1; // is stacked
        }
    }
}