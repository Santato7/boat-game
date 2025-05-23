package dev.santato.boatgame.entity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Trash extends Entity{
    private final BufferedImage image;
    private final Random rand = new Random();

    public Trash(int x, int y, int width, int height, int speed, JPanel panel, BufferedImage image) {
        super(x, y, width, height, speed, panel);

        this.image = image;
    }

    @Override
    public void update() {
        y += speed;

        if (rand.nextDouble() < 0.025) {
            int direction = rand.nextBoolean() ? 1 : -1;
            int offset = rand.nextInt(22) + 22;
            x += direction * offset;

            x = Math.max(0, Math.min(x, panel.getWidth() - width));
        }
    }

    @Override
    public void draw(Graphics g) { g.drawImage(image, x, y, width, height, null); }
}
