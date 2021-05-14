import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI {

    private static final int BOARD_HEIGHT = 580;
    private static final int BOARD_WIDTH = 664;
    private static final int BEZEL = 10;
    private static final int OUTER_LEFT_EDGE = 308;
    private static final int OUTER_TOP_EDGE = 50;
    private static final int INNER_LEFT_EDGE = OUTER_LEFT_EDGE + BEZEL;
    private static final int INNER_TOP_EDGE = OUTER_TOP_EDGE + BEZEL;
    private static final int BOARD_X_AXIS = 640;
    private static final int BOARD_Y_AXIS = 340;
    private static final int PIP_LENGTH = 220;
    private static final int PIP_WIDTH = 48;
    private static final int CHECKER_DIAMETER = 44;
    private static final int BAR_WIDTH = 68;
    private static final int BOARD_DICE_DISTANCE = 50;
    private static final int DICE_TRAY_WIDTH = 94;
    private static final int DICE_TRAY_HEIGHT = 168;
    private static final int DICE_WIDTH = 64;
    private static final int DICE_TRAY_LEFT = OUTER_LEFT_EDGE - BOARD_DICE_DISTANCE - DICE_TRAY_WIDTH;
    private static final int DICE_TRAY_TOP = BOARD_Y_AXIS - DICE_TRAY_HEIGHT/2;
    private static final int TITLE_BAR_HEIGHT = 30;
    private static final int OUTER_RIGHT_EDGE = OUTER_LEFT_EDGE + BOARD_WIDTH;
    private static final int OUTER_BOTTOM_EDGE = OUTER_TOP_EDGE + BOARD_HEIGHT;
    private static final int INNER_RIGHT_EDGE = OUTER_RIGHT_EDGE - BEZEL;
    private static final int INNER_BOTTOM_EDGE = OUTER_BOTTOM_EDGE - BEZEL;

    private static final java.awt.Color RED_PIP = new java.awt.Color(255, 44, 19);
    private static final java.awt.Color WHITE_PIP = new java.awt.Color(255, 221, 215);
    private static final java.awt.Color BOARD_COLOR = new java.awt.Color(213, 123, 77);
    private static final java.awt.Color BORDER_COLOR = new java.awt.Color(147, 64, 24);
    private static final java.awt.Color BAR_COLOR = new java.awt.Color(172, 76, 27);
    private static final java.awt.Color ROLL_DICE_COLOR = new java.awt.Color(241, 178, 70);
    private static final java.awt.Color RED_CHECKER = new java.awt.Color(134, 19, 0);
    private static final java.awt.Color RED_CHECKER_BORDER = new java.awt.Color(98, 4, 2);
    private static final java.awt.Color WHITE_CHECKER = new java.awt.Color(238, 225, 203);
    private static final java.awt.Color WHITE_CHECKER_BORDER = new java.awt.Color(196, 176, 159);


    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    public GUI() {
        JFrame window = new JFrame();
        window.setSize(1280, 720);
        window.setTitle("Backgammon");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        // Create base pane for layering panels
        JLayeredPane base = new JLayeredPane();
        base.setOpaque(false);

        DiceTrayP diceTray = new DiceTrayP();
        DiceFacesP diceFaces = new DiceFacesP(null);
        RollDiceP rollDice = new RollDiceP();
        BoardP board = new BoardP();
        BarP bar = new BarP();
        PipsP pips = new PipsP();
        CheckersP checkers = new CheckersP();

        base.add(checkers, 0, -1);
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

        private ArrayList<Integer> diceValues;

        public DiceFacesP(ArrayList<Integer> dv) {
            setBounds( DICE_TRAY_LEFT,  DICE_TRAY_TOP,  DICE_TRAY_WIDTH,  DICE_TRAY_HEIGHT );
            diceValues = dv;
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(Color.white);
            g2.fillRect(15, 15,  DICE_WIDTH,  DICE_WIDTH);
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(15, 15, DICE_WIDTH, DICE_WIDTH);
            g2.fillRect(25,25,10,10);


            g2.setColor(Color.white);
            g2.fillRect(15, 15+ DICE_WIDTH + 10,  DICE_WIDTH,  DICE_WIDTH);
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(15, 15+ DICE_WIDTH + 10, DICE_WIDTH, DICE_WIDTH);
        }
    }

    private class RollDiceP extends JPanel {
        public RollDiceP() {
            setBounds( DICE_TRAY_LEFT,  DICE_TRAY_TOP + DICE_TRAY_HEIGHT + 20,  DICE_TRAY_WIDTH,  DICE_TRAY_WIDTH );
            setBackground(ROLL_DICE_COLOR);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR,5));
            setOpaque(true);

            JLabel textRoll = new JLabel("<html><br><h1>ROLL</h1></html>");
            textRoll.setSize(DICE_TRAY_WIDTH-20, DICE_TRAY_WIDTH-20);
            JLabel textDice = new JLabel("<html><h1>DICE</h1><br><br></html>");
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
}
