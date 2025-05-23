package dev.santato.boatgame;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("AquaMarine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1380, 820);

        CardLayout layout = new CardLayout();
        JPanel mainPanel = new JPanel(layout);

        GamePanel game = new GamePanel();
        MainMenuPanel menu = new MainMenuPanel(layout, mainPanel, game);

        mainPanel.add(menu, "menu");
        mainPanel.add(game, "game");

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
