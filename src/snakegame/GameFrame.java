/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;

import javax.swing.JFrame;

/**
 *
 * @author riyou
 */
public class GameFrame extends JFrame {
    GameFrame() {
        this.add(new GamePanel()); // Ajouter les composants du jeu
        this.setTitle("Mini Projet POO, SnakeGame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quitter toute l'application en tapant le bouton Fermer |X|
        this.setResizable(false); // Pour ne pas altérer les composants de la fenêtre
        this.pack(); // Adapter la fenêtre autour des composants qu'on a ajoutés
        this.setVisible(true); // Afficher la fenètre
        this.setLocationRelativeTo(null); // Pour que la fenêtre s'affiche au milieu de l'écran
    }
}
