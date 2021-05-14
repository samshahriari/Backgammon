import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI {

    private final int BOARD_HEIGHT = 580;
    private final int BOARD_WIDTH = 664;
    private final int BEZEL = 10;
    private final int OUTER_LEFT_EDGE = 308;
    private final int OUTER_TOP_EDGE = 50;
    private final int INNER_LEFT_EDGE = OUTER_LEFT_EDGE + BEZEL;
    private final int INNER_TOP_EDGE = OUTER_TOP_EDGE + BEZEL;
    private final int BOARD_X_AXIS = 640;
    private final int BOARD_Y_AXIS = 340;
    private final int PIP_LENGTH = 220;
    private final int PIP_WIDTH = 48;
    private final int CHECKER_DIAMETER = 44;
    private final int BAR_WIDTH = 68;

    private final java.awt.Color RED_PIP = new java.awt.Color(255, 44, 19);
    private final java.awt.Color WHITE_PIP = new java.awt.Color(255, 221, 215);
    private final java.awt.Color BOARD_COLOR = new java.awt.Color(213, 123, 77);
    private final java.awt.Color BORDER_COLOR = new java.awt.Color(147, 64, 24);
    private final java.awt.Color BAR_COLOR = new java.awt.Color(172, 76, 27);


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

        BoardP board = new BoardP();
        BarP bar = new BarP();
        PipsP pips = new PipsP();

        base.add(pips,0,-1);
        base.add(bar,0,-1);
        base.add(board,0,-1);

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
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR,10));
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

            xp = new int[]{0,24,48};
            yp = new int[]{0,220,0};

            for (int i = 0; i < 24; i++) {
                if (i==12) {
                    xp = new int[]{0,24,48};
                    yp = new int[]{560,340,560};
                }
                g2.setColor((i%2 == 0 && i < 12) || (i%2 == 1 && i >= 12) ? WHITE_PIP : RED_PIP);
                g2.fillPolygon(xp,yp,3);
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
}

//            g2.setColor(new java.awt.Color(134, 19, 0));
//            g2.fillOval(2,0,44,44);
//            g2.fillOval(2,44,44,44);
//            g2.fillOval(2,88,44,44);
//            g2.fillOval(2,132,44,44);
//            g2.fillOval(2,176,44,44);