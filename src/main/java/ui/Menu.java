package ui;

import org.example.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu {

    GamePanel gp;
    public int page = 0; 
    
    // Variables pour les images
    BufferedImage bonusLife, bonusSpeed, enemyNorm, enemyBoss;

    public Menu(GamePanel gp) {
        this.gp = gp;
        
        // Chargement des images pour les explications du menu
        try {
            bonusLife = ImageIO.read(getClass().getResourceAsStream(system.GameConfig.IMG_BONUS_LIFE));
            bonusSpeed = ImageIO.read(getClass().getResourceAsStream(system.GameConfig.IMG_BONUS_SPEED));
            enemyNorm = ImageIO.read(getClass().getResourceAsStream(system.GameConfig.IMG_ENEMY));
            enemyBoss = ImageIO.read(getClass().getResourceAsStream(system.GameConfig.IMG_ENEMY_ADVANCED));
        } catch(Exception e) { e.printStackTrace(); }
    }

    // Méthode outil pour centrer les titres
    private void drawTitle(Graphics2D g2, String text, int y) {
        g2.setFont(new Font("Verdana", Font.BOLD, 50));
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        
        g2.setColor(Color.black);
        g2.drawString(text, x + 3, y + 3); // Ombre
        g2.setColor(Color.cyan);
        g2.drawString(text, x, y); // Texte
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 220)); 
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setColor(Color.white);

        switch(page) {
            case 0: drawMain(g2); break;
            case 1: drawTouches(g2); break;
            case 2: drawRegles(g2); break;
            case 3: drawBoutique(g2); break;
            case 4: drawVolume(g2); break;
        }

        g2.setFont(new Font("Verdana", Font.ITALIC, 20));
        g2.setColor(Color.lightGray);
        if(page == 0) {
            if(gp.previousState == gp.titleState) {
                g2.drawString("Appuyez sur 'M' pour revenir au titre...", 50, gp.screenHeight - 50);
            } else {
                g2.drawString("Appuyez sur 'M' pour reprendre la partie...", 50, gp.screenHeight - 50);
            }
        } else {
            g2.drawString("Appuyez sur 'ECHAP' pour revenir au menu principal...", 50, gp.screenHeight - 50);
        }
    }

    private void drawMain(Graphics2D g2) {
        int x = gp.screenWidth / 2 - 150;
        int y = 120;
        drawTitle(g2, "MENU PRINCIPAL", y);

        g2.setFont(new Font("Verdana", Font.PLAIN, 30));
        g2.setColor(Color.white);
        y += 100; g2.drawString("1. Touches", x, y);
        y += 60;  g2.drawString("2. Règles et Bonus", x, y);
        y += 60;  g2.drawString("3. Boutique", x, y);
        y += 60;  g2.drawString("4. Volume", x, y);
        
        y += 100;
        g2.setFont(new Font("Verdana", Font.ITALIC, 22));
        g2.setColor(Color.yellow);
        g2.drawString("Utilisez les touches 1, 2, 3 ou 4 de votre clavier.", x - 50, y);
    }

    private void drawTouches(Graphics2D g2) {
        int x = 80, y = 120;
        drawTitle(g2, "TOUCHES", y);

        g2.setFont(new Font("Verdana", Font.PLAIN, 24));
        g2.setColor(Color.white);
        y += 100; g2.drawString("- Flèches Gauche/Droite : Déplacer le vaisseau", x, y);
        y += 50;  g2.drawString("- Touche ESPACE : Tirer", x, y);
        y += 50;  g2.drawString("- Touche M : Ouvrir/Fermer le Menu", x, y);
        y += 50;  g2.drawString("- Touche B : Raccourci vers la Boutique", x, y);
        y += 50;  g2.drawString("- Touche R : Recommencer quand Game Over", x, y);
    }

    private void drawRegles(Graphics2D g2) {
        int x = 80, y = 100;
        drawTitle(g2, "RÈGLES & BONUS", y);

        g2.setFont(new Font("Verdana", Font.BOLD, 26));
        g2.setColor(Color.yellow);
        y += 70; g2.drawString("Les Ennemis :", x, y);

        g2.setFont(new Font("Verdana", Font.PLAIN, 22));
        g2.setColor(Color.white);
        
        // Ennemi Normal
        y += 35;
        if (enemyNorm != null) g2.drawImage(enemyNorm, x, y - 25, 35, 35, null);
        g2.drawString(" Intercepteur : 1 Vie, Vitesse normale (" + system.GameConfig.SCORE_PER_ENEMY + " Pts)", x + 50, y);
        
        // Boss
        y += 55;
        if (enemyBoss != null) g2.drawImage(enemyBoss, x, y - 25, 35, 35, null);
        g2.drawString(" Vaisseau Lourd : " + system.GameConfig.ENEMY_ADVANCED_LIFE + " Vies, Plus rapide (x" + system.GameConfig.ENEMY_ADVANCED_SCORE_MULT + " Pts)", x + 50, y);

        g2.setFont(new Font("Verdana", Font.BOLD, 26));
        g2.setColor(Color.yellow);
        y += 70; g2.drawString("Les Bonus :", x, y);
        
        g2.setFont(new Font("Verdana", Font.PLAIN, 22));
        g2.setColor(Color.white);
        
        // Bonus Vie
        y += 35;
        if (bonusLife != null) g2.drawImage(bonusLife, x, y - 25, 30, 30, null);
        g2.drawString(" Module de survie : Restaure 1 point de vie", x + 40, y);
        
        // Bonus Tir
        y += 55;
        if (bonusSpeed != null) g2.drawImage(bonusSpeed, x, y - 25, 30, 30, null);
        g2.drawString(" Surchargeur Laser : Tir ultra-rapide temporaire", x + 40, y);
    }

    private void drawBoutique(Graphics2D g2) {
        int x = 80, y = 120;
        drawTitle(g2, "BOUTIQUE", y);

        g2.setColor(Color.yellow);
        g2.setFont(new Font("Verdana", Font.BOLD, 28));
        g2.drawString("Vos points : " + gp.scoreManager.score, gp.screenWidth - 300, 50);
        
        g2.setColor(Color.white);
        g2.setFont(new Font("Verdana", Font.PLAIN, 26));
        y += 100; g2.drawString("Appuyez sur la touche correspondante pour acheter :", x, y);
        
        y += 80; 
        g2.setColor(Color.green);
        g2.drawString("1. +1 Vie", x, y);
        g2.setColor(Color.lightGray);
        g2.drawString(" (Coût : "+system.GameConfig.PRICE_LIFE+" pts)", x + 130, y);
        
        y += 70;
        g2.setColor(Color.cyan);
        g2.drawString("2. Super Tir Rapide", x, y);
        g2.setColor(Color.lightGray);
        g2.drawString(" (Coût : "+system.GameConfig.PRICE_BOOST+" pts)", x + 280, y);
    }

    private void drawVolume(Graphics2D g2) {
        int x = gp.screenWidth / 2 - 200;
        int y = 120;
        drawTitle(g2, "VOLUME", y);

        g2.setColor(Color.white);
        g2.setFont(new Font("Verdana", Font.PLAIN, 26));
        y += 150; 
        g2.drawString("Volume de la musique et des tirs :", x, y);

        // --- BARRE DE VOLUME ---
        y += 60;
        // Le contour de la barre (250 pixels de large, pour 5 niveaux = 50px par niveau)
        g2.drawRect(x, y, 250, 30); 
        
        // Le remplissage
        int volumeWidth = 50 * gp.musicVolume; 
        g2.setColor(Color.cyan);
        g2.fillRect(x + 1, y + 1, volumeWidth, 28);

        // Textes indicatifs
        g2.setColor(Color.white);
        g2.setFont(new Font("Verdana", Font.ITALIC, 20));
        y += 80;
        g2.drawString("Utilisez les flèches <-- et --> pour ajuster.", x, y);
    }
}