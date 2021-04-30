public class Move {

    public void moveChecker(Checker checker, int distance, Board board) {

        Pip currentPip = checker.getPosition();
        int currentPipIndex = board.getPipIndex(currentPip);
        int newPipIndex = currentPipIndex + (checker.getDirection() * distance);

        Pip newPip = board.getPip(newPipIndex);
        newPip.addChecker(currentPip.removeChecker());
    }

}
