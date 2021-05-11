import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class BoardGUI {
    JFrame window;

    public static void main(String[] args) {
        new BoardGUI();
    }

    public BoardGUI() {
        // Create main frame
        window = new JFrame();

        drawDiceFaces();
        drawSkipTurnButton();
        drawRollDiceButton();
        drawGoals();
        drawBar();
        drawBoard();
        windowInit();
    }

    public void windowInit() {
        window.setTitle("Backgammon");
        window.getContentPane().setBackground(new java.awt.Color(190, 160, 140));
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setSize(1280, 720);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
    }

    public void drawBoard() {
        // Create board
        JPanel boardPNL = new JPanel();
        boardPNL.setBounds(200,50,880,420);
        boardPNL.setBorder(BorderFactory.createLineBorder(new java.awt.Color(85, 32, 4), 3));
        boardPNL.setBackground(new java.awt.Color(180, 100, 55));
        window.add(boardPNL);
    }

    public void drawBar() {
        JPanel bar = new JPanel();
        bar.setBounds(610,53,60,414);
        bar.setBackground(new java.awt.Color(116, 48, 16));
        window.add(bar);
    }

    public void drawGoals() {
        // Draw the red goal
        JPanel redGoal = new JPanel();
        redGoal.setBounds(1100,50,80,80);
        redGoal.setBorder(BorderFactory.createLineBorder(new java.awt.Color(109, 50, 26), 3));
        redGoal.setBackground(new java.awt.Color(217, 100, 60));

        // Draw the white goal
        JPanel whiteGoal = new JPanel();
        whiteGoal.setBounds(1100,390,80,80);
        whiteGoal.setBorder(BorderFactory.createLineBorder(new java.awt.Color(109, 50, 26), 3));
        whiteGoal.setBackground(new java.awt.Color(229, 201, 160));

        // Add the goals to the window
        window.add(redGoal);
        window.add(whiteGoal);
    }

    public void drawDiceFaces() {

    }

    public void drawSkipTurnButton() {

    }

    public void drawRollDiceButton() {

    }
}