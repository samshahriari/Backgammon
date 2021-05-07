import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class for playing backgammon games. Run main to play!
 *
 * @author  Jordan & Sam
 * @version 2021-05-07
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

    /**
     * Set up a new game and initialize its properties.
     */
    public Game() {
        board = new Board();
        move = new Move();
        gameOver = false;
        currentPlayerColor = Color.WHITE;  // WHITE starts the game
        whiteCheckersLeft = board.getWhiteCheckerCount();  // reads the board for the number of WHITE checkers set
        redCheckersLeft = board.getRedCheckerCount();  // reads the board for the number of RED checkers set
        updateBearingOffStatus(Color.WHITE);
        updateBearingOffStatus(Color.RED);
    }

    /**
     * Switch currentPlayerColor.
     */
    public void nextTurn() {
        // Switch color
        if (currentPlayerColor == Color.WHITE) {
            currentPlayerColor = Color.RED;
        } else {
            currentPlayerColor = Color.WHITE;
        }
    }

    /**
     * Roll the dice and return die values to determine player movement.
     *
     * @return An ArrayList containing die values (2 or 4 values)
     */
    public ArrayList<Integer> rollDice() {
        rand = new Random();
        // Create ArrayList to hold dice values
        ArrayList<Integer> diceValues = new ArrayList<>();
        // Cast the dice
        int dieCast1 = rand.nextInt(6) + 1;
        int dieCast2 = rand.nextInt(6) + 1;
        // Add the dice values to the Arraylist
        diceValues.add(dieCast1);
        diceValues.add(dieCast2);
        // Upon rolling doubles, duplicate the results giving 4 movements
        if (dieCast1 == dieCast2) {
            diceValues.add(dieCast1);
            diceValues.add(dieCast2);
        }
        return diceValues;
    }

    /**
     * Determine whether WHITE or RED is currently bearing off or not.
     *
     * @param col The Color of the current player
     */
    public void updateBearingOffStatus(Color col) {
        int lowerBounds = -1;
        int upperBounds = -1;
        int checkersLeft = -1;
        switch (col) {
            case WHITE:
                // Set the correct bounds for WHITE's home quadrant
                lowerBounds = 19;
                upperBounds = 25;
                // Get the number of WHITE checkers left to look for
                checkersLeft = whiteCheckersLeft;
                break;
            case RED:
                // Set the correct bounds for RED's home quadrant
                lowerBounds = 1;
                upperBounds = 7;
                // Get the number of RED checkers left to look for
                checkersLeft = redCheckersLeft;
                break;
        }
        // Iterate over home quadrant
        for (int i = lowerBounds; i < upperBounds; i++) {
            // If found pip of owned color
            if (board.getPip(i).getColor() == col) {
                // Subtract checker count of that pip from checkersLeft
                checkersLeft -= board.getPip(i).getCheckerCount();
            }
        }
        // If checkersLeft = 0, all the checkers are in the home quadrant
        boolean bearingOffStatus = (checkersLeft == 0);
        // Update the bearingOffStatus for the current player color
        if (col == Color.WHITE) {
            whiteBearingOff = bearingOffStatus;
        } else if (col == Color.RED) {
            redBearingOff = bearingOffStatus;
        }
    }

    /**
     * Decrease the whiteCheckersLeft or the redCheckersLeft fields depending on current player color.
     * Used in Move.moveChecker when a checker is borne off.
     *
     * @param col The Color of the current player
     * @return True if checkers were successfully decreased or return false if current player is not WHITE or RED
     */
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

    /**
     * Get the current bearing-off status for WHITE or RED.
     *
     * @param col The current player color
     * @return True if the current player is bearing off and false if they are not currently bearing off
     */
    public boolean getBearingOffStatus(Color col) {
        if (col == Color.WHITE) {
            return whiteBearingOff;
        } else if (col == Color.RED) {
            return redBearingOff;
        }
        return false;
    }

    /**
     * Verify if the game has ended by checking if either player has zero checkers left.
     */
    public void updateGameStatus() {
        gameOver = whiteCheckersLeft == 0 || redCheckersLeft == 0;
    }

    /**
     * Run the game.
     */
    public static void main(String[] args) {
        // Start new game
        Game game = new Game();
        game.board.displayBoard(game);
        Scanner input = new Scanner(System.in);

        while (!game.gameOver) {

            System.out.println("\nPlayer turn: " + game.currentPlayerColor);
            System.out.print("\n< Press enter to roll dice >");
            // Read in enter line
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Get dice values for dice roll
            ArrayList<Integer> diceCasts = game.rollDice();

            // Loop as long as there are moves to make
            while (diceCasts.size() > 0) {
                // Print dice values
                System.out.println("\nDice: " + diceCasts.toString());
                // Loop until the player has made a valid move
                boolean moveValid = false;
                while (!moveValid) {
                    int pipIndex;
                    Pip chosenPip;
                    boolean isBar = false;
                    // Get the bar for the current player
                    Pip bar = game.board.getBar(game.currentPlayerColor);
                    // If the bar has a checker on it, force the player to play that checker first
                    if (bar.getCheckerCount() > 0) {
                        chosenPip = bar;
                        isBar = true;
                    }
                    else {
                        // Ask player to choose a pip to move a checker from
                        System.out.print("\nEnter pip to move checker from: ");
                        pipIndex = input.nextInt();
                        // Check that the pip being moved from is on the board
                        if (pipIndex < 1 || pipIndex > 24) {
                            System.out.println("\n    --- PIP OUT OF BOUNDS ---");
                            continue;
                        }
                        // Use input as an index to retrieve the corresponding Pip object
                        chosenPip = game.board.getPip(pipIndex);

                        // Check that pip being moved from is owned
                        if (chosenPip.getColor() != game.currentPlayerColor) {
                            System.out.println("\n    --- PIP NOT OWNED ---");
                            continue;
                        }

                    }
                    // Check if checkers can move from chosen pip
                    if (!game.move.canMove(chosenPip, diceCasts, game.board)) {
                        // End turn if checker is on bar and there are no valid moves
                        if (isBar) {
                            diceCasts.clear();
                            moveValid = true;
                        } else {
                            System.out.println("No valid movement for pip. \nEnd turn? (y/n)");
                            String turnChoice = input.next().toLowerCase().trim();
                            // If player decides to end turn, the turn will end
                            if (turnChoice.equals("y") || turnChoice.equals("yes")) {
                                diceCasts.clear();
                                moveValid = true;
                            }
                        }
                        continue;
                    }
                    // Loop until player makes a valid movement choice
                    int dieChoice = -1;
                    while (!diceCasts.contains(dieChoice)) {
                        System.out.print("\nDie-value options: " + diceCasts.toString() + "\nWhich die value do you wish to use?\n> ");
                        dieChoice = input.nextInt();
                        // If player makes invalid choice, print error message
                        if (!diceCasts.contains(dieChoice)) {
                            System.out.println("\n    --- NO SUCH DIE VALUE ---");
                        }
                    }
                    // Retrieve verdict on whether the move is valid or not
                    moveValid = game.move.moveChecker(game.currentPlayerColor, chosenPip, dieChoice,
                            game.getBearingOffStatus(game.currentPlayerColor), game, game.board);
                    // If the move is valid, remove the used dice value, and update the game/board
                    if (moveValid) {
                        diceCasts.remove(Integer.valueOf(dieChoice));
                        game.updateBearingOffStatus(Color.WHITE);
                        game.updateBearingOffStatus(Color.RED);
                        game.board.displayBoard(game);
                        game.updateGameStatus();
                    } else {
                        // If the move was invalid, print error message
                        System.out.println("\n    --- INVALID MOVE ---");
                    }
                }
            }
            // Switch player at the end of the current players turn
            game.nextTurn();
        }
    }
}