package entity;

import java.awt.*;

public class Projectile extends Entity {

    // Direction : -1 pour aller vers le haut, 1 pour aller vers le bas
    public int direction; 

    public Projectile(int x, int y, int speed, int direction) {
        // x, y, largeur(5), hauteur(10), vitesse(10), vie(1 - bien qu'inutile ici, on respecte le parent)
        super(x, y, system.GameConfig.PROJECTILE_WIDTH, system.GameConfig.PROJECTILE_HEIGHT, speed, 1);
        this.direction = direction;
    }

    @Override
    public void update() {
        // La vitesse est multipliée par la direction (-1 ou 1)
        y += speed * direction;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.yellow);
        g2.fillRect(x, y, width, height); 
    }
}