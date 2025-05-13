package dev.santato.boatgame;

import dev.santato.boatgame.sound.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(CardLayout layout, JPanel parentPanel, GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        setBackground(new Color(50, 150, 200));

        JButton playButton = new JButton("Jogar");
        playButton.setFont(new Font("Arial", Font.BOLD, 24));

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("/dev/santato/boatgame/resources/audio/confirm_compat.wav");
                layout.show(parentPanel, "game");
                gamePanel.requestFocusInWindow();
                gamePanel.startGame();
            }
        });

        add(playButton);
    }
}
