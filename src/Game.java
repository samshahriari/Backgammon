import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for playing backgammon games. Run main to play!
 *
 * @author  Jordan & Sam
 * @version 2021-05-17
 */
public class Game implements MouseListener {

    private boolean gameOver;
    private Board board;
    private Move move;
    Random rand;
    private Player currentPlayerColor;
    private int whiteCheckersLeft;
    private int redCheckersLeft;
    private final int whiteCheckersAtStart;
    private final int redCheckersAtStart;
    private int whiteCheckersInGoal;
    private int redCheckersInGoal;
    private boolean whiteBearingOff;
    private boolean redBearingOff;
    private GUI gui;

    // Gameplay fields
    ArrayList<Integer> diceValues;
    int selection1 = -1;
    int selection2 = -1;


    /**
     * Set up a new game and initialize its properties.
     */
    public Game() {
        board = new Board();
        move = new Move();
        gui = new GUI();
        whiteCheckersAtStart = board.getWhiteCheckerCount();  // reads the board for the number of WHITE checkers set
        redCheckersAtStart = board.getRedCheckerCount();  // reads the board for the number of RED checkers set
        whiteCheckersLeft = whiteCheckersAtStart;
        redCheckersLeft = redCheckersAtStart;
        diceValues = new ArrayList<>();
        decideStartingPlayer();
        updateGameStatus();
    }

    /**
     * Switch currentPlayerColor.
     */
    public void nextTurn() {
        // Switch color if the game is not over
        if (!gameOver) {
            if (currentPlayerColor == Player.WHITE) {
                currentPlayerColor = Player.RED;
            } else {
                currentPlayerColor = Player.WHITE;
            }
        }
    }

    /**
     * Get the current player.
     *
     * @return The current player's color
     */
    public Player getCurrentPlayerColor() {
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
    public boolean decreaseCheckersLeft(Player col) {
        if (col == Player.WHITE) {
            whiteCheckersLeft--;
            return true;
        } else if (col == Player.RED) {
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
    public boolean getBearingOffStatus(Player col) {
        if (col == Player.WHITE) {
            return whiteBearingOff;
        } else if (col == Player.RED) {
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
            if (board.getPip(i).getColor() == Player.WHITE) {
                counterW += board.getPip(i).getCheckerCount();
            }
        }
        int counterR = 0;
        // Iterate over RED'S home quadrant
        for (int i = 1; i < 7; i++) {
            // If found RED pip
            if (board.getPip(i).getColor() == Player.RED) {
                counterR += board.getPip(i).getCheckerCount();
            }
        }
        whiteCheckersInGoal = whiteCheckersAtStart - whiteCheckersLeft;
        redCheckersInGoal = redCheckersAtStart - redCheckersLeft;
        whiteBearingOff = (counterW == whiteCheckersLeft);
        redBearingOff = (counterR == redCheckersLeft);

        if (!move.canPlay(this, diceValues)) {
            nextTurn();
            diceValues.clear();
        }
        gui.activePlayerPanel.updateText(currentPlayerColor);
        gui.activePlayerPanel.updateBackground(currentPlayerColor);
        gui.checkersPanel.updatePips(board.getPips());
        gui.chkInGoalPanel.updateCheckers(whiteCheckersInGoal, redCheckersInGoal);

        if (gameOver) {
            String message = currentPlayerColor + " won the game!";
            JOptionPane.showMessageDialog(gui.window, message);
        }
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
        currentPlayerColor = (r==0 ? Player.WHITE : Player.RED);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gui.window.addMouseListener(game);
    }



    public void eventHandler(int xp, int yp) {
        // Check if click is on roll-dice button
        if (gui.rollDicePanel.isMouseOn(xp,yp) && diceValues.size() == 0) {
            diceValues = rollDice();
            gui.diceFacesPanel.updateDiceValues(diceValues);
            // Check if click is on pip
            updateGameStatus();
        }
        int clickedPipIndex = gui.pipsPanel.mouseOnPipIndex(xp,yp);

        int clickedGoalIndex = gui.goalPanel.mouseOnGoalIndex(xp,yp);
        System.out.println(clickedGoalIndex);
        if (clickedGoalIndex == 0 || clickedGoalIndex == 25) {
            clickedPipIndex = clickedGoalIndex;
        }

        if (clickedPipIndex > -1 && clickedPipIndex < 26) {


            if (selection1 == -1 && isNotBarMoveWhenBarHasCheckers(clickedPipIndex)) {
                return;
            }

            // If you click on your currently selected pip, it deselects
            if (clickedPipIndex == selection1) {
                selection1 = -1;
                gui.checkersPanel.resetHighlight();
                // Check if click is on pip
                updateGameStatus();
            } else if (selection1 == -1) {
                if (move.canMove(this, board.getPip(clickedPipIndex), diceValues)) {
                    selection1 = clickedPipIndex;
                    gui.checkersPanel.highlightChecker(selection1);
                    System.out.println("Selection1 index: " + clickedPipIndex);
                }
            } else {
                int moveDist = (clickedPipIndex - selection1)*move.getDirection(currentPlayerColor);

                boolean validMoveDist = false;
                for (int dieValue : diceValues) {
                    if (moveDist <= dieValue) {
                        validMoveDist = true;
                        break;
                    }
                }

                boolean isBearingOffMove = (clickedPipIndex == 0 || clickedPipIndex == 25)
                        && getBearingOffStatus(currentPlayerColor) && validMoveDist;
                if (moveDist > 0 && (diceValues.contains(moveDist) || isBearingOffMove)) {
                    selection2 = clickedPipIndex;
                    System.out.println("Selection2 index: " + clickedPipIndex);
                    if (move.moveChecker(this, board.getPip(selection1), moveDist)) {
                        gui.checkersPanel.resetHighlight();
                        while (!diceValues.remove((Integer) moveDist)) {
                            moveDist++;
                        }
                        selection1 = -1;
                        selection2 = -1;
                        System.out.println(diceValues);
                        updateGameStatus();
                    }
                }
            }
        }
    }

    /**
     * Check if the current player tries to make a move from a pip that is not the bar when the player has checkers on bar.
     *
     * @param clickedPipIndex The index of the pip that the player want to move from
     * @return True if the bar has checkers AND the clicked pip is NOT the bar, otherwise false.
     */
    private boolean isNotBarMoveWhenBarHasCheckers(int clickedPipIndex) {

        boolean hasCheckersInBar = board.getBar(currentPlayerColor).containsCheckers();
        return hasCheckersInBar && clickedPipIndex != (currentPlayerColor == Player.RED ? 25 : 0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(e.getX() + " " + (e.getY()-30));
        eventHandler(e.getX(), e.getY()-30);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}