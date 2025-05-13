package dev.santato.boatgame;

import dev.santato.boatgame.entity.Boat;
import dev.santato.boatgame.entity.Entity;
import dev.santato.boatgame.entity.Trash;
import dev.santato.boatgame.sound.SoundPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private int score = 0;
    private boolean movingLeft, movingRight;
    private boolean usingBoost = false;
    private final Random rand = new Random();
    private Timer timer;
    private boolean gameStarted = false;
    private int lives = 3;
    private boolean gameOver = false;

    private ImageIcon backgroundGif;
    private Image heartImage;
    private BufferedImage imgBoatRight, imgBoatLeft;
    private BufferedImage[] trashImages;

    private final Boat boat;
    private final List<Entity> entities = new ArrayList<>();

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        try {
            backgroundGif = new ImageIcon(getClass().getResource("/dev/santato/boatgame/resources/images/agua.gif"));
            imgBoatRight = ImageIO.read(getClass().getResource("/dev/santato/boatgame/resources/images/barco_direita.png"));
            imgBoatLeft = ImageIO.read(getClass().getResource("/dev/santato/boatgame/resources/images/barco_esquerda.png"));
            heartImage = ImageIO.read(getClass().getResource("/dev/santato/boatgame/resources/images/heart.png"));
            heartImage = heartImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);

            trashImages = new BufferedImage[] {
                    ImageIO.read(getClass().getResource("/dev/santato/boatgame/resources/images/lixo1.png")),
                    ImageIO.read(getClass().getResource("/dev/santato/boatgame/resources/images/lata.png")),
                    ImageIO.read(getClass().getResource("/dev/santato/boatgame/resources/images/lata2.png")),
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        boat = new Boat(570, 570, 240, 180, 8, 12, this, imgBoatLeft, imgBoatRight);
        entities.add(boat);
    }

    public void startGame() {
        if (!gameStarted) {
            SoundPlayer.playBackgroundMusic("/dev/santato/boatgame/resources/audio/background_music.wav");
            timer = new Timer(16, this);
            timer.start();
            gameStarted = true;
        }
    }

    private void restartGame() {
        score = 0;
        lives = 3;
        gameOver = false;
        entities.clear();
        boat.setX(570);
        boat.setY(590);
        entities.add(boat);
        timer.restart();

        repaint();
    }

    private BufferedImage randomTrashImage() {
        return trashImages[rand.nextInt(trashImages.length)];
    }

    private void updateEntities() {
        for (Entity entity : entities) {
            entity.update();
        }
    }

    private void checkTrashCollisions() {
        for (Entity entity : entities) {
            if (entity instanceof Trash trash && trash.isAlive()) {
                if (trash.getBounds().intersects(boat.getBounds())) {
                    score++;
                    trash.kill();
                    SoundPlayer.playSound("/dev/santato/boatgame/resources/audio/score_compat.wav");
                } else if (trash.getY() > getHeight()) {
                    trash.kill();
                    SoundPlayer.playSound("/dev/santato/boatgame/resources/audio/damage_compat.wav");
                    lives--;

                    if (lives <= 0) {
                        gameOver = true;
                    }
                }
            }
        }
    }

    private void removeDeadEntities() {
        entities.removeIf(entity -> !entity.isAlive());
    }


    private void spawnTrash() {
        if (rand.nextInt(70) == 0) {
            int x = rand.nextInt(getWidth() - 170) + 50;
            int speed = 2 + rand.nextInt(5);
            entities.add(new Trash(x, 30, 50, 50, speed, this, randomTrashImage()));
        }
    }

    private void drawScore(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 300));
        FontMetrics fm = g.getFontMetrics();
        String text = Integer.toString(score);
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g.setColor(new Color(0, 0, 0, 80));
        g.drawString(text, x, y);
    }

    private void drawLives(Graphics g) {
        ImageIcon heartIcon = new ImageIcon(heartImage);
        int heartWidth = heartIcon.getIconWidth();
        int totalWidth = heartWidth * lives;
        int spacing = 5;
        int heartsX = (getWidth() - totalWidth - 2 * spacing) / 2;
        int heartY = 20;
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, heartsX + i * (heartWidth + spacing), heartY, 50, 50,this);
        }
    }

    private void handleGameOver(Graphics g) {
        SoundPlayer.stopBackgroundMusic();

        g.setFont(new Font("Arial", Font.BOLD, 64));
        g.setColor(Color.RED);
        g.drawString("GAME OVER", getWidth() / 2 - 190, getHeight() / 2 - 50);

        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.setColor(Color.BLACK);
        g.drawString("Pontuação: " + score, getWidth() / 2 - 150, getHeight() / 2 + 60);

        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.setColor(Color.BLACK);
        g.drawString("Pressione SPACE", getWidth() / 2 - 130, getHeight() / 2 + 120);

        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boat.setMovement(movingLeft, movingRight, usingBoost);

        updateEntities();
        checkTrashCollisions();
        removeDeadEntities();
        spawnTrash();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameOver) {
            handleGameOver(g);
            return;
        }

        g.drawImage(backgroundGif.getImage(), 0, 0, 1380, 820, this);

        for (Entity entity : entities) {
            entity.draw(g);
        }

        drawScore(g);
        drawLives(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
            restartGame();
        } else {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) movingLeft = true;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) movingRight = true;
            if (e.getKeyCode() == KeyEvent.VK_SPACE) usingBoost = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) movingLeft = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) movingRight = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) usingBoost = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
