package Main;

import javax.swing.*;

/**
 *
 */
public final class Game
{
    private Game() {}

    public static void main(String[] args) {
        JFrame window = new JFrame("Java game");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
}
