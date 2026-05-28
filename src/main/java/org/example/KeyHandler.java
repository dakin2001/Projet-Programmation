package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean leftPressed, rightPressed, spacePressed, restartPressed;
    GamePanel gp;public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(gp.gameState == gp.playState) {
            if(code == KeyEvent.VK_LEFT) leftPressed = true;
            if(code == KeyEvent.VK_RIGHT) rightPressed = true;
            if(code == KeyEvent.VK_SPACE) spacePressed = true;
            if(code == KeyEvent.VK_R) restartPressed = true;
            
            
            // Ouvrir le menu normal
            if(code == KeyEvent.VK_M) {
                gp.gameState = gp.pauseState;
                gp.menu.page = 0; // Ouvre sur la table des matières
            }
            
            // Raccourci : Ouvrir directement la boutique avec B
            if(code == KeyEvent.VK_B) {
                gp.gameState = gp.pauseState;
                gp.menu.page = 3; // Ouvre directement la page boutique
            }
        }
        else if(gp.gameState == gp.pauseState) {
            
            // Fermer le menu avec M
            if(code == KeyEvent.VK_M) {
                gp.gameState = gp.playState; 
            }
            
            // Revenir au menu principal avec Echap
            if(code == KeyEvent.VK_ESCAPE) {
                gp.menu.page = 0;
            }

            // Navigation dans le Menu Principal (page 0)
            if(gp.menu.page == 0) {
                // Fonctionne avec le pavé numérique ET les touches du haut
                if(code == KeyEvent.VK_1 || code == KeyEvent.VK_NUMPAD1) gp.menu.page = 1;
                if(code == KeyEvent.VK_2 || code == KeyEvent.VK_NUMPAD2) gp.menu.page = 2;
                if(code == KeyEvent.VK_3 || code == KeyEvent.VK_NUMPAD3) gp.menu.page = 3;
                if(code == KeyEvent.VK_4 || code == KeyEvent.VK_NUMPAD4) gp.menu.page = 4;
            }
            
            // Achats dans la Boutique (page 3)
            else if(gp.menu.page == 3) {
                
                // Achat 1 : La Vie (Coût 50)
                if(code == KeyEvent.VK_1 || code == KeyEvent.VK_NUMPAD1) {
                    if(gp.scoreManager.score >= system.GameConfig.PRICE_LIFE && gp.player.life > 0 && gp.player.life < system.GameConfig.PLAYER_MAX_LIFE) {
                        gp.scoreManager.score -= system.GameConfig.PRICE_LIFE;
                        gp.player.addLife(1);
                    }
                }
                
                // Achat 2 : Super Tir Rapide (Coût 100)
                if(code == KeyEvent.VK_2 || code == KeyEvent.VK_NUMPAD2) {
                    if(gp.scoreManager.score >= system.GameConfig.PRICE_BOOST && gp.player.life > 0) {
                        gp.scoreManager.score -= system.GameConfig.PRICE_BOOST;  // Déduit les points
                        gp.player.boostTimer += system.GameConfig.BOOST_DURATION_SHOP;   // Gros bonus de temps
                        gp.player.shootCooldown = system.GameConfig.PLAYER_BOOST_COOLDOWN;   // Cadence max
                    }
                }
            }
        }
        // ÉTAT DE VICTOIRE
        else if(gp.gameState == gp.winState) {
            if(code == KeyEvent.VK_R) {
                restartPressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_LEFT) leftPressed = false;
        if(code == KeyEvent.VK_RIGHT) rightPressed = false;
        if(code == KeyEvent.VK_SPACE) spacePressed = false;
        if(code == KeyEvent.VK_R) restartPressed = false;
    }
}