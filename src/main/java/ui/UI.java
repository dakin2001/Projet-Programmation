package ui;

import org.example.GamePanel;
import system.GameConfig;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    BufferedImage lifeFull, lifeEmpty;
    BufferedImage bonusLifeImg, bonusSpeedImg, bonusShieldImg;

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            lifeFull = ImageIO.read(getClass().getResourceAsStream(GameConfig.IMG_HEART_FULL));
            lifeEmpty = ImageIO.read(getClass().getResourceAsStream(GameConfig.IMG_HEART_EMPTY));
            bonusLifeImg = ImageIO.read(getClass().getResourceAsStream(GameConfig.IMG_BONUS_LIFE));
            bonusSpeedImg = ImageIO.read(getClass().getResourceAsStream(GameConfig.IMG_BONUS_SPEED));
            bonusShieldImg = ImageIO.read(getClass().getResourceAsStream(GameConfig.IMG_BONUS_SHIELD));
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Méthode outil pour centrer le texte avec une ombre
    public void drawCenteredTextWithShadow(Graphics2D g2, String text, int y, Color color) {
        // Calcule la largeur du texte avec la police actuelle
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        
        // Dessine l'ombre (noir, décalé de 3 pixels)
        g2.setColor(Color.black);
        g2.drawString(text, x + 3, y + 3);
        
        // Dessine le texte principal
        g2.setColor(color);
        g2.drawString(text, x, y);
    }

    private void drawActiveBonus(Graphics2D g2, BufferedImage img, String text, int x, int y) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(x, y, 160, 50, 15, 15);
        g2.setColor(Color.gray);
        g2.drawRoundRect(x, y, 160, 50, 15, 15);
        
        if(img != null) g2.drawImage(img, x + 10, y + 10, 30, 30, null);
        
        g2.setColor(Color.white);
        g2.setFont(new Font("Verdana", Font.BOLD, 18));
        g2.drawString(text, x + 50, y + 32);
    }

    public void draw(Graphics2D g2) {

        // Affiche le score et le niveau
        if(gp.gameState == gp.playState || gp.gameState == gp.pauseState) {
            g2.setColor(Color.white);
            g2.setFont(new Font("Verdana", Font.BOLD, 20)); // Utilisation d'une police plus jolie
            g2.drawString("Score: " + gp.scoreManager.score, 20, 30);
            g2.drawString("Niveau: " + gp.currentLevel, 20, 60);
            
            String timeText = gp.scoreManager.getFormattedTime();
            g2.setFont(new Font("Verdana", Font.BOLD, 24));
            int textLength = (int)g2.getFontMetrics().getStringBounds(timeText, g2).getWidth();
            int xTimer = gp.screenWidth / 2 - textLength / 2;
            int yTimer = 40;
            
            // Fond du timer (rectangle arrondi semi-transparent)
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRoundRect(xTimer - 15, yTimer - 25, textLength + 30, 35, 15, 15);
            // Bordure du timer
            g2.setColor(Color.cyan);
            g2.drawRoundRect(xTimer - 15, yTimer - 25, textLength + 30, 35, 15, 15);
            
            // Texte du timer
            g2.setColor(Color.white);
            g2.drawString(timeText, xTimer, yTimer);
            // ------------------------------------

            int size = 40; 
            int xStart = gp.screenWidth - (size * 3) - 20; 
            int y = gp.screenHeight - size - 20;

            for (int i = 0; i < GameConfig.PLAYER_MAX_LIFE; i++) {
                BufferedImage img = (i < gp.player.life) ? lifeFull : lifeEmpty;
                if (img != null) {
                    g2.drawImage(img, xStart + (i * size), y, size, size, null);
                }
            }

            int bonusY = gp.screenHeight - 70; // On commence en bas
            int bonusX = 20;

            // Affiche le message de Vie (Priorité basse)
            if (gp.player.lifeDisplayTimer > 0) {
                drawActiveBonus(g2, bonusLifeImg, "+1 Vie !", bonusX, bonusY);
                bonusY -= 60; // On remonte pour le prochain
            }
            // Affiche le Bouclier
            if (gp.player.shieldHits > 0) {
                drawActiveBonus(g2, bonusShieldImg, gp.player.shieldHits + " Coups", bonusX, bonusY);
                bonusY -= 60;
            }
            // Affiche le Tir Rapide
            if (gp.player.boostTimer > 0) {
                int seconds = gp.player.boostTimer / GameConfig.FPS;
                drawActiveBonus(g2, bonusSpeedImg, seconds + "s restants", bonusX, bonusY);
            }
        }

        // --- AFFICHAGE CINÉMATIQUE ---
        if(gp.gameState == gp.transitionState) {
            if(gp.transitionStep == 1) {
                String titre = (gp.currentLevel == 1) ? "NIVEAU 1" : "BRAVO !";
                String sousTitre = (gp.currentLevel == 1) ? "Bonne chance Commandant !" : "Niveau 2";
                
                g2.setFont(new Font("Verdana", Font.BOLD, 70));
                drawCenteredTextWithShadow(g2, titre, gp.screenHeight/2 - 20, Color.cyan);
                
                g2.setFont(new Font("Verdana", Font.PLAIN, 35));
                drawCenteredTextWithShadow(g2, sousTitre, gp.screenHeight/2 + 40, Color.white);
            }
        }

        // --- AFFICHAGE GAME OVER ---
        if(gp.player.life <= 0 && gp.gameState == gp.playState) {
            g2.setFont(new Font("Verdana", Font.BOLD, 80));
            drawCenteredTextWithShadow(g2, "GAME OVER", gp.screenHeight/2 - 20, Color.red);

            g2.setFont(new Font("Verdana", Font.PLAIN, 25));
            drawCenteredTextWithShadow(g2, "Appuyez sur R pour l'écran titre", gp.screenHeight/2 + 40, Color.white);
        }
        
        // --- AFFICHAGE VICTOIRE ---
        if(gp.gameState == gp.winState) {
            g2.setFont(new Font("Verdana", Font.BOLD, 80));
            drawCenteredTextWithShadow(g2, "VICTOIRE !", gp.screenHeight/2 - 30, Color.yellow);

            g2.setFont(new Font("Verdana", Font.BOLD, 30));
            drawCenteredTextWithShadow(g2, "Score Final : " + gp.scoreManager.score, gp.screenHeight/2 + 30, Color.white);
            
            g2.setFont(new Font("Verdana", Font.PLAIN, 25));
            drawCenteredTextWithShadow(g2, "Appuyez sur R pour l'écran titre", gp.screenHeight/2 + 80, Color.lightGray);
        }

        // --- AFFICHAGE ÉCRAN TITRE ---
        if(gp.gameState == gp.titleState) {
            g2.setFont(new Font("Verdana", Font.BOLD, 100));
            drawCenteredTextWithShadow(g2, "NO GRAVITY", gp.screenHeight/2 - 100, Color.cyan);

            g2.setFont(new Font("Verdana", Font.BOLD, 30));
            drawCenteredTextWithShadow(g2, "Appuyez sur ESPACE pour Jouer", gp.screenHeight/2 + 50, Color.white);

            g2.setFont(new Font("Verdana", Font.PLAIN, 20));
            drawCenteredTextWithShadow(g2, "Appuyez sur M pour le Menu (Règles, Boutique...)", gp.screenHeight/2 + 100, Color.lightGray);

            // TOP 3 MEILLEURS TEMPS
            int yTop = gp.screenHeight/2 + 180; 
            g2.setFont(new Font("Verdana", Font.BOLD, 25));
            drawCenteredTextWithShadow(g2, "- TOP 3 MEILLEURS TEMPS -", yTop, Color.yellow);
            
            g2.setFont(new Font("Verdana", Font.PLAIN, 22));
            if(gp.scoreManager.topTimes.isEmpty()) {
                drawCenteredTextWithShadow(g2, "Aucun record pour le moment", yTop + 40, Color.lightGray);
            } else {
                for(int i = 0; i < gp.scoreManager.topTimes.size(); i++) {
                    // On récupère le temps en ticks et on le convertit en format MM:SS
                    String timeString = gp.scoreManager.getFormattedTime(gp.scoreManager.topTimes.get(i));
                    String scoreText = (i+1) + ". " + timeString;
                    drawCenteredTextWithShadow(g2, scoreText, yTop + 40 + (i * 35), Color.white);
                }
            }
        }
    }
}