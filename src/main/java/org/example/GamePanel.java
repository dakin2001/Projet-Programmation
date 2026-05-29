package org.example;

import bonus.Bonus;
import entity.Enemy;
import entity.Player;
import environment.Background;
import system.CollisionManager;
import system.ScoreManager;
import ui.UI;
import ui.Menu;

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
    public final int screenWidth = system.GameConfig.SCREEN_WIDTH; // tileSize * maxScreenCol;
    public final int screenHeight = system.GameConfig.SCREEN_HEIGHT; // tileSize * maxScreenRow;

    // Système principal
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler(this);

    // Joueur principal
    public Player player = new Player(this, keyHandler);
    
    // Le fond d'écran
    public Background background = new Background();

    // La musique de fond
    public system.Sound music = new system.Sound();

    public system.Sound se = new system.Sound();

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int transitionState = 3; // Pendant la cinématique
    public final int winState = 4;        // Écran de victoire
    public int previousState;

    // Gestion des niveaux et cinématiques
    public int currentLevel = 1;
    public int transitionStep = 0;  // 0 = Vaisseau recule, 1 = Texte, 2 = Vaisseau avance
    public int transitionTimer = 0; // Chronomètre pour afficher le texte

    public Menu menu = new Menu(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        
        gameState = titleState; // Commence sur le menu principal
        currentLevel = 1;
        transitionStep = 1; // On passe l'étape "reculer" pour le tout début
        player.y = screenHeight + 50; // Le vaisseau commence caché en bas
    }

    // Lance la boucle de jeu
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

        playMusic();
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

        // VÉRIFICATION GAME OVER
        if(gameState == playState && player.life <= 0) {
            if(keyHandler.restartPressed) {
                restartGame();
            }
            return; // On stoppe la méthode update.
        }

        // Le fond d'écran bouge TOUT LE TEMPS (même pendant les cinématiques)
        if(gameState == playState || gameState == transitionState || gameState == titleState) {
            background.update();
        }

        if(gameState == playState){

            scoreManager.playTimeTicks++;

            // Vérification Passage Niveau 2
            if(currentLevel == 1 && scoreManager.score >= system.GameConfig.SCORE_LEVEL_2) {
                currentLevel = 2;
                gameState = transitionState;
                transitionStep = 0; // Le vaisseau va commencer par reculer
                enemies.clear();    // On nettoie l'écran
                bonuses.clear();
                player.projectiles.clear();
                return;
            }

            // Vérification Victoire Finale
            if(currentLevel == 2 && scoreManager.score >= system.GameConfig.SCORE_WIN) {
                gameState = winState;
                enemies.clear();
                bonuses.clear();
                player.projectiles.clear();
                return;
            }
            
            // On déplace le joueur
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
        // --- ÉTAT DE TRANSITION (CINÉMATIQUE) ---
        else if(gameState == transitionState) {
            
            // Étape 0 : Le vaisseau recule jusqu'à disparaître (uniquement pour le Niv 2)
            if(transitionStep == 0) {
                player.y += 4; // Vitesse de recul
                if(player.y > screenHeight + 50) {
                    transitionStep = 1; // Passe à l'étape texte
                    player.x = screenWidth / 2 - (system.GameConfig.PLAYER_SIZE / 2); // Recentre le vaisseau
                }
            }
            // Étape 1 : Le vaisseau est caché, on affiche le texte
            else if(transitionStep == 1) {
                transitionTimer++;
                if(transitionTimer >= 150) { // Attend environ 2,5 secondes
                    transitionStep = 2; // Passe à l'arrivée du vaisseau
                    transitionTimer = 0;
                }
            }
            // Étape 2 : Le vaisseau arrive par le bas
            else if(transitionStep == 2) {
                player.y -= 3; // Vitesse d'arrivée
                if(player.y <= screenHeight - 100) {
                    player.y = screenHeight - 100; // Le fixe à sa place exacte
                    gameState = playState;         // FIN DE LA CINÉMATIQUE, ON JOUE !
                }
            }
        }
        // --- ÉTAT DE VICTOIRE ---
        else if(gameState == winState) {
            if(keyHandler.restartPressed) {
                restartGame();
            }
        }

    }

    // Affichage du jeu
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Dessine le fond d'écran
        background.draw(g2);

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
            
            int enemyType = 1; 
            
            // Si on est au niveau 2, on a X% de chance de faire apparaître un boss
            if(currentLevel >= 2) {
                if(random.nextInt(100) < system.GameConfig.ENEMY_ADVANCED_SPAWN_CHANCE) {
                    enemyType = 2;
                }
            }
            
            enemies.add(new Enemy(this, x, 0, enemyType)); 
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
            int type = random.nextInt(3); // MODIFIÉ : 3 au lieu de 2 !
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

        // Vérifie si le score correspond à la victoire !
        boolean isVictory = (scoreManager.score >= system.GameConfig.SCORE_WIN);
        
        // N'enregistre le temps que si c'est une victoire
        scoreManager.checkHighScore(isVictory);
        scoreManager.reset();

        player.life = 3;
        
        enemies.clear();
        bonuses.clear();
        player.projectiles.clear();
        spawnTimer = 0;

        currentLevel = 1;
        gameState = titleState; 
        transitionStep = 1;
        transitionTimer = 0;
        player.x = screenWidth / 2 - (system.GameConfig.PLAYER_SIZE / 2);
        player.y = screenHeight + 50;
    }

    // Lance la musique de fond
    public void playMusic() {
        music.setFile(system.GameConfig.AUDIO_BACKGROUND);
        music.play();
        music.loop();
    }

    // Arrête la musique de fond
    public void stopMusic() {
        music.stop();
    }

    public void playShootSound() {
        se.setFile(system.GameConfig.AUDIO_SHOOT);
        se.play();
    }

    public int musicVolume = 3;

    // Méthode pour mettre à jour le volume de la musique ET des tirs
    public void updateVolume(int scale) {
        musicVolume = scale;
        
        music.volumeScale = musicVolume;
        music.checkVolume();
        
        se.volumeScale = musicVolume;
    }
}