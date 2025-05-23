package dev.santato.boatgame.entity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoostRefill extends PowerUp {
    private final BufferedImage image;

    public BoostRefill(int x, int y, int width, int height, int speed, JPanel panel, BufferedImage image) {
        super(x, y, width, height, speed, panel);

        this.image = image;
    }

    @Override
    public void update() { this.y += speed; }

    @Override
    public void draw(Graphics g) { g.drawImage(image, x, y, width, height, null); }

    @Override
    public void applyEffect(Boat boat) {
        boat.fillBoost();
    }
}
