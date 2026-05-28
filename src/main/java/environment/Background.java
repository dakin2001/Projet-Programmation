package environment;

import system.GameConfig;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {

    // On n'a besoin que d'un seul 'y' pour faire glisser deux images !
    public int y;
    public BufferedImage image;

    public Background() {
        y = 0;
        
        // Chargement de l'image de fond
        try {
            image = ImageIO.read(getClass().getResourceAsStream(GameConfig.BACKGROUND_IMAGE));
        } catch (Exception e) {
            System.out.println("Erreur: Image de fond introuvable ! Vérifie le dossier src/main/resources.");
            e.printStackTrace();
        }
    }

    public void update() {
        // Le fond descend
        y += GameConfig.BACKGROUND_SPEED;
        
        // Dès que l'image du bas sort complètement de l'écran, 
        // on réinitialise la position pour créer une boucle infinie.
        if (y >= GameConfig.SCREEN_HEIGHT) {
            y = 0;
        }
    }

    public void draw(Graphics2D g2) {
        if (image != null) {
            // Dessine la 1ère image (celle qui est actuellement à l'écran et qui descend)
            g2.drawImage(image, 0, y, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT, null);
            
            // Dessine la 2ème image (celle qui est "accrochée" exactement au-dessus de la première)
            g2.drawImage(image, 0, y - GameConfig.SCREEN_HEIGHT, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT, null);
        } else {
            // Sécurité : si l'image ne charge pas, on met un fond noir par défaut
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        }
    }
}