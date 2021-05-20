import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

/**
 * Generates graphics for Backgammon game.
 *
 * @author  Jordan & Sam
 * @version 2021-05-20
 */
public class GUI {


    // Coordinates and sizes constants for components on the board.
    public static final int BOARD_HEIGHT = 580;
    public static final int BOARD_WIDTH = 664;
    public static final int BEZEL = 10;
    public static final int OUTER_LEFT_EDGE = 308;
    public static final int OUTER_TOP_EDGE = 50;
    public static final int INNER_LEFT_EDGE = OUTER_LEFT_EDGE + BEZEL;
    public static final int INNER_TOP_EDGE = OUTER_TOP_EDGE + BEZEL;
    public static final int BOARD_X_AXIS = 640;
    public static final int BOARD_Y_AXIS = 340;
    public static final int PIP_LENGTH = 220;
    public static final int PIP_WIDTH = 48;
    public static final int CHECKER_DIAMETER = 44;
    public static final int BAR_WIDTH = 68;
    public static final int BOARD_DICE_DISTANCE = 50;
    public static final int DICE_TRAY_WIDTH = 94;
    public static final int DICE_TRAY_HEIGHT = 168;
    public static final int DICE_WIDTH = 64;
    public static final int DICE_TRAY_LEFT = OUTER_LEFT_EDGE - BOARD_DICE_DISTANCE - DICE_TRAY_WIDTH;
    public static final int DICE_TRAY_RIGHT = DICE_TRAY_LEFT + DICE_TRAY_WIDTH;
    public static final int DICE_TRAY_TOP = BOARD_Y_AXIS - DICE_TRAY_HEIGHT/2;
    public static final int OUTER_RIGHT_EDGE = OUTER_LEFT_EDGE + BOARD_WIDTH;
    public static final int OUTER_BOTTOM_EDGE = OUTER_TOP_EDGE + BOARD_HEIGHT;
    public static final int INNER_RIGHT_EDGE = OUTER_RIGHT_EDGE - BEZEL;
    public static final int INNER_BOTTOM_EDGE = OUTER_BOTTOM_EDGE - BEZEL;

    // Colors used in the game
    private static final java.awt.Color RED_PIP = new java.awt.Color(255, 44, 19);
    private static final java.awt.Color WHITE_PIP = new java.awt.Color(255, 221, 215);
    private static final java.awt.Color BOARD_COLOR = new java.awt.Color(213, 123, 77);
    private static final java.awt.Color BORDER_COLOR = new java.awt.Color(147, 64, 24);
    private static final java.awt.Color BAR_COLOR = new java.awt.Color(172, 76, 27);
    private static final java.awt.Color ROLL_DICE_COLOR = new java.awt.Color(253, 113, 41);
    private static final java.awt.Color RED_CHECKER = new java.awt.Color(134, 19, 0);
    private static final java.awt.Color RED_CHECKER_BORDER = new java.awt.Color(98, 4, 2);
    private static final java.awt.Color WHITE_CHECKER = new java.awt.Color(238, 225, 203);
    private static final java.awt.Color WHITE_CHECKER_BORDER = new java.awt.Color(196, 176, 159);

    // Panels on the GUI
    JFrame window;
    DiceTrayP diceTrayPanel;
    DiceFacesP diceFacesPanel;
    RollDiceP rollDicePanel;
    BoardP boardPanel;
    BarP barPanel;
    GoalP goalPanel;
    PipsP pipsPanel;
    CheckersP checkersPanel;
    CheckersInGoalP chkInGoalPanel;
    ActivePlayerP activePlayerPanel;

