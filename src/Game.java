import java.util.ArrayList;
import java.util.Random;

/**
 * Game class handles all the game logic.
 *
 * @author  Jordan & Sam
 * @version 2021-05-02
 */
public class Game {

    private boolean gameOver;
    private Board board;
    private Color playerTurn;
    Random rand;

    public Game() {
        gameOver = false;
        board = new Board();
        playerTurn = Color.WHITE;
    }

    public void nextTurn() {
        if(playerTurn == Color.WHITE) {
            playerTurn = Color.RED;
        } else {
            playerTurn = Color.WHITE;
        }
    }

    public ArrayList<Integer> rollDice() {
        rand = new Random();

        ArrayList<Integer> diceValues = new ArrayList<>();

        int dieCast1 = rand.nextInt(6) + 1;
        int dieCast2 = rand.nextInt(6) + 1;

        diceValues.add(dieCast1);
        diceValues.add(dieCast2);

        if (dieCast1 == dieCast2) {
            diceValues.add(dieCast1);
            diceValues.add(dieCast2);
        }

        return diceValues;
    }

    public static void main(String[] args) {
        Game game = new Game();

    }

    // TODO: Implement DICE
    // TODO: Bear-in/off
    // TODO: Player turns
    // TODO: Bar - hitting and entering
    // TODO: win/lose state


}