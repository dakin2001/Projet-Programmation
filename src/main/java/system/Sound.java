package system;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    FloatControl fc;
    public int volumeScale = 3; // Volume par défaut (de 0 à 5)
    float volume;

    public void setFile(String soundFileName) {
        try {
            URL soundURL = getClass().getResource(soundFileName);
            if(soundURL != null) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
                clip = AudioSystem.getClip();
                clip.open(ais);
                
                // On récupère le contrôleur de volume du fichier son
                fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                checkVolume(); // On applique le bon volume dès le chargement
            } else {
                System.out.println("Fichier audio introuvable : " + soundFileName);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    // Calcule et applique le volume en décibels
    public void checkVolume() {
        switch(volumeScale) {
            case 0: volume = -80f; break; // Muet
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f;  break;
            case 5: volume = 6f;  break; // Max
        }
        if (fc != null) {
            fc.setValue(volume);
        }
    }
}