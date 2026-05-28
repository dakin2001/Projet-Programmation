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

        // Affiche le score
        g2.setColor(Color.white);
        g2.drawString("Score: " + gp.scoreManager.score, 20, 30);

        // Affiche la vie
        g2.drawString("Vie: " + gp.player.life, 20, 50);

        // Affiche écran de fin
        if(gp.player.life <= 0) {
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            g2.drawString("GAME OVER", gp.screenWidth/2 - 120, gp.screenHeight/2);

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.drawString("Appuie sur R pour restart", gp.screenWidth/2 - 120, gp.screenHeight/2 + 40);
        }
    }
}