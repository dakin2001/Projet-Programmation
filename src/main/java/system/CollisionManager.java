package system;

import bonus.Bonus;
import entity.Enemy;
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
    }

    // Collision tir ↔ ennemi
    private void checkProjectileEnemy() {

        for(int i = gp.enemies.size() - 1; i >= 0; i--) {
            Enemy e = gp.enemies.get(i);

            for(int j = gp.player.projectiles.size() - 1; j >= 0; j--) {
                var p = gp.player.projectiles.get(j);

                if(e.getBounds().intersects(p.getBounds())) {
                    gp.enemies.remove(i);
                    gp.player.projectiles.remove(j);
                    gp.scoreManager.addScore(10);
                    break;
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
                    gp.player.shootCooldown = 5;
                    gp.player.boostTimer = 300;
                }

                gp.bonuses.remove(i);
            }
        }
    }
}