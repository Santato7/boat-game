package dev.santato.boatgame.entity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

enum Direction {
    LEFT, RIGHT
}

public class Boat extends Entity {
    private int lives;
    private final int maxLives = 5;
    private final int maxBoost = 150;
    private int currentBoost = 150;
    private final int boostRechargeRate = 1;
    private final int boostRechargeDelay = 2000;
    private long lastBoostUseTime = 0;

    private boolean usingBoost;
    private int boostSpeed;
    private boolean movingLeft = false, movingRight = false;
    private Direction lastDirection = Direction.RIGHT;

    private final BufferedImage imgLeft, imgRight;

    public Boat(int startX, int startY, int width, int height, int speed, JPanel panel, int lives, int boostSpeed, BufferedImage imgLeft, BufferedImage imgRight) {
        super(startX, startY, width, height, speed, panel);

        this.lives = lives;
        this.boostSpeed = boostSpeed;
        this.imgLeft = imgLeft;
        this.imgRight = imgRight;
    }

    public void setMovement(boolean left, boolean right, boolean usingBoost) {
        this.movingLeft = left;
        this.movingRight = right;
        this.usingBoost = usingBoost;
    }

    public void drawBoostBar(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(150, 150, 150));
        g.fillRect(x, y, width, height);

        int currentBoostWidth = (int) ((double) currentBoost / maxBoost * width);
        g.setColor(new Color(0, 255, 0));
        g.fillRect(x, y, currentBoostWidth, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    public void fillBoost() {
        this.currentBoost = this.maxBoost;
    }

    public int getLives(){
        return this.lives;
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    @Override
    public void update() {
        int currentSpeed = usingBoost && currentBoost > 0 ? boostSpeed : speed;

        if (movingLeft && x > 0) {
            x -= currentSpeed;
            lastDirection = Direction.LEFT;
        }
        if (movingRight && x + width < panel.getWidth()) {
            x += currentSpeed;
            lastDirection = Direction.RIGHT;
        }

        long currentTime = System.currentTimeMillis();
        if (currentBoost < maxBoost && currentTime - lastBoostUseTime > boostRechargeDelay) {
            currentBoost += boostRechargeRate;
            if (currentBoost > maxBoost) currentBoost = maxBoost;
        }
        if (usingBoost && currentBoost > 0) {
            currentBoost -= 1;
            lastBoostUseTime = currentTime;
        }
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage img = (lastDirection == Direction.LEFT) ? imgLeft : imgRight;
        g.drawImage(img, x, y, width, height, null);

        drawBoostBar(g, x, y + 180, 230, 20);
    }
}