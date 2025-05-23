package dev.santato.boatgame.entity;

import javax.swing.*;

public abstract class PowerUp extends Entity {
    public PowerUp(int x, int y, int width, int height, int speed, JPanel panel) {
        super(x, y, width, height, speed, panel);
    }

    public abstract void applyEffect(Boat boat);
}
