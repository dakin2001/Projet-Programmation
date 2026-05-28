package org.example;

import javax.swing.JFrame;

public class Main {

    // Point d'entrée du programme
    static void main(String[] args) {

        // Création de la fenêtre
        JFrame window = new JFrame();

        // Ferme le programme quand on quitte
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Empêche le redimensionnement
        window.setResizable(false);

        // Définit le titre
        window.setTitle("NoGravity");

        // Création du panel du jeu
        GamePanel gamePanel = new GamePanel();

        // Ajoute le panel à la fenêtre
        window.add(gamePanel);

        // Ajuste la taille de la fenêtre
        window.pack();

        // Centre la fenêtre
        window.setLocationRelativeTo(null);

        // Rend la fenêtre visible
        window.setVisible(true);

        // Lance le game loop
        gamePanel.startGameThread();
    }
}