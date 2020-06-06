package game;

import javax.swing.*;

public class FlappyBird {

    public static FlappyBird flappyBird;

    public final int WIDTH = 800, HEIGHT = 800;

    public FlappyBird() {
        JFrame jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setResizable(false);
        jframe.setVisible(true);

    }

    public static void main(String[] args) {

    }

}
