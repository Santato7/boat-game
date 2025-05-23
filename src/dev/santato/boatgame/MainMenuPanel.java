package dev.santato.boatgame;

import dev.santato.boatgame.sound.SoundPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(CardLayout layout, JPanel parentPanel, GamePanel gamePanel) {
        setLayout(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/dev/santato/boatgame/resources/images/main-menu.jpg"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new GridBagLayout());

        JButton playButton = new JButton("Jogar");
        playButton.setFont(new Font("Arial", Font.BOLD, 48));
        playButton.setForeground(Color.WHITE);

        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        playButton.setOpaque(false);
        playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));



        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("/dev/santato/boatgame/resources/audio/confirm_compat.wav");
                layout.show(parentPanel, "game");
                gamePanel.requestFocusInWindow();
                gamePanel.startGame();
            }
        });

        backgroundLabel.add(playButton, new GridBagConstraints());

        // Adiciona o label de fundo ao painel principal
        add(backgroundLabel, BorderLayout.CENTER);
    }
}
