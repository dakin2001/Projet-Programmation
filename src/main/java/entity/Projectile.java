package entity;

import java.awt.*;
import javax.imageio.ImageIO;

import system.GameConfig;

import java.awt.image.BufferedImage;

public class Projectile extends Entity {

    // Direction : -1 pour aller vers le haut, 1 pour aller vers le bas
    public int direction; 
    BufferedImage image;

    public Projectile(int x, int y, int speed, int direction) {
        // x, y, largeur(5), hauteur(10), vitesse(10), vie(1 - bien qu'inutile ici, on respecte le parent)
        super(x, y, system.GameConfig.PROJECTILE_WIDTH, system.GameConfig.PROJECTILE_HEIGHT, speed, 1);
        this.direction = direction;

        try {
            String path = (direction == -1) ? GameConfig.IMG_PROJECTILE_PLAYER : GameConfig.IMG_PROJECTILE_ENEMY;
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (Exception e) { e.printStackTrace(); }
    }
    

    @Override
    public void update() {
        // La vitesse est multipliée par la direction (-1 ou 1)
        y += speed * direction;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, x, y, width, height, null);
        }
    }
}