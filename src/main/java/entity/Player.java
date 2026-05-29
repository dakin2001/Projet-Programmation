package entity;

import org.example.GamePanel;
import org.example.KeyHandler;

import java.awt.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public ArrayList<Projectile> projectiles = new ArrayList<>();
    GamePanel gp;
    KeyHandler keyH;

    BufferedImage image;
    
    // Variables pour la gestion des tirs et des bonus
    public int shootCooldown = 0; 
    public int boostTimer = 0; // Ajout du timer pour le bonus de cadence

    public Player(GamePanel gp, KeyHandler keyH) {
        // x, y, largeur(40), hauteur(40), vitesse(8), vie(3)
        super(gp.screenWidth / 2 - (system.GameConfig.PLAYER_SIZE / 2), gp.screenHeight - 100, system.GameConfig.PLAYER_SIZE, system.GameConfig.PLAYER_SIZE, system.GameConfig.PLAYER_SPEED, system.GameConfig.PLAYER_MAX_LIFE);
        this.gp = gp;
        this.keyH = keyH;
        // Chargement de l'image du joueur
        try {
            image = ImageIO.read(getClass().getResourceAsStream(system.GameConfig.IMG_PLAYER));
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }

    @Override
    public void update() {

        int currentBaseCooldown = system.GameConfig.PLAYER_NORMAL_COOLDOWN;

        if (life >= 3) {
            speed = system.GameConfig.PLAYER_SPEED; // Vitesse normale
        } 
        else if (life == 2) {
            speed = system.GameConfig.PLAYER_SPEED - 3; // Malus de vitesse (-1 vie)
        } 
        else if (life <= 1) {
            speed = system.GameConfig.PLAYER_SPEED - 5; // Gros malus de vitesse (-2 vies)
            currentBaseCooldown = system.GameConfig.PLAYER_NORMAL_COOLDOWN + 15; // Malus de cadence (tire plus lentement)
        }

        // Déplacement latéral
        if(keyH.leftPressed) x -= speed;
        if(keyH.rightPressed) x += speed;

        // Limites de l'écran
        if(x < 0) x = 0;
        if(x > gp.screenWidth - width) x = gp.screenWidth - width;

        // Baisse du cooldown de tir à chaque frame
        if(shootCooldown > 0) {
            shootCooldown--;
        }

        // Baisse du timer de bonus à chaque frame s'il est actif
        if(boostTimer > 0) {
            boostTimer--;
        }

        // Tir manuel
        if(keyH.spacePressed && shootCooldown == 0) {
            // Création du projectile vers le haut (-1)
            projectiles.add(new Projectile(x + (width / 2) - 2, y, system.GameConfig.PLAYER_PROJECTILE_SPEED, -1));
            
            gp.playShootSound();

            // Si le bonus est actif, la cadence est très rapide (5), sinon elle est normale (20)
            if(boostTimer > 0) {
                shootCooldown = system.GameConfig.PLAYER_BOOST_COOLDOWN;
            } else {
                shootCooldown = system.GameConfig.PLAYER_NORMAL_COOLDOWN;
            }
        }

        // Mise à jour de la position des tirs
        for(int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        // Dessin du vaisseau
        if (image != null) {
            g2.drawImage(image, x, y, width, height, null);
        } else {
            // Dessin de secours si l'image ne charge pas
            g2.setColor(Color.white);
            g2.fillRect(x, y, width, height);
        }

        // Dessin des tirs
        for(Projectile p : projectiles) {
            p.draw(g2);
        }
    }

    // Méthode pour ajouter de la vie (appelée par le CollisionManager)
    public void addLife(int value) {
        life += value;
        // On empêche le joueur d'avoir plus de 3 vies
        if(life > system.GameConfig.PLAYER_MAX_LIFE) {
            life = system.GameConfig.PLAYER_MAX_LIFE;
        }
    }
}