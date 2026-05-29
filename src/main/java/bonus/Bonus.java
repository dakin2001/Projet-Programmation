package bonus;

import javax.imageio.ImageIO;

import system.GameConfig;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bonus {

    // Position du bonus
    public int x, y;

    // Vitesse de descente
    int speed = system.GameConfig.BONUS_SPEED;

    // Type de bonus (0 = vie, 1 = tir rapide)
    public int type;
    
    BufferedImage image;

    public Bonus(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;

        try {
            String path = (type == 0) ? GameConfig.IMG_BONUS_LIFE : GameConfig.IMG_BONUS_SPEED;
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Fait descendre le bonus
    public void update() {
        y += speed;
    }

    // Dessine le bonus selon son type
    public void draw(Graphics2D g2) {

        if (image != null) {
            g2.drawImage(image, x, y, GameConfig.BONUS_SIZE, GameConfig.BONUS_SIZE, null);
        }
    }

    // Hitbox du bonus
    public Rectangle getBounds() {
        return new Rectangle(x, y, system.GameConfig.BONUS_SIZE, system.GameConfig.BONUS_SIZE);
    }
}