    public GUI() {
        window = new JFrame();
        window.setSize(1280, 720);
        window.setTitle("Backgammon");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // the game closes when you press X
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        // Set the icon
        ImageIcon img = new ImageIcon("Img/backgammon-icon.png");
        window.setIconImage(img.getImage());

        // Create base pane for layering panels
        JLayeredPane base = new JLayeredPane();
        base.setOpaque(false);

        diceTrayPanel = new DiceTrayP();
        diceFacesPanel = new DiceFacesP();
        rollDicePanel = new RollDiceP();
        boardPanel = new BoardP();
        barPanel = new BarP();
        goalPanel = new GoalP();
        pipsPanel = new PipsP();
        checkersPanel = new CheckersP();
        chkInGoalPanel = new CheckersInGoalP();
        activePlayerPanel = new ActivePlayerP();

        // Add the panels to the frame
        // The panel added first will be on top
        base.add(activePlayerPanel, 0, -1);
        base.add(chkInGoalPanel, 0, -1);
        base.add(checkersPanel, 0, -1);
        base.add(goalPanel, 0, -1);
        base.add(pipsPanel,0,-1);
        base.add(barPanel,0,-1);
        base.add(boardPanel,0,-1);
        base.add(rollDicePanel, 0, -1);
        base.add(diceFacesPanel, 0, -1);
        base.add(diceTrayPanel, 0, -1);

        window.add(base);
        window.setVisible(true);
    }

