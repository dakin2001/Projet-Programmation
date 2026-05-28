package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Entity {

    // Caractéristiques communes
    public int x, y;
    public int width, height;
    public int speed;
    public int life;

    // Constructeur de la classe mère
    public Entity(int x, int y, int width, int height, int speed, int life) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.life = life;
    }

    // Méthodes que les enfants devront coder (se déplacer et se dessiner)
    public abstract void update();
    public abstract void draw(Graphics2D g2);

    // Hitbox commune à toutes les entités (plus besoin de la recréer dans les classes filles !)
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}