package entity;


import org.example.GamePanel;
import org.example.KeyHandler;

import java.awt.*;
import java.util.ArrayList;

public class Player {

    // Liste des tirs
    public ArrayList<Projectile> projectiles = new ArrayList<>();

    // Références
    GamePanel gp;
    KeyHandler keyH;

    // Vie du joueur
    public int life = 3;

    // Position
    public int x, y;

    // Vitesse déplacement
    int speed = 5;

    // Gestion tir
    public int shootCooldown = 20;

    // Timer bonus tir
    public int boostTimer = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // Position de départ
        x = gp.screenWidth / 2 - 20;
        y = gp.screenHeight - 100;
    }

    public void update() {

        // Déplacement horizontal
        if(keyH.leftPressed) x -= speed;
        if(keyH.rightPressed) x += speed;

        // Limites écran
        if(x < 0) x = 0;
        if(x > gp.screenWidth - 40) x = gp.screenWidth - 40;

        // Tir automatique
        shootCooldown--;
        if(shootCooldown <= 0) {
            projectiles.add(new Projectile(x + 18, y));
            shootCooldown = 20;
        }

        // Update des tirs
        for(int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }

        // Gestion bonus tir rapide
        if(boostTimer > 0) {
            boostTimer--;

            if(boostTimer == 0) {
                shootCooldown = 20; // Retour normal
            }
        }
    }

    public void draw(Graphics2D g2) {

        // Dessine joueur
        g2.setColor(Color.white);
        g2.fillRect(x, y, 40, 40);

        // Dessine tirs
        for(Projectile p : projectiles) {
            p.draw(g2);
        }
    }

    // Hitbox joueur
    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }

    // Ajoute de la vie avec limite
    public void addLife(int value) {
        life += value;
        if(life > 3) life = 3;
    }
}