    /**
     * Paints the outer board.
     */
    private class BoardP extends JPanel {
        public BoardP() {
            setBounds( OUTER_LEFT_EDGE,  OUTER_TOP_EDGE,  BOARD_WIDTH,  BOARD_HEIGHT );
            setBackground(BOARD_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR,10));
        }
    }

    /**
     * Paints the bar
     */
    private class BarP extends JPanel {
        public BarP() {
            setBounds( BOARD_X_AXIS-(BAR_WIDTH/2),  OUTER_TOP_EDGE,  BAR_WIDTH,BOARD_HEIGHT );
            setBackground(BAR_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 10));
        }
    }

    /**
     * Paints the dice tray
     */
    private class DiceTrayP extends JPanel {
        public DiceTrayP() {
            setBounds( DICE_TRAY_LEFT,  DICE_TRAY_TOP,  DICE_TRAY_WIDTH,  DICE_TRAY_HEIGHT );
            setBackground(BOARD_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR,5));
            setOpaque(true);
        }
    }

    /**
     * Paints  the checkers in the goal
     */
    public class CheckersInGoalP extends JPanel {

        int whiteCheckersInGoal;
        int redCheckersInGoal;

        public CheckersInGoalP() {
            setBounds(OUTER_RIGHT_EDGE + BOARD_DICE_DISTANCE / 2, OUTER_TOP_EDGE, CHECKER_DIAMETER + 30, BOARD_HEIGHT);
            setOpaque(false);
        }

        /**
         * Update the fields that keeps track of the checkers in white's and red's goal and paint the new checker.
         *
         * @param whiteCheckers The number of white checkers in goal
         * @param redCheckers The number of red checkers in goal
         */
        public void updateCheckers(int whiteCheckers, int redCheckers) {
            whiteCheckersInGoal = whiteCheckers;
            redCheckersInGoal = redCheckers;
            repaint();
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            // Draw the goal divider
            g2.setColor(BORDER_COLOR);
            g2.setStroke(new BasicStroke(10));
            g2.drawLine(0, BOARD_Y_AXIS-OUTER_TOP_EDGE, CHECKER_DIAMETER+30, BOARD_Y_AXIS-OUTER_TOP_EDGE);
            // Common settings
            int checkerThickness = 14;
            int checkerSpacing = 3;
            int initialPadding = 11;
            int xp = BEZEL+5;
            // RED settings
            g2.setColor(RED_CHECKER);
            int yp = BEZEL+initialPadding;
            for (int i = 0; i < redCheckersInGoal; i++) {
                g2.fillRect(xp,yp, CHECKER_DIAMETER, checkerThickness); // paint the red checkers
                yp += checkerThickness + checkerSpacing;
            }
            // WHITE settings
            g2.setColor(WHITE_CHECKER);
            yp = BOARD_HEIGHT - BEZEL - checkerThickness - initialPadding; // bottom of the red goal
            for (int i = 0; i < whiteCheckersInGoal; i++) {
                g2.fillRect(xp,yp, CHECKER_DIAMETER, checkerThickness); // paint the white checkers
                yp -= checkerThickness + checkerSpacing;
            }
        }
    }

    /**
     * Paints and updates the checkers on the pips and bar.
     */
    public class CheckersP extends JPanel {

        ArrayList<Pip> pips;
        int highlightPipIndex = -1;

        public CheckersP() {
            setBounds( INNER_LEFT_EDGE,  INNER_TOP_EDGE,  BOARD_WIDTH,  BOARD_HEIGHT );
            setOpaque(false);
        }

        /**
         * Updates the internal list of where the checkers are placed.
         *
         * @param newPips A updated list of the checkers placement
         */
        public void updatePips(ArrayList<Pip> newPips) {
            pips = newPips;
            repaint();
        }

        /**
         * Highlights the top checker of a pip.
         *
         * @param pipIndex The index of the pip whose last checker should be highlighted
         */
        public void highlightChecker(int pipIndex) {
            highlightPipIndex = pipIndex;
            repaint();
        }

        /**
         * Remove the highlight for the highlighted checker.
         */
        public void resetHighlight() {
            highlightPipIndex = -1;
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            // Paint the checkers in upper quadrants
            int index = 12;
            for (int x = 0; x < INNER_RIGHT_EDGE-INNER_LEFT_EDGE; x += PIP_WIDTH) {
                if (index == 6) { // set up for painting the bar
                    index = 0;
                    x += BEZEL;
                } else if (index == -1) { // go back to paint the pips
                    index = 6;
                    x += BEZEL;
                }
                int checkersOnPip = pips.get(index).getCheckerCount();
                int checkersPlaced = 0;
                Color checkerColor = pips.get(index).getColor() == Player.WHITE ? WHITE_CHECKER : RED_CHECKER;
                Color checkerBorderColor = pips.get(index).getColor() == Player.WHITE ? WHITE_CHECKER_BORDER : RED_CHECKER_BORDER;
                for (int y = 0; checkersPlaced < checkersOnPip; y += PIP_WIDTH - 1) { // paints checkers until all checkers on pip has been painted
                    switch (checkersPlaced) { // adjust y-axis when pip becomes full
                        case 5:
                            y = PIP_WIDTH/2;
                            break;
                        case 9:
                            y = 2*PIP_WIDTH/2;
                            break;
                        case 12:
                            y = 3*PIP_WIDTH/2;
                            break;
                        case 14:
                            y = 4*PIP_WIDTH/2;
                            break;
                    }
                    g2.setColor(checkerColor);
                    g2.fillOval(x, y, CHECKER_DIAMETER, CHECKER_DIAMETER);
                    g2.setColor(checkerBorderColor);
                    g2.setStroke(new BasicStroke(8));
                    g2.drawOval(x + 4, y + 4, CHECKER_DIAMETER-4, CHECKER_DIAMETER-4);
                    checkersPlaced++;

                    // paint highlight
                    if (index == highlightPipIndex && checkersPlaced == checkersOnPip) {
                        g2.setColor(new Color(253, 172, 12));
                        g2.setStroke(new BasicStroke(4));
                        g2.drawOval(x + 1, y + 1, CHECKER_DIAMETER + 2, CHECKER_DIAMETER + 2);
                    }
                }
                index--;
            }

            // Paint the checkers in lower quadrants
            index = 13;
            for (int x = 0; x < INNER_RIGHT_EDGE-INNER_LEFT_EDGE; x += PIP_WIDTH) {
                if (index == 19) { // set up for painting the bar
                    index = 25;
                    x += BEZEL;
                } else if (index == 26) { // go back to paint the pips
                    index = 19;
                    x += BEZEL;
                }
                int checkersOnPip = pips.get(index).getCheckerCount();
                int checkersPlaced = 0;
                Color checkerColor = pips.get(index).getColor() == Player.WHITE ? WHITE_CHECKER : RED_CHECKER;
                Color checkerBorderColor = pips.get(index).getColor() == Player.WHITE ? WHITE_CHECKER_BORDER : RED_CHECKER_BORDER;
                int startPos = BOARD_HEIGHT- 2*BEZEL-PIP_WIDTH;
                for (int y = startPos; checkersPlaced < checkersOnPip; y -= PIP_WIDTH - 1) { // paints checkers until all checkers on pip has been painted
                    switch (checkersPlaced) { // adjust y-axis when pip becomes full
                        case 5:
                            y = startPos - PIP_WIDTH/2;
                            break;
                        case 9:
                            y = startPos - 2*PIP_WIDTH/2;
                            break;
                        case 12:
                            y = startPos - 3*PIP_WIDTH/2;
                            break;
                        case 14:
                            y = startPos - 4*PIP_WIDTH / 2;
                            break;
                    }
                    g2.setColor(checkerColor);
                    g2.fillOval(x, y, CHECKER_DIAMETER, CHECKER_DIAMETER);
                    g2.setColor(checkerBorderColor);
                    g2.setStroke(new BasicStroke(8));
                    g2.drawOval(x + 4, y + 4, CHECKER_DIAMETER-4, CHECKER_DIAMETER-4);
                    checkersPlaced++;

                    // paint highlight
                    if (index == highlightPipIndex && checkersPlaced == checkersOnPip) {
                        g2.setColor(new Color(253, 172, 12));
                        g2.setStroke(new BasicStroke(4));
                        g2.drawOval(x + 1, y + 1, CHECKER_DIAMETER + 2, CHECKER_DIAMETER + 2);
                    }
                }
                index++;
            }
        }
    }

    /**
     * Paints the board.
     */
    public class GoalP extends JPanel {
        public GoalP() {
            setBounds(OUTER_RIGHT_EDGE + BOARD_DICE_DISTANCE / 2, OUTER_TOP_EDGE, CHECKER_DIAMETER + 30, BOARD_HEIGHT);
            setBackground(BOARD_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR, BEZEL));
        }

        /**
         * Calculates where the mouse has clicked.
         *
         * @param x The x coordinate
         * @param y The y coordinate
         * @return -1 if clicked outside of goal, 0 if clicked on red's goal and 25 if clicked on white's goal
         */
        public int mouseOnGoalIndex(int x, int y) {
            int pipIndex = -1;
            x -= OUTER_RIGHT_EDGE + BOARD_DICE_DISTANCE / 2;
            y -= OUTER_TOP_EDGE;
            if (x > BEZEL && x < CHECKER_DIAMETER + 30 - BEZEL) {
                if (y > BEZEL && y < BOARD_HEIGHT / 2 - 5) {
                    pipIndex = 0; // red's goal
                } else if (y > INNER_BOTTOM_EDGE - BOARD_HEIGHT / 2 + 5 && y < INNER_BOTTOM_EDGE) {
                    pipIndex = 25; // white's goal
                }
            }
            return pipIndex;
        }
    }

    /**
     * Paints the pips.
     */
    public class PipsP extends JPanel {

        private int[] xp;
        private int[] yp;

        public PipsP() {
            setBounds( INNER_LEFT_EDGE,  INNER_TOP_EDGE,  BOARD_WIDTH,  BOARD_HEIGHT );
        }

        /**
         * Calculates where the mouse has clicked.
         *
         * @param x The x coordinate
         * @param y The y coordinate
         * @return -1 if clicked outside of goal, otherwise the index of the pip/bar (0--25)
         */
        public int mouseOnPipIndex(int x, int y) {
            int pipIndex = -1;
            x -= INNER_LEFT_EDGE;
            if (y > INNER_TOP_EDGE && y < INNER_TOP_EDGE + 5*(CHECKER_DIAMETER+3)) {
                if (x > 0 && x < 6*PIP_WIDTH) {
                    pipIndex = 12-x/PIP_WIDTH; // red outer quadrant
                }
                else if (x > 6*PIP_WIDTH && x < 6*PIP_WIDTH + BAR_WIDTH) {
                    pipIndex = 0; // white's bar
                }
                else if (x > 6*PIP_WIDTH + BAR_WIDTH && x < 12*PIP_WIDTH + BAR_WIDTH) {
                    x -= 6*PIP_WIDTH + BAR_WIDTH;
                    pipIndex = 6-x/PIP_WIDTH; // red home quadrant
                }
            }
            else if (y > INNER_BOTTOM_EDGE - 5*(CHECKER_DIAMETER+3) && y < INNER_BOTTOM_EDGE) {
                if (x > 0 && x < 6*PIP_WIDTH) {
                    pipIndex = 13+x/PIP_WIDTH; // white outer quadrant
                }
                else if (x > 6*PIP_WIDTH && x < 6*PIP_WIDTH + BAR_WIDTH) {
                    pipIndex = 25; // red's bar
                }
                else if (x > 6*PIP_WIDTH + BAR_WIDTH && x < 12*PIP_WIDTH + BAR_WIDTH) {
                    x -= 6*PIP_WIDTH + BAR_WIDTH;
                    pipIndex = 19+x/PIP_WIDTH; // red home quadrant
                }
            }
            return pipIndex;
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            xp = new int[]{0, PIP_WIDTH/2, PIP_WIDTH};
            yp = new int[]{0, PIP_LENGTH, 0};

            int bottom = BOARD_HEIGHT-2*BEZEL;

            for (int i = 0; i < 24; i++) {
                if (i==12) { // change to coordinates for the bottom pips
                    xp = new int[]{0, PIP_WIDTH/2, PIP_WIDTH};
                    yp = new int[]{bottom, bottom-PIP_LENGTH, bottom};
                }
                g2.setColor((i%2 == 0 && i < 12) || (i%2 == 1 && i >= 12) ? WHITE_PIP : RED_PIP); // alternate between white and red
                g2.fillPolygon(xp, yp, 3);
                // Move to next pip
                xp[0] += PIP_WIDTH;
                xp[1] += PIP_WIDTH;
                xp[2] += PIP_WIDTH;

                if (i%12 == 5) { // jump over the bar
                    xp[0] += BAR_WIDTH;
                    xp[1] += BAR_WIDTH;
                    xp[2] += BAR_WIDTH;
                }
            }
        }
    }

    /**
     * Paints the dice.
     */
    public class DiceFacesP extends JPanel {

        ArrayList<Integer> diceValues = new ArrayList<>(Arrays.asList(1,1));

        int spotSize = 8;
        int spotLeft = (DICE_TRAY_WIDTH - DICE_WIDTH)/2 + spotSize + 4;
        int D1SpotTop = spotLeft;
        int D2SpotTop = D1SpotTop + DICE_WIDTH + 10;

        public DiceFacesP() {
            setBounds( DICE_TRAY_LEFT,  DICE_TRAY_TOP,  DICE_TRAY_WIDTH,  DICE_TRAY_HEIGHT );
            setOpaque(false);
        }

        /**
         * Paints new dice on the board.
         *
         * @param dv A list of new dice values
         */
        public void updateDiceValues(ArrayList<Integer> dv) {
            diceValues = new ArrayList<>(dv); // make sure that it is a copy and not a reference
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            // Draw first dice
            g2.setColor(Color.white);
            g2.fillRect(15, 15,  DICE_WIDTH,  DICE_WIDTH);
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(4));
            g2.drawRect(15, 15, DICE_WIDTH, DICE_WIDTH);

            // Draw second dice
            g2.setColor(Color.white);
            g2.fillRect(15, 15 + DICE_WIDTH + 10,  DICE_WIDTH,  DICE_WIDTH);
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(4));
            g2.drawRect(15, 15 + DICE_WIDTH + 10, DICE_WIDTH, DICE_WIDTH);

            // Draw spots
            int spotTop = D1SpotTop;
            int tr = 2*spotSize;
            for (int numSpots : diceValues) {
                switch (numSpots) { // exploit that spots on die 1 is a subset of die 3 which is a subset of die 5
                    case 5:
                        g2.fillRect(spotLeft, spotTop, spotSize, spotSize);
                        g2.fillRect(spotLeft + 2*tr, spotTop + 2*tr, spotSize, spotSize);
                    case 3:
                        g2.fillRect(spotLeft, spotTop + 2*tr, spotSize, spotSize);
                        g2.fillRect(spotLeft + 2*tr, spotTop, spotSize, spotSize);
                    case 1:
                        g2.fillRect(spotLeft + tr, spotTop + 2 * spotSize, spotSize, spotSize);
                        break;
                    case 6:
                        g2.fillRect(spotLeft, spotTop + tr, spotSize, spotSize);
                        g2.fillRect(spotLeft + 2*tr, spotTop + tr, spotSize, spotSize);
                    case 4:
                        g2.fillRect(spotLeft, spotTop, spotSize, spotSize);
                        g2.fillRect(spotLeft + 2*tr, spotTop + 2*tr, spotSize, spotSize);
                    case 2:
                        g2.fillRect(spotLeft, spotTop + 2*tr, spotSize, spotSize);
                        g2.fillRect(spotLeft + 2*tr, spotTop, spotSize, spotSize);
                        break;
                }
                spotTop = D2SpotTop;
            }
        }
    }

    /**
     * Paints roll dice button
     */
    public class RollDiceP extends JPanel {
        public RollDiceP() {
            setBounds( DICE_TRAY_LEFT,  DICE_TRAY_TOP + DICE_TRAY_HEIGHT + 20,  DICE_TRAY_WIDTH,  DICE_TRAY_WIDTH );
            setBackground(ROLL_DICE_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR,5));
            setOpaque(true);

            JLabel textRoll = new JLabel("<html><br><h2>ROLL</h2></html>");
            textRoll.setSize(DICE_TRAY_WIDTH-20, DICE_TRAY_WIDTH-20);
            JLabel textDice = new JLabel("<html><h2>DICE</h2><br><br></html>");
            this.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.gridy = 1;
            this.add(textRoll, c);
            c.gridy = 2;
            this.add(textDice, c);
        }

        /**
         * Checks if the button is clicked or not.
         *
         * @param xp The x coordinate
         * @param yp The y coordinate
         * @return True if button is clicked : False otherwise
         */
        public boolean isMouseOn(int xp, int yp) {
            if (xp > DICE_TRAY_LEFT && xp < DICE_TRAY_RIGHT && yp > DICE_TRAY_TOP + DICE_TRAY_HEIGHT + 20 &&
                    yp < DICE_TRAY_TOP + DICE_TRAY_HEIGHT + DICE_TRAY_WIDTH) {
                return true;
            }
            return false;
        }
    }

    /**
     * Displays the current player.
     */
    public class ActivePlayerP extends JPanel {

        JLabel currentPlayer;

        public ActivePlayerP() {
            setBounds( DICE_TRAY_LEFT,  DICE_TRAY_TOP - DICE_TRAY_WIDTH - 20,  DICE_TRAY_WIDTH,  DICE_TRAY_WIDTH );
            setBackground(ROLL_DICE_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR,5));
            setOpaque(true);

            currentPlayer = new JLabel("<html><br><h2>WHITE</h2></html>");
            currentPlayer.setSize(DICE_TRAY_WIDTH-20, DICE_TRAY_WIDTH-20);
            this.add(currentPlayer);
        }

        public void updateBackground(Player col) {
            setBackground(col == Player.WHITE ? WHITE_PIP : RED_PIP);
        }

        public void updateText(Player p) {
            if (p == Player.WHITE) {
                currentPlayer.setText("<html><br><h2>WHITE</h2></html>");
            } else {
                currentPlayer.setText("<html><br><h2>RED</h2></html>");
            }
        }
    }
}
