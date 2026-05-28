package ui;

import org.example.GamePanel;
import java.awt.*;

public class Menu {

    GamePanel gp;
    
    // 0 = Principal, 1 = Touches, 2 = Règles, 3 = Boutique, 4 = Volume
    public int page = 0; 

    public Menu(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        
        // Fond semi-transparent commun à tous les menus
        g2.setColor(new Color(0, 0, 0, 220)); 
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(Color.white);

        // Affiche la bonne page selon la variable
        switch(page) {
            case 0: drawMain(g2); break;
            case 1: drawTouches(g2); break;
            case 2: drawRegles(g2); break;
            case 3: drawBoutique(g2); break;
            case 4: drawVolume(g2); break;
        }

        // Indication commune en bas de l'écran
        g2.setFont(new Font("Arial", Font.ITALIC, 18));
        g2.setColor(Color.lightGray);
        if(page == 0) {
            g2.drawString("Appuyez sur 'M' pour reprendre la partie...", 50, gp.screenHeight - 50);
        } else {
            g2.drawString("Appuyez sur 'ECHAP' pour revenir au menu principal...", 50, gp.screenHeight - 50);
        }
    }

    private void drawMain(Graphics2D g2) {
        int x = 50, y = 100;
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("MENU PRINCIPAL", x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        y += 80;
        g2.drawString("1. Touches", x, y);
        y += 50;
        g2.drawString("2. Règles et Bonus", x, y);
        y += 50;
        g2.drawString("3. Boutique", x, y);
        y += 50;
        g2.drawString("4. Volume", x, y);
        
        y += 80;
        g2.setFont(new Font("Arial", Font.ITALIC, 20));
        g2.setColor(Color.yellow);
        g2.drawString("Utilisez les touches 1, 2, 3 ou 4 de votre clavier.", x, y);
    }

    private void drawTouches(Graphics2D g2) {
        int x = 50, y = 100;
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("1. TOUCHES", x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        y += 80; g2.drawString("- Flèches Gauche/Droite : Déplacer le vaisseau", x, y);
        y += 40; g2.drawString("- Touche ESPACE : Tirer", x, y);
        y += 40; g2.drawString("- Touche M : Ouvrir/Fermer le Menu", x, y);
        y += 40; g2.drawString("- Touche B : Raccourci vers la Boutique", x, y);
        y += 40; g2.drawString("- Touche R : Recommencer quand Game Over", x, y);
    }

    private void drawRegles(Graphics2D g2) {
        int x = 50, y = 100;
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("2. RÈGLES & BONUS", x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        y += 60; g2.drawString("- Détruisez les ennemis rouges.", x, y);
        y += 30; g2.drawString("- Un ennemi tué = 10 Points (utiles pour la boutique).", x, y);
        
        y += 60; g2.drawString("Les Bonus :", x, y);
        y += 40;
        g2.setColor(Color.green); g2.fillRect(x, y - 15, 20, 20);
        g2.setColor(Color.white); g2.drawString(" : Restaure 1 point de vie (Max 3)", x + 30, y);
        y += 40;
        g2.setColor(Color.cyan); g2.fillRect(x, y - 15, 20, 20);
        g2.setColor(Color.white); g2.drawString(" : Tir rapide temporaire", x + 30, y);
    }

    private void drawBoutique(Graphics2D g2) {
        int x = 50, y = 100;
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("3. BOUTIQUE", x, y);

        // Affiche le score actuel du joueur
        g2.setColor(Color.yellow);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("Vos points : " + gp.scoreManager.score, gp.screenWidth - 250, 50);
        g2.setColor(Color.white);

        g2.setFont(new Font("Arial", Font.PLAIN, 22));
        y += 80; g2.drawString("Appuyez sur la touche correspondante pour acheter :", x, y);
        
        y += 60; 
        g2.setColor(Color.green);
        g2.drawString("1. +1 Vie", x, y);
        g2.setColor(Color.white);
        g2.drawString(" (Coût : 50 pts)", x + 100, y);
        
        y += 50;
        g2.setColor(Color.cyan);
        g2.drawString("2. Super Tir Rapide", x, y);
        g2.setColor(Color.white);
        g2.drawString(" (Coût : 100 pts)", x + 210, y);
    }

    private void drawVolume(Graphics2D g2) {
        int x = 50, y = 100;
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("4. VOLUME", x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        y += 80; g2.drawString("Fonctionnalité en cours de développement...", x, y);
    }
}