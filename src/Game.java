import java.io.IOException;
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
    private Color currentPlayerColor;
    private int whiteCheckersLeft;
    private int redCheckersLeft;
    private boolean whiteBearingOff;
    private boolean redBearingOff;

    public Game() {
        gameOver = false;
        board = new Board();
        move = new Move();
        currentPlayerColor = Color.WHITE;
        whiteCheckersLeft = board.getWhiteCheckerCount();
        redCheckersLeft = board.getRedCheckerCount();
        updateBearingOffStatus(Color.WHITE);
        updateBearingOffStatus(Color.RED);
    }

    public void nextTurn() {
        if (currentPlayerColor == Color.WHITE) {
            currentPlayerColor = Color.RED;
        } else {
            currentPlayerColor = Color.WHITE;
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
        int checkersLeft = -1;
        switch (col) {
            case WHITE:
                lowerBounds = 19;
                upperBounds = 25;
                checkersLeft = whiteCheckersLeft;
                break;
            case RED:
                lowerBounds = 1;
                upperBounds = 7;
                checkersLeft = redCheckersLeft;
                break;
        }
        for (int i = lowerBounds; i < upperBounds; i++) {
            if (board.getPip(i).getColor() == col) {
                checkersLeft -= board.getPip(i).getCheckerCount();
            }
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

    public static void main(String[] args) {

        Game game = new Game();
        Move move = new Move();
        game.board.setUpCheckers();
        game.board.displayBoard(game);
        Scanner input = new Scanner(System.in);

        while (!game.gameOver) {

            System.out.println("\nPlayer turn: " + game.currentPlayerColor);
            System.out.print("\n< Press enter to roll dice >");

            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<Integer> diceCasts = game.rollDice();

            while (diceCasts.size() > 0) {
                System.out.println("\nDice: " + diceCasts.toString());

                boolean moveValid = false;
                while (!moveValid) {

                    int pipIndex;
                    Pip chosenPip;

                    Pip bar = game.board.getBar(game.currentPlayerColor);

                    if (bar.getCheckerCount() > 0) {
                        chosenPip = bar;
                    }
                    else {
                        System.out.print("\nEnter pip to move checker from: ");
                        pipIndex = input.nextInt();

                        // Check that pip being moved from exists
                        if (pipIndex < 1 || pipIndex > 24) {
                            System.out.println("\n    --- PIP OUT OF BOUNDS ---");
                            continue;
                        }

                        chosenPip = game.board.getPip(pipIndex);

                        // Check that pip being moved from is owned
                        if (chosenPip.getColor() != game.currentPlayerColor) {
                            System.out.println("\n    --- PIP NOT OWNED ---");
                            continue;
                        }
                    }

                    int dieChoice = -1;
                    while (!diceCasts.contains(dieChoice)) {
                        System.out.print("\nDie-value options: " + diceCasts.toString() + "\nWhich die value do you wish to use?\n> ");
                        dieChoice = input.nextInt();
                        if (!diceCasts.contains(dieChoice)) {
                            System.out.println("\n    --- NO SUCH DIE VALUE ---");
                        }
                    }

                    moveValid = game.move.moveChecker(game.currentPlayerColor, chosenPip, dieChoice,
                            game.getBearingOffStatus(game.currentPlayerColor), game, game.board);

                    if (moveValid) {
                        diceCasts.remove(Integer.valueOf(dieChoice));
                        game.updateBearingOffStatus(game.currentPlayerColor);
                        game.board.displayBoard(game);
                        game.updateGameStatus();
                    } else {
                        System.out.println("\n    --- INVALID MOVE ---");
                    }
                }
            }
            game.nextTurn();
        }
    }
}