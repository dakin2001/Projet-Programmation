package system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreManager {

    public int score = 0;
    public int playTimeTicks = 0; 

    // Liste pour garder le top 3 des meilleurs TEMPS (en ticks)
    public ArrayList<Integer> topTimes = new ArrayList<>();

    public ScoreManager() {
        loadTimes();
    }

    public void addScore(int value) {
        score += value;
    }

    // NOUVEAU : On passe un boolean pour savoir si c'est une victoire
    public void checkHighScore(boolean isVictory) {
        if (isVictory) {
            topTimes.add(playTimeTicks);
            // Trie la liste par ordre croissant (le plus petit temps en premier !)
            Collections.sort(topTimes); 
            
            // Si on a plus de 3 temps, on supprime le dernier (le plus lent)
            if (topTimes.size() > 3) {
                topTimes.remove(3);
            }
            
            saveTimes();
        }
    }

    public void reset() {
        score = 0;
        playTimeTicks = 0;
    }

    // Convertit le temps actuel de la partie en "MM:SS"
    public String getFormattedTime() {
        return formatTicks(playTimeTicks);
    }
    
    // NOUVEAU : Convertit un temps sauvegardé (en ticks) en "MM:SS"
    public String getFormattedTime(int ticks) {
        return formatTicks(ticks);
    }

    // Outil mathématique pour formater les secondes
    private String formatTicks(int ticks) {
        int totalSeconds = ticks / GameConfig.FPS;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds); 
    }

    private void loadTimes() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(GameConfig.SAVE_FILE_PATH));
            String line;
            while ((line = br.readLine()) != null) {
                topTimes.add(Integer.parseInt(line));
            }
            br.close();
            Collections.sort(topTimes);
        } catch (Exception e) {
            System.out.println("Création d'un nouveau profil de temps.");
        }
    }

    private void saveTimes() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(GameConfig.SAVE_FILE_PATH));
            for (int t : topTimes) {
                bw.write(String.valueOf(t));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}