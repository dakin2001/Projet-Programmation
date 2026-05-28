package entity;

import java.awt.*;

public class Projectile {

    // Position du tir
    public int x, y;

    // Vitesse du tir
    int speed = 10;

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Fait monter le tir
    public void update() {
        y -= speed;
    }

    // Dessine le projectile
    public void draw(Graphics2D g2) {
        g2.setColor(Color.yellow);
        g2.fillRect(x, y, 5, 10);
    }

    // Hitbox du tir
    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 10);
    }
}