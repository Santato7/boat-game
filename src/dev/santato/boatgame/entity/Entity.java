package dev.santato.boatgame.entity;

import javax.swing.*;
import java.awt.*;

public abstract class Entity {
    protected int x, y;
    protected int width, height;
    protected int speed;
    protected JPanel panel;

    private boolean isAlive = true;

    public Entity(int x, int y, int width, int height, int speed, JPanel panel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.panel = panel;
    }

    public abstract void update();
    public abstract void draw(Graphics g);

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public boolean isAlive() { return isAlive; }
    public void kill() { isAlive = false; }
}
