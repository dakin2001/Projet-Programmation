package entity;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Entity {

    // Liste de ses propres tirs
    public ArrayList<Projectile> projectiles = new ArrayList<>();
    
    // Cadence de tir
    public int shootCooldown = 160; 

    private int moveCounter = 0;

    public Enemy(int x, int y) {
        // x, y, largeur(40), hauteur(40), vitesse(1), vie(1)
        super(x, y, 40, 40, 1, 1);
    }

    @Override
    public void update() {
        // Descente automatique
        moveCounter++;
        if(moveCounter >= 2) {
            y += speed;
            moveCounter = 0; // On réinitialise le compteur
        }

        // Tir automatique vers le bas
        shootCooldown--;
        if(shootCooldown <= 0) {
            // direction = 1 (vers le bas)
            projectiles.add(new Projectile(x + (width / 2) - 2, y + height, 4, 1));
            shootCooldown = 160; // Réinitialise la cadence
        }

        // Mise à jour des tirs de l'ennemi
        for(int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        // Dessin de l'ennemi
        g2.setColor(Color.red);
        g2.fillRect(x, y, width, height);

        // Dessin de ses tirs
        for(Projectile p : projectiles) {
            p.draw(g2);
        }
    }
}