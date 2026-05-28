package org.example;

import bonus.Bonus;
import entity.Enemy;
import entity.Player;
import system.CollisionManager;
import system.ScoreManager;
import ui.UI;
import ui.Menu;
import system.GameConfig;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    // Gestion spawn ennemis et bonus
    Random random = new Random();
    int spawnTimer = 0;
    int bonusTimer = 0;

    // Listes des objets du jeu
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Bonus> bonuses = new ArrayList<>();

    // Gestion du score
    public ScoreManager scoreManager = new ScoreManager();

    // Gestion collisions et affichage UI
    CollisionManager collisionManager = new CollisionManager(this);
    UI ui = new UI(this);

    // Paramètres écran
    final int tileSize = 48;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    public final int screenWidth = system.GameConfig.SCREEN_WIDTH; // tileSize * maxScreenCol;
    public final int screenHeight = system.GameConfig.SCREEN_HEIGHT; // tileSize * maxScreenRow;

    // Système principal
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler(this);

    // Joueur principal
    public Player player = new Player(this, keyHandler);

    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    public Menu menu = new Menu(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        gameState = playState;
    }

    // Lance la boucle de jeu
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Boucle principale du jeu
    @Override
    public void run() {

        double drawInterval = 1000000000 / system.GameConfig.FPS; // 1 seconde / FPS
        double delta = 0;
        long lastTime = System.nanoTime();

        while(gameThread != null) {

            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    // Mise à jour globale du jeu
    public void update() {

        if(gameState == playState){
            player.update();
            // Restart si mort
            if(player.life <= 0 && keyHandler.restartPressed) {
                restartGame();
                return;
            }

            // Stop si mort
            if(player.life <= 0) return;

            // Update joueur
            player.update();

            // Gestion ennemis
            spawnEnemies();
            updateEnemies();

            // Gestion bonus
            spawnBonus();
            updateBonuses();

            // Gestion collisions
            collisionManager.checkAll();

            // Nettoyage
            cleanEnemies();
        }

    }

    // Affichage du jeu
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Dessine joueur
        player.draw(g2);

        // Dessine ennemis et bonus
        for(Enemy e : enemies) e.draw(g2);
        for(Bonus b : bonuses) b.draw(g2);

        // Dessine interface
        ui.draw(g2);

        if(gameState == pauseState) {
            menu.draw(g2);
        }

        g2.dispose();
    }

    // Spawn des ennemis
    private void spawnEnemies() {
        spawnTimer--;
        if(spawnTimer <= 0) {
            int x = random.nextInt(screenWidth - 40);
            enemies.add(new Enemy(x, 0));
            spawnTimer = system.GameConfig.ENEMY_SPAWN_RATE;
        }
    }

    // Update des ennemis
    private void updateEnemies() {
        for(Enemy e : enemies) e.update();
    }

    // Update des bonus
    private void updateBonuses() {
        for(Bonus b : bonuses) b.update();
    }

    // Spawn des bonus
    private void spawnBonus() {
        bonusTimer--;
        if(bonusTimer <= 0) {
            int x = random.nextInt(screenWidth - 30);
            int type = random.nextInt(2);
            bonuses.add(new Bonus(x, 0, type));
            bonusTimer = system.GameConfig.BONUS_SPAWN_RATE;
        }
    }

    // Supprime ennemis hors écran
    private void cleanEnemies() {
        for(int i = enemies.size() - 1; i >= 0; i--) {
            if(enemies.get(i).y > screenHeight) enemies.remove(i);
        }
    }

    // Reset de la partie
    public void restartGame() {
        player.life = 3;
        scoreManager.score = 0;
        enemies.clear();
        player.projectiles.clear();

        player.x = screenWidth / 2 - 20;
        player.y = screenHeight - 100;

        spawnTimer = 0;
    }
}