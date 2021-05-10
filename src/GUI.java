import javax.swing.*;
import java.awt.*;

public class GUI {

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create main frame
                Frame mainframe = new Frame();

                // Create panels
                BoardPanel boardPNL = new BoardPanel();
                JButton rollDiceBTN = new JButton();
                JButton skipTurnBTN = new JButton();
                JPanel diceFacesPNL = new JPanel();
                JPanel checkersPNL = new JPanel();

                // Add panels to main frame
                mainframe.add(boardPNL);
                mainframe.add(boardPNL.homeRed);
                mainframe.add(boardPNL.outerRed);
                mainframe.add(boardPNL.outerWhite);
                mainframe.add(boardPNL.whiteHome);

            }
        });

        // open window
        // draw board
        // draw roll dice button
        // draw skip turn button
        // draw dice faces
        // draw pips
        // draw checkers
    }



    private class Frame extends JFrame {
        public Frame() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1280,720);
            setLocationRelativeTo(null);
            setResizable(false);
            setTitle("Backgammon");
            getContentPane().setBackground(new java.awt.Color(190, 160, 140));
            setVisible(true);
        }
    }



    private class BoardPanel extends JPanel {
        QuadrantPanel homeRed, outerRed, outerWhite, whiteHome;

        public BoardPanel() {
            super(new GridLayout(2,2));

            homeRed = new QuadrantPanel(java.awt.Color.BLACK);
            outerRed = new QuadrantPanel(java.awt.Color.DARK_GRAY);
            outerWhite = new QuadrantPanel(java.awt.Color.RED);
            whiteHome = new QuadrantPanel(java.awt.Color.GREEN);

            add(homeRed);
            add(outerRed);
            add(outerWhite);
            add(whiteHome);

            setOpaque(false);
            validate();
        }
    }



    private class QuadrantPanel extends JPanel {
        public QuadrantPanel(java.awt.Color color) {
            setBackground(color);
            setSize(200,200);
            setOpaque(false);
            validate();
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.fillOval(400,400,100,100);
        }
    }
}
