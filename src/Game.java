import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class for playing backgammon games. Run main to play!
 *
 * @author  Jordan & Sam
 * @version 2021-05-13
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
        whiteCheckersLeft = board.getWhiteCheckerCount();  // reads the board for the number of WHITE checkers set
        redCheckersLeft = board.getRedCheckerCount();  // reads the board for the number of RED checkers set
        updateGameStatus();
    }

    /**
     * Switch currentPlayerColor.
     */
    public void nextTurn() {
        // Switch color if the game is not over
        if (!gameOver) {
            if (currentPlayerColor == Color.WHITE) {
                currentPlayerColor = Color.RED;
            } else {
                currentPlayerColor = Color.WHITE;
            }
        }
    }

    /**
     * Get the current player.
     *
     * @return The current player's color
     */
    public Color getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    /**
     * Get the board.
     *
     * @return The board
     */
    public Board getBoard() {
        return board;
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
     * Update game-over and bearing-off statuses.
     */
    public void updateGameStatus() {
        // Update game-over status
        gameOver = whiteCheckersLeft == 0 || redCheckersLeft == 0;

        // Update bearing-off statuses
        int counterW = 0;
        // Iterate over WHITE'S home quadrant
        for (int i = 19; i < 25; i++) {
            // If found WHITE pip
            if (board.getPip(i).getColor() == Color.WHITE) {
                counterW += board.getPip(i).getCheckerCount();
            }
        }
        int counterR = 0;
        // Iterate over RED'S home quadrant
        for (int i = 1; i < 7; i++) {
            // If found RED pip
            if (board.getPip(i).getColor() == Color.RED) {
                counterR += board.getPip(i).getCheckerCount();
            }
        }
        whiteBearingOff = (counterW == whiteCheckersLeft);
        redBearingOff = (counterR == redCheckersLeft);
    }

    /**
     * Display the intro screen with title and instructions.
     *
     * @throws MalformedURLException if the URL link does not function properly
     */
    public void startScreen() throws MalformedURLException {
        URL link = new URL("https://en.wikipedia.org/wiki/Backgammon");
        System.out.println("\n\n    -------------------------------------------");
        System.out.println("    W E L C O M E    T O    B A C K G A M M O N");
        System.out.println("    -------------------------------------------");
        System.out.println("              A game by Jordan & Sam\n\n");
        System.out.println("    INSTRUCTIONS:");
        System.out.println("       * To learn how to play backgammon, click this link: " + link);
        System.out.println("       * The current version of the game is played here in the terminal.");
        System.out.println("       * The positions on which the checkers stand are called 'pips'.");
        System.out.println("       * These are displayed as indices ranging from 1 to 24.");
        System.out.println("       * The checkers are displayed on each pip as two-character combinations of their");
        System.out.println("         number and color. Ex: '2R' means two red checkers are standing on this pip. The");
        System.out.println("         checkers have also been colored in the terminal to make gameplay easier.");
        System.out.println("       * The bar contains the hit checkers for respective color and the number of checkers");
        System.out.println("         in a bar is given inside of the '< >' signs.");
        System.out.println("       * Upon bearing off, the borne-off checkers are not displayed. Instead, they are simply");
        System.out.println("         removed from the board and the number of checkers on the board are used to determine");
        System.out.println("         who is winning.");

        System.out.print("\n\n< Press enter to start the game >");
        // Read in enter line
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Randomize player turn at game start.
     */
    public void decideStartingPlayer() {
        rand = new Random();
        int r = rand.nextInt(2);
        currentPlayerColor = (r==0 ? Color.WHITE : Color.RED);
    }

    /**
     * Run the game.
     *
     * @throws MalformedURLException if URL links do not function properly
     */
    public static void main(String[] args) throws MalformedURLException {
        // Start new game
        Game game = new Game();
        // Display start screen : continue to game upon enter press
        game.startScreen();
        // Display start screen
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
            while (diceCasts.size() > 0 && !game.gameOver) {
                // Print dice values
                System.out.println("\nDice: " + diceCasts.toString());

                // Loop until the player has made a valid move
                boolean moveValid = false;

                // Check if the player can play
                if (!game.move.canPlay(game.currentPlayerColor, diceCasts, game.getBearingOffStatus(game.currentPlayerColor), game.board)) {
                    diceCasts.clear();
                    moveValid = true;
                }

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
                    // TODO: Update code for new canPlay method
                    // Check if player can move the checker
                    if (!game.move.canMove(game.currentPlayerColor, chosenPip, diceCasts,
                            game.getBearingOffStatus(game.currentPlayerColor), game.board)) {
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

        // Print the winner
        System.out.println("\n    --- GAME OVER --- \n\n" + "    "+game.currentPlayerColor + " player won!");
    }
}