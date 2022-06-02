/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import java.util.Random;

/**
 * GamePanel hérite de JPanel pour dessiner les composants du jeu
 * Elle implémente l'interface ActionListener
 * Elle capture les évenements du Timer ainsi que le Clavier
 * @author riyou
 */
public class GamePanel extends JPanel implements ActionListener {
    
    // Les attributs constantes du jeu
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25; // La taille d'une cellule
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 200; // L'intervalle de temps
    final int X[] = new int[GAME_UNITS];
    final int Y[] = new int[GAME_UNITS];
    
    // Les attributs variables
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    // Les méthodes
    GamePanel() {
        
        random = new Random();
        
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        
        // Pour capturer les touches du clavier
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        
        startGame();
    }
    
    public void startGame() {
        newApple();
        running = true;
        
        // Timer génère des évènements chaque DELEY ms
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }
    
    public void move() {
        for(int i = bodyParts; i > 0; i--) {
            X[i] = X[i-1];
            Y[i] = Y[i-1];
        }
        switch (direction) {
            case 'U':
                Y[0] -= UNIT_SIZE;
                break;
            case 'D':
                Y[0] += UNIT_SIZE;
                break;
            case 'L':
                X[0] -= UNIT_SIZE;
                break;
            case 'R':
                X[0] += UNIT_SIZE;
                break;
        }
    }
    
    public void checkApple() {
        if((X[0] == appleX) && (Y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    
    public void checkCollision() {
        // Vérifier si la tête heurte le corps
        for(int i = bodyParts; i > 0; i--)
            if((X[0] == X[i]) && (Y[0] == Y[i]))
                running = false;
        
        // Vérifier si la tête heurte un mur
        if(X[0] < 0 || X[0] > SCREEN_WIDTH || Y[0] < 0 || Y[0] > SCREEN_HEIGHT)
            running = false;
        
        // S'il y'a une collision on stoppe le Timer
        if(!running)
            timer.stop();
        
    }
    
    /**
     * Dessiner les composnats du jeu
     * @param g 
     */
    public void draw(Graphics g) {
        if(running) {
            
            // Les lignes horizontales de la grille
            for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++)
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            
            // Les lignes verticales de la grille
            for(int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++)
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            
            
            // Dessiner le but
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            
            // Dessiner la tête et le corps
            for(int i = 0; i < bodyParts; i++) {
                if(i==0)
                    g.setColor(Color.green); // Spécifier la couleur pour la tête
                else
                    g.setColor(new Color(45,180,0)); // Spécifier la couleur pour le corps

                g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
            }    
            
            // Dessiner le score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(
                    "Score: " + applesEaten,
                    (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());
            
        }
        else
            gameOver(g);

    }
    
    /**
     * Dessiner les composants du jeu dans le cas du Game Over
     * @param g 
     */
    public void gameOver(Graphics g) {
        
        // Dessiner le score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString(
                "Score: " + applesEaten,
                (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2,
                g.getFont().getSize());
        
        // Dessiner le message Game Over
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(
                "Game Over",
                (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2,
                SCREEN_HEIGHT / 2);
    }
    
    
    /**
     * Applé a chaque évènement par repaint()
     * @param g 
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    /**
     * Appelé a chaque fois qu'un évènement est déclenché
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollision();
        }
        // repaint() appelle paintComponent(Graphics g) pour redessiner les composants du jeu
        repaint();
    }
    
    /**
     * Cette classe permet de capturer les touches de direction
     * Elle hérite du classe java.awt.event.KeyAdapter
     * Elle redéfinit la méthode keyPressed
     */
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') direction = 'D';
                    break;
            }
        }
        
    }
    
}
