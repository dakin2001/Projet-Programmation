package system;

public class GameConfig {

    // --- PARAMÈTRES DE L'ÉCRAN ---
    
    /** Taille de base d'une tuile (en pixels). */
    public static final int TILE_SIZE = 48;
    
    /** Nombre maximum de colonnes affichées à l'écran. */
    public static final int MAX_SCREEN_COL = 16;
    
    /** Nombre maximum de lignes affichées à l'écran. */
    public static final int MAX_SCREEN_ROW = 12;
    
    /** Largeur totale de la fenêtre du jeu. */
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    
    /** Hauteur totale de la fenêtre du jeu. */
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    /** Nombre d'images par seconde ciblées pour la boucle de jeu. */
    public static final int FPS = 60;

    
    // --- PARAMÈTRES DU JOUEUR ---
    
    /** Taille (largeur et hauteur) de la hitbox et du dessin du joueur. */
    public static final int PLAYER_SIZE = 40;
    
    /** Vitesse de déplacement du joueur de gauche à droite (pixels par image). */
    public static final int PLAYER_SPEED = 8;
    
    /** Nombre maximum de points de vie que le joueur peut posséder à la fois. */
    public static final int PLAYER_MAX_LIFE = 3;
    
    /** Délai d'attente normal (en images/frames) entre deux tirs du joueur. */
    public static final int PLAYER_NORMAL_COOLDOWN = 20;
    
    /** Délai d'attente (très court) quand le bonus de tir rapide est actif. */
    public static final int PLAYER_BOOST_COOLDOWN = 5;
    
    /** Vitesse à laquelle le laser du joueur monte vers le haut de l'écran. */
    public static final int PLAYER_PROJECTILE_SPEED = 10;
    

    // --- PARAMÈTRES DES ENNEMIS ---
    
    /** Taille (largeur et hauteur) de la hitbox et du dessin des ennemis. */
    public static final int ENEMY_SIZE = 40;
    
    /** Vitesse de descente de base d'un ennemi (pixels par image). */
    public static final int ENEMY_SPEED = 1;
    
    /** Ralentissement artificiel : l'ennemi bouge seulement 1 image sur X (ici 2). */
    public static final int ENEMY_MOVE_DELAY = 2; 
    
    /** Délai d'attente (en images/frames) entre deux tirs d'un ennemi. */
    public static final int ENEMY_SHOOT_COOLDOWN = 160;
    
    /** Vitesse à laquelle le laser de l'ennemi descend vers le bas de l'écran. */
    public static final int ENEMY_PROJECTILE_SPEED = 4;
    
    /** Fréquence d'apparition d'un nouvel ennemi (ex: tous les 60 ticks = 1 seconde). */
    public static final int ENEMY_SPAWN_RATE = 60; 

    
    // --- PARAMÈTRES DES PROJECTILES & BONUS ---
    
    /** Largeur des tirs (joueur et ennemi). */
    public static final int PROJECTILE_WIDTH = 5;
    
    /** Hauteur des tirs (joueur et ennemi). */
    public static final int PROJECTILE_HEIGHT = 10;
    
    /** Taille (largeur et hauteur) des carrés représentant les bonus. */
    public static final int BONUS_SIZE = 30;
    
    /** Vitesse de chute des bonus tombant du ciel. */
    public static final int BONUS_SPEED = 2;
    
    /** Fréquence d'apparition aléatoire d'un bonus (tous les 300 ticks = 5 secondes). */
    public static final int BONUS_SPAWN_RATE = 300;
    
    /** Temps d'activation du bonus "Tir Rapide" lorsqu'il est ramassé sur le terrain. */
    public static final int BOOST_DURATION_BONUS = 300; 
    
    /** Temps d'activation du bonus "Tir Rapide" (plus long) lorsqu'acheté en boutique. */
    public static final int BOOST_DURATION_SHOP = 500;  

    
    // --- PARAMÈTRES DE L'ÉCONOMIE (BOUTIQUE ET SCORE) ---
    
    /** Nombre de points gagnés à chaque fois qu'un ennemi est détruit. */
    public static final int SCORE_PER_ENEMY = 10;
    
    /** Coût en points pour acheter 1 point de vie supplémentaire dans la boutique. */
    public static final int PRICE_LIFE = 50;
    
    /** Coût en points pour activer le mode Tir Rapide depuis la boutique. */
    public static final int PRICE_BOOST = 100;


    // --- ENVIRONNEMENT (FOND D'ÉCRAN) ---
    
    /** Vitesse de défilement du fond d'écran (pixels par image). */
    public static final int BACKGROUND_SPEED = 2;
    
    /** Chemin vers l'image du fond d'écran. */
    public static final String BACKGROUND_IMAGE = "/background.jpg";
}