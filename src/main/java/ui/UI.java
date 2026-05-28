package ui;

import org.example.GamePanel;

import java.awt.*;

public class UI {

    // Référence au jeu
    GamePanel gp;

    public UI(GamePanel gp) {
        this.gp = gp;
    }

    // Dessine les éléments UI
    public void draw(Graphics2D g2) {

        // Affiche le score et la vie (Uniquement en jeu ou en pause)
        if(gp.gameState == gp.playState || gp.gameState == gp.pauseState) {
            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Score: " + gp.scoreManager.score, 20, 30);
            g2.drawString("Vie: " + gp.player.life, 20, 50);
            g2.drawString("Niveau: " + gp.currentLevel, 20, 70);
        }

        // --- AFFICHAGE CINÉMATIQUE DE TRANSITION ---
        if(gp.gameState == gp.transitionState) {
            // On affiche le texte uniquement pendant l'étape 1 (quand le vaisseau est caché)
            if(gp.transitionStep == 1) {
                g2.setColor(Color.white);
                g2.setFont(new Font("Arial", Font.BOLD, 50));
                
                String titre = (gp.currentLevel == 1) ? "NIVEAU 1" : "BRAVO !";
                String sousTitre = (gp.currentLevel == 1) ? "Bonne chance !" : "Niveau 2";
                
                g2.drawString(titre, gp.screenWidth/2 - 120, gp.screenHeight/2 - 20);
                
                g2.setFont(new Font("Arial", Font.PLAIN, 30));
                g2.drawString(sousTitre, gp.screenWidth/2 - 100, gp.screenHeight/2 + 30);
            }
        }

        // --- AFFICHAGE GAME OVER ---
        if(gp.player.life <= 0 && gp.gameState == gp.playState) {
            g2.setColor(Color.red);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.drawString("GAME OVER", gp.screenWidth/2 - 150, gp.screenHeight/2);

            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.drawString("Appuyez sur R pour recommencer", gp.screenWidth/2 - 150, gp.screenHeight/2 + 40);
        }
        
        // --- AFFICHAGE VICTOIRE ---
        if(gp.gameState == gp.winState) {
            g2.setColor(Color.yellow);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.drawString("VICTOIRE !", gp.screenWidth/2 - 130, gp.screenHeight/2 - 20);

            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.drawString("Score Final : " + gp.scoreManager.score, gp.screenWidth/2 - 70, gp.screenHeight/2 + 30);
            g2.drawString("Appuyez sur R pour rejouer", gp.screenWidth/2 - 130, gp.screenHeight/2 + 70);
        }
    }
}