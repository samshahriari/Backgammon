import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI {
    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    public GUI() {
        JFrame window = new JFrame();
        window.setSize(1280, 720);
        window.setTitle("Backgammon");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        // Create base pane for layering panels
        JLayeredPane base = new JLayeredPane();
        base.setOpaque(false);

        BoardP board = new BoardP();
        BarP bar = new BarP();
        PipP pip = new PipP(1,2,true);

        base.add(pip,0,-1);
        base.add(bar,0,-1);
        base.add(board,0,-1);

        window.add(base);
        window.setVisible(true);
    }

    private class BoardP extends JPanel {
        public BoardP() {
            setBounds(200,50,880,420);
            setBackground(new java.awt.Color(213, 123, 77));
            setBorder(BorderFactory.createLineBorder(new java.awt.Color(147, 64, 24),10));
        }
    }

    private class BarP extends JPanel {
        public BarP() {
            setBounds(600,50,80,420);
            setBackground(new java.awt.Color(172, 76, 27));
            setBorder(BorderFactory.createLineBorder(new java.awt.Color(147, 64, 24),10));
        }
    }

    private class PipP extends JPanel {

        private int[] xp;
        private int[] yp;
        private int[] xpF;
        private int[] ypF;
        private boolean top;
        private final int WIDTH = 1;

        public PipP(int x, int y, boolean t) {
            setBounds(200,50,880,420);
            xp = new int[]{10,40,70};
            yp = new int[]{10,190,10};
            xpF = new int[]{10,40,70};
            ypF = new int[]{410,230,410};
            top = t;
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(new java.awt.Color(255, 44, 19));
            g2.fillPolygon(xp,yp,3);

            g2.setColor(new java.awt.Color(134, 19, 0));
            g2.fillOval(20,10,40,40);

            g2.setColor(new java.awt.Color(255, 215, 197));
            g2.fillPolygon(xpF,ypF,3);
        }
    }
}
