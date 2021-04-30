import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class TestGUI{

    public static void main(String[] args) {

        JFrame f = new JFrame("A JFrame"); // Title
        f.setSize(250, 250); // Size of window
        f.setLocation(300,300); // Position on screen
        final JTextArea textArea = new JTextArea(10, 40);
        f.getContentPane().add(BorderLayout.CENTER, textArea);
        final JButton button = new JButton("Click Me"); // Create button
        f.getContentPane().add(BorderLayout.SOUTH, button); // Position button
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //textArea.append("Button was clicked\n");
                //f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                //f.dispose();
            }
        });

        f.setVisible(true);
    }
}