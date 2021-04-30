public class Move {

    public void moveChecker(Checker checker, int distance, Board board) {

        Pip currentPip = checker.getPosition();
        int currentPipIndex = board.getPipIndex(currentPip);
        int newPipIndex = currentPipIndex + (checker.getDirection() * distance);

        Pip newPip = board.getPip(newPipIndex);
        newPip.addChecker(currentPip.removeChecker());
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
