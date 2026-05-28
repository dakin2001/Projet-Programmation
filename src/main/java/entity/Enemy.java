package entity;

import java.awt.*;

public class Enemy {

    // Position de l'ennemi
    public int x, y;

    // Vitesse de descente
    int speed = 2;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Fait descendre l'ennemi
    public void update() {
        y += speed;
    }

    // Dessine l'ennemi
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fillRect(x, y, 40, 40);
    }

    // Hitbox de l'ennemi
    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }
}