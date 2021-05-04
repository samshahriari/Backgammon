import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Game class handles all the game logic.
 *
 * @author Jordan & Sam
 * @version 2021-05-02
 */
public class Game {

    private boolean gameOver;
    private Board board;
    private Move move;
    Random rand;
    private Color playerTurn;
    private int whiteCheckersLeft;
    private int redCheckersLeft;
    private boolean whiteBearingOff;
    private boolean redBearingOff;

    public Game() {
        gameOver = false;
        board = new Board();
        move = new Move();
        playerTurn = Color.WHITE;
        whiteCheckersLeft = 15;
        redCheckersLeft = 15;
        whiteBearingOff = false;
        redBearingOff = false;
    }

    public int getWhiteCheckersLeft() {
        return whiteCheckersLeft;
    }

    public int getRedCheckersLeft() {
        return redCheckersLeft;
    }

    public void nextTurn() {
        if (playerTurn == Color.WHITE) {
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

    public void updateBearingOffStatus(Color col) {
        int lowerBounds = -1;
        int upperBounds = -1;
        int checkersLeft = 15;
        switch (col) {
            case WHITE:
                lowerBounds = 18;
                upperBounds = 24;
                checkersLeft = whiteCheckersLeft;
                break;
            case RED:
                lowerBounds = 0;
                upperBounds = 6;
                checkersLeft = redCheckersLeft;
                break;
        }
        for (int i = lowerBounds; i < upperBounds; i++) {
            checkersLeft -= board.getPip(i).getCheckerCount();
        }
        boolean bearingOffStatus = (checkersLeft == 0);

        if (col == Color.WHITE) {
            whiteBearingOff = bearingOffStatus;
        } else if (col == Color.RED) {
            redBearingOff = bearingOffStatus;
        }
    }

    public boolean decreaseCheckersLeft(Color col) {
        if (col == Color.WHITE) {
            whiteCheckersLeft--;
            return true;
        } else if (col == Color.RED) {
            redCheckersLeft--;
            return true;
        }
        return false;
    }

    public boolean getBearingOffStatus(Color col) {
        if (col == Color.WHITE) {
            return whiteBearingOff;
        } else if (col == Color.RED) {
            return redBearingOff;
        }
        return false;
    }

    public void updateGameStatus() {
        gameOver = whiteCheckersLeft == 0 || redCheckersLeft == 0;
    }

    // TODO: Bear-in/off
    // TODO: Player turns
    // TODO: Bar - hitting and entering
    // TODO: win/lose state

    public static void main(String[] args) {

        Game game = new Game();
        Move move = new Move();
        game.board.setUpCheckers();
        game.board.displayBoard(game);
        Scanner input = new Scanner(System.in);


        while (!game.gameOver) {

            System.out.print("\n< Press enter to roll dice >");
            input.nextLine();
            ArrayList<Integer> diceCasts = game.rollDice();
            System.out.println("\nDice Values: " + diceCasts.toString());


            for (int i = 0; i < diceCasts.size(); i++) {
                boolean moveValid = false;
                while (!moveValid) {
                    System.out.println("\nPlayer turn: " + game.playerTurn);
                    System.out.println("Die value:   " + diceCasts.get(i));
                    System.out.print("\nEnter pip to move checker from: ");
                    int pipChoice = input.nextInt();

                    // Check that pip being moved from exists
                    if (pipChoice < 1 || pipChoice > 24) {
                        System.out.println("\n    --- PIP OUT OF BOUNDS ---");
                    } else {
                        moveValid = game.move.moveChecker(game.playerTurn, game.board.getPip(pipChoice - 1), diceCasts.get(i),
                                game.getBearingOffStatus(game.playerTurn), game, game.board);
                        if (!moveValid) {
                            System.out.println("\n    --- INVALID MOVE ---");
                        }
                    }

                    game.updateBearingOffStatus(game.playerTurn);
                    game.board.displayBoard(game);
                }
            }
            game.nextTurn();
            game.updateGameStatus();
        }

    }
}