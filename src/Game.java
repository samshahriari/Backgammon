import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class for playing backgammon games. Run main to play!
 *
 * @author  Jordan & Sam
 * @version 2021-05-20
 */
public class Game implements MouseListener {

    private boolean gameOver;
    private final Board board;
    private final Move move;
    private final GUI gui;
    private Player currentPlayerColor;
    private int whiteCheckersLeft;
    private int redCheckersLeft;
    private final int whiteCheckersAtStart;
    private final int redCheckersAtStart;
    private int whiteCheckersInGoal;
    private int redCheckersInGoal;
    private boolean whiteBearingOff;
    private boolean redBearingOff;

    // Declare diceValues variable
    ArrayList<Integer> diceValues;
    // Declare and initialize pip-selection fields
    int selection1 = -1;
    int selection2 = -1;
    boolean gameStarted = false;
    Random rand;

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
        // Initialize diceValues ArrayList as empty
        diceValues = new ArrayList<>();
        decideStartingPlayer();
        updateGameStatus();  // sets the initial game status
        gameStarted = true;
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
     * Get the backgammon board.
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
     * Update game-over status, bearing-off statuses, checkers in goals, and gui.
     * Auto-end turn for unplayable turns.
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
        // Calculate the number of checkers in respective color's goal
        whiteCheckersInGoal = whiteCheckersAtStart - whiteCheckersLeft;
        redCheckersInGoal = redCheckersAtStart - redCheckersLeft;
        // Determine bearing-off statuses
        whiteBearingOff = (counterW == whiteCheckersLeft);
        redBearingOff = (counterR == redCheckersLeft);

        // Update gui
        gui.checkersPanel.updatePips(board.getPips());
        gui.chkInGoalPanel.updateCheckers(whiteCheckersInGoal, redCheckersInGoal);

        // If player is unable to play at all, auto-end turn and clear the dice
        if (!gameOver && !move.canPlay(this, diceValues)) {
            if (!diceValues.isEmpty()) {
                JOptionPane.showMessageDialog(gui.window, "No valid moves, ending turn!");
            }
            nextTurn();
            diceValues.clear();
            gui.diceFacesPanel.updateDiceValues(diceValues);
        }
        gui.activePlayerPanel.updateDisplay(currentPlayerColor);

        if (gameOver) {
            String gameOverMessage = currentPlayerColor + " won the game!";
            JOptionPane.showMessageDialog(gui.window, gameOverMessage);
        }
    }

    /**
     * Welcome the player to the game.
     */
    public void startScreen() {
        JLabel message = new JLabel("<html><body><br>Welcome to BACKGAMMON!<br><br>A Jordan & Sam production</body></html>");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(gui.window, message);
    }

    /**
     * Randomize player turn at game start.
     */
    public void decideStartingPlayer() {
        rand = new Random();
        int r = rand.nextInt(2);
        currentPlayerColor = (r==0 ? Player.WHITE : Player.RED);
    }

    /**
     * Takes in mouse coordinates and determines what is being clicked and how to handle the clicks.
     * Handles pip, goal, and bar selections, button presses for roll dice, etc.
     *
     * @param xp mouse x-coordinates
     * @param yp mouse y-coordinates
     */
    public void eventHandler(int xp, int yp) {
        // Check if click is on roll-dice button
        if (gui.rollDicePanel.isMouseOn(xp,yp) && diceValues.size() == 0) {
            // Roll the dice and update the gui
            diceValues = rollDice();
            gui.diceFacesPanel.updateDiceValues(diceValues);
            updateGameStatus();
        }

        // Get the pip index mouse coordinates xp and yp
        int clickedPipIndex = gui.pipsPanel.mouseOnPipIndex(xp,yp);

        // Get the 'fake' goal index for mouse coordinates xp and yp : the goal uses bar indices in pips
        int clickedGoalIndex = gui.goalPanel.mouseOnGoalIndex(xp,yp);
        System.out.println(clickedGoalIndex);  // prints index in terminal
        // If the goal is in fact clicked, overwrite clickedPipIndex
        if (clickedGoalIndex == 0 || clickedGoalIndex == 25) {
            clickedPipIndex = clickedGoalIndex;
        }

        // Check for valid clickedPipIndex
        if (clickedPipIndex > -1 && clickedPipIndex < 26) {

            // Check if first pip selection is not bar when the bar has checkers
            if (selection1 == -1 && isNotBarMoveWhenBarHasCheckers(clickedPipIndex)) {
                return; // exits eventHandler
            }

            // Second click deselects checker/pip
            if (clickedPipIndex == selection1) {
                selection1 = -1;
                gui.checkersPanel.resetHighlight();
                updateGameStatus();
            }
            // Else if no first selection has been registered yet
            else if (selection1 == -1) {
                // And the chosen pips checkers are able to move
                if (move.canMove(this, board.getPip(clickedPipIndex), diceValues)) {
                    // Lock in first pip selection and enable highlight in gui
                    selection1 = clickedPipIndex;
                    System.out.println("Selection1 index: " + clickedPipIndex);  // prints index in terminal
                    gui.checkersPanel.highlightChecker(selection1);
                }
            } else {
                // Calculate the move distance between chosen pip and a previous selection
                int moveDist = (clickedPipIndex - selection1)*move.getDirection(currentPlayerColor);

                // Filter out move distances that are impossible for bearing off
                boolean validMoveDist = false;
                if (clickedPipIndex == 0 || clickedPipIndex == 25) {
                    // Dice values must be sorted in ascending order for the bearing-off movement to function properly
                    Collections.sort(diceValues);
                    for (int dieValue : diceValues) {
                        if (moveDist <= dieValue) {
                            validMoveDist = true;
                            moveDist = dieValue;
                            break;
                        }
                    }
                }

                // Check if the specified move is a bearing-off move
                boolean isBearingOffMove = (clickedPipIndex == 0 || clickedPipIndex == 25)
                        && getBearingOffStatus(currentPlayerColor) && validMoveDist;

                // Check that move is in the correct direction and that the dice allow movement
                if (moveDist > 0 && (diceValues.contains(moveDist) || isBearingOffMove)) {
                    // Lock in second pip selection
                    selection2 = clickedPipIndex;
                    System.out.println("Selection2 index: " + clickedPipIndex);  // prints index in terminal
                    // Attempt to move the checker
                    if (move.moveChecker(this, board.getPip(selection1), moveDist)) {
                        gui.checkersPanel.resetHighlight();

                        // Bearing off can be done with a die value larger than the actual distance required
                        // So loop until a moveDist is found that can be removed from diceValues
                        while (!diceValues.remove((Integer) moveDist)) {
                            moveDist++;
                        }

                        // Reset first and second pip selections and update the game status
                        selection1 = -1;
                        selection2 = -1;
                        System.out.println(diceValues);  // prints dice values in terminal
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
        boolean barNotChosen = clickedPipIndex != (currentPlayerColor == Player.RED ? 25 : 0);
        return hasCheckersInBar && barNotChosen;
    }

    /**
     * Catch mouse-releases and register the coordinates of the mouse and send to the event handler method.
     *
     * @param e MouseEvent of the form "release"
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(e.getX() + " " + (e.getY()-30));
        eventHandler(e.getX(), e.getY()-30);
    }

    // UNUSED MOUSE-LISTENER METHODS
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Run the game.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.gui.window.addMouseListener(game);
        game.startScreen();
    }
}