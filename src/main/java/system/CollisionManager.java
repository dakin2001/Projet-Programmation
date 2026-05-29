package system;

import bonus.Bonus;
import entity.Enemy;
import entity.Projectile;

import org.example.GamePanel;

public class CollisionManager {

    // Référence au jeu
    GamePanel gp;

    public CollisionManager(GamePanel gp) {
        this.gp = gp;
    }

    // Lance toutes les vérifications de collision
    public void checkAll() {

        checkProjectileEnemy();
        checkPlayerEnemy();
        checkPlayerBonus();
        checkProjectilePlayer();
    }

    // Collision tir ↔ ennemi
    private void checkProjectileEnemy() {

        for(int i = gp.enemies.size() - 1; i >= 0; i--) {
            Enemy e = gp.enemies.get(i);

            for(int j = gp.player.projectiles.size() - 1; j >= 0; j--) {
                var p = gp.player.projectiles.get(j);

                // Si un tir touche un ennemi
                if(e.getBounds().intersects(p.getBounds())) {
                    
                    e.life--; // L'ennemi perd 1 point de vie
                    gp.player.projectiles.remove(j); // Le tir du joueur disparaît
                    
                    // Si l'ennemi n'a plus de vie, il meurt
                    if(e.life <= 0) {
                        gp.enemies.remove(i);
                        
                        // Calcul du score avec la constante
                        int points = system.GameConfig.SCORE_PER_ENEMY;
                        if(e.type == 2) {
                            points = (int)(system.GameConfig.SCORE_PER_ENEMY * system.GameConfig.ENEMY_ADVANCED_SCORE_MULT);
                        }
                        gp.scoreManager.addScore(points);
                    }
                    break; // On sort de la boucle des projectiles car celui-ci a été détruit
                }
            }
        }
    }

    // Collision joueur ↔ ennemi
    private void checkPlayerEnemy() {

        for(int i = gp.enemies.size() - 1; i >= 0; i--) {
            Enemy e = gp.enemies.get(i);

            if(gp.player.getBounds().intersects(e.getBounds())) {
                gp.enemies.remove(i);
                gp.player.life--;
            }
        }
    }

    // Collision joueur ↔ bonus
    private void checkPlayerBonus() {

        for(int i = gp.bonuses.size() - 1; i >= 0; i--) {
            Bonus b = gp.bonuses.get(i);

            if(gp.player.getBounds().intersects(b.getBounds())) {

                // Bonus vie
                if(b.type == 0) {
                    gp.player.addLife(1);
                }

                // Bonus tir rapide
                if(b.type == 1) {
                    gp.player.shootCooldown = system.GameConfig.PLAYER_BOOST_COOLDOWN;
                    gp.player.boostTimer = system.GameConfig.BOOST_DURATION_BONUS;
                }

                gp.bonuses.remove(i);
            }
        }
    }

    // Collision tir ennemi ↔ joueur
    private void checkProjectilePlayer() {
        
        // On parcourt tous les ennemis
        for(int i = gp.enemies.size() - 1; i >= 0; i--) {
            Enemy e = gp.enemies.get(i);
            
            // On parcourt tous les projectiles de CET ennemi
            for(int j = e.projectiles.size() - 1; j >= 0; j--) {
                Projectile p = e.projectiles.get(j);
                
                // Si le tir touche le joueur
                if(gp.player.getBounds().intersects(p.getBounds())) {
                    gp.player.life--;         // Le joueur perd une vie
                    e.projectiles.remove(j);  // Le tir disparaît
                }
            }
        }
    }
}