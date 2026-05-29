package entity;

import java.awt.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.example.GamePanel; 

public class Enemy extends Entity {

    // Liste de ses propres tirs
    public ArrayList<Projectile> projectiles = new ArrayList<>();
    
    // Cadence de tir
    public int shootCooldown = system.GameConfig.ENEMY_SHOOT_COOLDOWN; 

    private int moveCounter = 0;

    GamePanel gp;
    BufferedImage image;

    public int type;
    private int moveDelay;

    public Enemy(GamePanel gp, int x, int y, int type) {
        // On initialise avec les valeurs par défaut
        super(x, y, system.GameConfig.ENEMY_SIZE, system.GameConfig.ENEMY_SIZE, system.GameConfig.ENEMY_SPEED, 1);
        this.shootCooldown = system.GameConfig.ENEMY_SHOOT_COOLDOWN;
        this.gp = gp;
        this.type = type;

        // CONFIGURATION SELON LE TYPE AVEC LES CONSTANTES
        try {
            if (type == 1) {
                image = ImageIO.read(getClass().getResourceAsStream(system.GameConfig.IMG_ENEMY));
                this.life = 1;
                this.speed = system.GameConfig.ENEMY_SPEED;
                this.moveDelay = system.GameConfig.ENEMY_MOVE_DELAY;
            } else if (type == 2) {
                image = ImageIO.read(getClass().getResourceAsStream(system.GameConfig.IMG_ENEMY_ADVANCED));
                this.life = system.GameConfig.ENEMY_ADVANCED_LIFE;
                this.speed = system.GameConfig.ENEMY_ADVANCED_SPEED;
                this.moveDelay = system.GameConfig.ENEMY_ADVANCED_MOVE_DELAY;
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void update() {
        // Descente automatique
        moveCounter++;
        if(moveCounter >= system.GameConfig.ENEMY_MOVE_DELAY) {
            y += speed;
            moveCounter = 0; // On réinitialise le compteur
        }

        // Tir automatique vers le bas
        shootCooldown--;
        if(shootCooldown <= 0) {
            // direction = 1 (vers le bas)
            projectiles.add(new Projectile(x + (width / 2) - 2, y + height, system.GameConfig.ENEMY_PROJECTILE_SPEED, 1));
            
            gp.playShootSound();
            shootCooldown = system.GameConfig.ENEMY_SHOOT_COOLDOWN; // Réinitialise la cadence
        }

        // Mise à jour des tirs de l'ennemi
        for(int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        // Dessin de l'ennemi
        if (image != null) {
            g2.drawImage(image, x, y, width, height, null);
        } else {
            // Dessin de secours si l'image ne charge pas
            g2.setColor(Color.red);
            g2.fillRect(x, y, width, height);
        }

        // Dessin de ses tirs
        for(Projectile p : projectiles) {
            p.draw(g2);
        }
    }
}