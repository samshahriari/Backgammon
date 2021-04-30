public class Board {

    public enum Player {
        WHITE, RED
    }

    private Pip[] pips = new Pip[24]; // 0-23
    private int redBar;
    private int whiteBar;

    public Board() {
        for (int i = 0; i < 24; i++) {
            pips[i] = new Pip();
        }
    }
}

    // pips
    // quadrants
    // checkers
    // players
