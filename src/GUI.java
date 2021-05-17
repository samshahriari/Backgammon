import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI {

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
    public static final int DICE_TRAY_TOP = BOARD_Y_AXIS - DICE_TRAY_HEIGHT/2;
    public static final int TITLE_BAR_HEIGHT = 30;
    public static final int OUTER_RIGHT_EDGE = OUTER_LEFT_EDGE + BOARD_WIDTH;
    public static final int OUTER_BOTTOM_EDGE = OUTER_TOP_EDGE + BOARD_HEIGHT;
    public static final int INNER_RIGHT_EDGE = OUTER_RIGHT_EDGE - BEZEL;
    public static final int INNER_BOTTOM_EDGE = OUTER_BOTTOM_EDGE - BEZEL;

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

    JFrame window;
    DiceTrayP diceTray;
    DiceFacesP diceFaces;
    RollDiceP rollDice;
    BoardP board;
    BarP bar;
    GoalP goal;
    PipsP pips;
    CheckersP checkers;
    CheckersInGoalP chkInGoal;
    ActivePlayerP activePlayer;

    public GUI() {
        window = new JFrame();
        window.setSize(1280, 720);
        window.setTitle("Backgammon");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        // Create base pane for layering panels
        JLayeredPane base = new JLayeredPane();
        base.setOpaque(false);

        diceTray = new DiceTrayP();
        diceFaces = new DiceFacesP(null);
        rollDice = new RollDiceP();
        board = new BoardP();
        bar = new BarP();
        goal = new GoalP();
        pips = new PipsP();
        checkers = new CheckersP();
        chkInGoal = new CheckersInGoalP();
        activePlayer = new ActivePlayerP();

        base.add(activePlayer, 0, -1);
        base.add(chkInGoal, 0, -1);
        base.add(checkers, 0, -1);
        base.add(goal, 0, -1);
        base.add(pips,0,-1);
        base.add(bar,0,-1);
        base.add(board,0,-1);
        base.add(rollDice, 0, -1);
        base.add(diceFaces, 0, -1);
        base.add(diceTray, 0, -1);

        window.add(base);
        window.setVisible(true);
    }

    private class BoardP extends JPanel {
        public BoardP() {
            setBounds( OUTER_LEFT_EDGE,  OUTER_TOP_EDGE,  BOARD_WIDTH,  BOARD_HEIGHT );
            setBackground(BOARD_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR,10));
        }
    }

    private class BarP extends JPanel {
        public BarP() {
            setBounds( BOARD_X_AXIS-(BAR_WIDTH/2),  OUTER_TOP_EDGE,  BAR_WIDTH,BOARD_HEIGHT );
            setBackground(BAR_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 10));
        }
    }

    private class GoalP extends JPanel {
        public GoalP() {
            setBounds(OUTER_RIGHT_EDGE + BOARD_DICE_DISTANCE / 2, OUTER_TOP_EDGE, CHECKER_DIAMETER + 30, BOARD_HEIGHT);
            setBackground(BOARD_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 10));
        }

    }
    private class CheckersInGoalP extends JPanel {

        int whiteCheckersAtStart = 15;
        int whiteCheckersLeft = 0;
        int redCheckersAtStart = 15;
        int redCheckersLeft = 0;
        int whiteCheckersInGoal = whiteCheckersAtStart - whiteCheckersLeft;
        int redCheckersInGoal = redCheckersAtStart - redCheckersLeft;

        public CheckersInGoalP() {
            setBounds(OUTER_RIGHT_EDGE + BOARD_DICE_DISTANCE / 2, OUTER_TOP_EDGE, CHECKER_DIAMETER + 30, BOARD_HEIGHT);
            setOpaque(false);
        }

        public void drawCheckersInGoal(Graphics2D g2) {
            g2.setColor(RED_CHECKER);
            int checkerThickness = 14;
            int checkerSpacing = 3;
            int initialPadding = 11;
            int xp = BEZEL+5;
            int yp = BEZEL+initialPadding;
            for (int i = 0; i < redCheckersInGoal; i++) {
                g2.fillRect(xp,yp, CHECKER_DIAMETER, checkerThickness);
                yp += checkerThickness + checkerSpacing;
            }
            g2.setColor(WHITE_CHECKER);
            yp = BOARD_HEIGHT - BEZEL - checkerThickness - initialPadding;
            for (int i = 0; i < whiteCheckersInGoal; i++) {
                g2.fillRect(xp,yp, CHECKER_DIAMETER, checkerThickness);
                yp -= checkerThickness + checkerSpacing;
            }
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            // Draw the goal divider
            g2.setColor(BORDER_COLOR);
            g2.setStroke(new BasicStroke(10));
            g2.drawLine(0, BOARD_Y_AXIS-OUTER_TOP_EDGE, CHECKER_DIAMETER+30, BOARD_Y_AXIS-OUTER_TOP_EDGE);
            // Draw checkers in goal
            drawCheckersInGoal(g2);
        }
    }

    private class CheckersP extends JPanel {

        ArrayList<Pip> pips;
        public CheckersP() {
            setBounds( INNER_LEFT_EDGE,  INNER_TOP_EDGE,  BOARD_WIDTH,  BOARD_HEIGHT );
            setOpaque(false);

            pips = new ArrayList<>(26);
            for (int i = 0; i < 26; i++) {
                pips.add(i, new Pip());
            }
            pips.get(1).setCheckers(2, Player.WHITE);
            pips.get(12).setCheckers(5, Player.WHITE);
            pips.get(17).setCheckers(3, Player.WHITE);
            pips.get(19).setCheckers(5, Player.WHITE);
            pips.get(6).setCheckers(5, Player.RED);
            pips.get(8).setCheckers(3, Player.RED);
            pips.get(13).setCheckers(5, Player.RED);
            pips.get(24).setCheckers(2, Player.RED);
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            // Paint the checkers in upper quadrants
            int index = 12;
            for (int x = 0; x < INNER_RIGHT_EDGE-INNER_LEFT_EDGE; x += PIP_WIDTH) {
                if (index == 6) {
                    x += BAR_WIDTH;
                }
                int checkersOnPip = pips.get(index).getCheckerCount();
                int checkersPlaced = 0;
                Color checkerColor = pips.get(index).getColor() == Player.WHITE ? WHITE_CHECKER : RED_CHECKER;
                Color checkerBorderColor = pips.get(index).getColor() == Player.WHITE ? WHITE_CHECKER_BORDER : RED_CHECKER_BORDER;
                for (int y = 0; checkersPlaced < checkersOnPip; y += PIP_WIDTH - 1) {
                    switch (checkersPlaced) {
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
                }
                index--;
            }

            // Paint the checkers in upper quadrants
            index = 13;
            for (int x = 0; x < INNER_RIGHT_EDGE-INNER_LEFT_EDGE; x += PIP_WIDTH) {
                if (index == 19) {
                    x += BAR_WIDTH;
                }
                int checkersOnPip = pips.get(index).getCheckerCount();
                int checkersPlaced = 0;
                Color checkerColor = pips.get(index).getColor() == Player.WHITE ? WHITE_CHECKER : RED_CHECKER;
                Color checkerBorderColor = pips.get(index).getColor() == Player.WHITE ? WHITE_CHECKER_BORDER : RED_CHECKER_BORDER;
                int startPos = BOARD_HEIGHT- 2*BEZEL-PIP_WIDTH;
                for (int y = startPos; checkersPlaced < checkersOnPip; y -= PIP_WIDTH - 1) {
                    switch (checkersPlaced) {
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
                }
                index++;
            }
        }
    }

    private class PipsP extends JPanel {

        private int[] xp;
        private int[] yp;

        public PipsP() {
            setBounds( INNER_LEFT_EDGE,  INNER_TOP_EDGE,  BOARD_WIDTH,  BOARD_HEIGHT );
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            xp = new int[]{0, 24, 48};
            yp = new int[]{0, 220, 0};

            for (int i = 0; i < 24; i++) {
                if (i==12) {
                    xp = new int[]{0, 24, 48};
                    yp = new int[]{560, 340, 560};
                }
                g2.setColor((i%2 == 0 && i < 12) || (i%2 == 1 && i >= 12) ? WHITE_PIP : RED_PIP);
                g2.fillPolygon(xp, yp, 3);
                xp[0] += PIP_WIDTH;
                xp[1] += PIP_WIDTH;
                xp[2] += PIP_WIDTH;

                if (i%12 == 5) {
                    xp[0] += BAR_WIDTH;
                    xp[1] += BAR_WIDTH;
                    xp[2] += BAR_WIDTH;
                }
            }
        }
    }

    private class DiceTrayP extends JPanel {
        public DiceTrayP() {
            setBounds( DICE_TRAY_LEFT,  DICE_TRAY_TOP,  DICE_TRAY_WIDTH,  DICE_TRAY_HEIGHT );
            setBackground(BOARD_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR,5));
            setOpaque(true);
        }
    }

    private class DiceFacesP extends JPanel {

        ArrayList<Integer> diceValues = new ArrayList<>(Arrays.asList(6,6,6,6));

        int spotSize = 8;
        int spotLeft = (DICE_TRAY_WIDTH - DICE_WIDTH)/2 + spotSize + 4;
        int D1SpotTop = spotLeft;
        int D2SpotTop = D1SpotTop + DICE_WIDTH + 10;

        public DiceFacesP(ArrayList<Integer> dv) {
            setBounds( DICE_TRAY_LEFT,  DICE_TRAY_TOP,  DICE_TRAY_WIDTH,  DICE_TRAY_HEIGHT );
            //diceValues = dv;
        }

        public void drawSpots(Graphics2D g2) {

            int spotTop = D1SpotTop;
            int tr = 2*spotSize;
            for (int numSpots : diceValues) {
                switch (numSpots) {
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
            drawSpots(g2);
        }
    }

    private class RollDiceP extends JPanel {
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
    }

    private class ActivePlayerP extends JPanel {

        JLabel textRoll;

        public ActivePlayerP() {
            setBounds( DICE_TRAY_LEFT,  DICE_TRAY_TOP - DICE_TRAY_WIDTH - 20,  DICE_TRAY_WIDTH,  DICE_TRAY_WIDTH );
            setBackground(ROLL_DICE_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR,5));
            setOpaque(true);

            textRoll = new JLabel("<html><br><h2>WHITE</h2></html>");
            textRoll.setSize(DICE_TRAY_WIDTH-20, DICE_TRAY_WIDTH-20);
            this.add(textRoll);
        }

        public void updateText() {
            if (textRoll.getText().equals("<html><br><h2>WHITE</h2></html>")) {
                textRoll.setText("<html><br><h2>RED</h2></html>");
            } else {
                textRoll.setText("<html><br><h2>WHITE</h2></html>");
            }
        }
    }
}
