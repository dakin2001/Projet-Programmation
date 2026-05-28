package bonus;

import java.awt.*;

public class Bonus {

    // Position du bonus
    public int x, y;

    // Vitesse de descente
    int speed = system.GameConfig.BONUS_SPEED;

    // Type de bonus (0 = vie, 1 = tir rapide)
    public int type;

    public Bonus(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    // Fait descendre le bonus
    public void update() {
        y += speed;
    }

    // Dessine le bonus selon son type
    public void draw(Graphics2D g2) {

        if(type == 0) {
            g2.setColor(Color.green); // Bonus vie
        } else {
            g2.setColor(Color.cyan); // Bonus tir rapide
        }

        g2.fillRect(x, y, system.GameConfig.BONUS_SIZE, system.GameConfig.BONUS_SIZE);
    }

    // Hitbox du bonus
    public Rectangle getBounds() {
        return new Rectangle(x, y, system.GameConfig.BONUS_SIZE, system.GameConfig.BONUS_SIZE);
    }
}