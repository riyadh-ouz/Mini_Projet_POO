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
 * Elle capture les évenements du Timer ainsi que du Clavier
 * @author riyou
 */
public class GamePanel extends JPanel implements ActionListener {
    
    static final int UNIT_SIZE = 25; // La taille d'une cellule
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600 + UNIT_SIZE;
    
    static final int MAX_APPLES_PER_LEVEL = 7;
    static final int ACCELERATION = 25;
    static final int MIN_DELAY = 50;
    
    
    int delay = 200; // L'intervalle de temps pendant lequel le Timer déclenche une évènement
    
    int applesEatenPerLevel = 0;
    int level = 1;
    int score = 0;
    
    char direction = 'R';
    boolean running = false; // L'etat du jeu
    
    Snake snake;
    Timer timer;
    Random random;
    Position2D apple; // La position du but

    
    GamePanel() {
        
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        
        // Pour capturer les touches du clavier
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        
        startGame();
    }
    
    public void startGame() {
        
        snake = new Snake(new Position2D(0, UNIT_SIZE), UNIT_SIZE);
        
        random = new Random();
        
        apple = new Position2D(
                random.nextInt(0, (int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE,
                random.nextInt(1, (int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE
        );

        running = true;
        
        // Timer génère des évènements chaque delay ms
        timer = new Timer(delay, this);
        timer.start();
    }
    
    /**
     * Cette méthode est appelé à chaque fois qu'un évènement est déclenché
     * Elle appelle paintComponent(Graphics g) pour redessiner les composants du jeu
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            snake.move(direction);
            checkApple();
            checkCollision();
        }
        // repaint() appelle paintComponent(Graphics g) pour redessiner les composants du jeu
        repaint();
    }
    
    /**
     * Cette méthode est appelé par repaint() après le déclenchement d'un évènement
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    public void checkApple() {
        if(snake.collide(apple)) {
            snake.eat();
            
            applesEatenPerLevel++;
            if (applesEatenPerLevel >= MAX_APPLES_PER_LEVEL) {
                level++;
                applesEatenPerLevel = 1;
                if (delay > MIN_DELAY) {
                    delay -= ACCELERATION;
                    timer.setDelay(delay);
                }
            }
            score += level;
            
            newApple();
        }
    }
    
    public void newApple() {
        apple.x = random.nextInt(0, (int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        apple.y = random.nextInt(1, (int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }
    
    public void checkCollision() {
        
        if(snake.eatItSelf()) running = false;
        
        // Vérifier si la tête heurte un mur
        if(snake.tete().x < 0 || snake.tete().x >= SCREEN_WIDTH || snake.tete().y < UNIT_SIZE || snake.tete().y >= SCREEN_HEIGHT)
            running = false;
        
        // S'il y'a une collision on stoppe le Timer
        if(!running)
            timer.stop();
    }
    
    /**
     * Dessine les composnats du jeu
     * @param g 
     */
    public void draw(Graphics g) {
        if(running) {
            
            // Les lignes horizontales de la grille
            for(int i = 1; i < SCREEN_HEIGHT / UNIT_SIZE; i++)
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            
            // Les lignes verticales de la grille
            for(int i = 1; i < SCREEN_WIDTH / UNIT_SIZE; i++)
                g.drawLine(i * UNIT_SIZE, UNIT_SIZE, i * UNIT_SIZE, SCREEN_HEIGHT);
            
            // Dessiner le but
            g.setColor(Color.red);
            g.fillOval(apple.x, apple.y, UNIT_SIZE, UNIT_SIZE);
            
            // Dessiner le snake
            for(Position2D element : this.snake.body) {
                if(element == this.snake.tete())
                    g.setColor(Color.green); // Spécifier la couleur pour la tête
                else
                    g.setColor(new Color(45,180,0)); // Spécifier la couleur pour le corps
                
                g.fillRect(element.x, element.y, UNIT_SIZE, UNIT_SIZE);
            }
            
            // Dessiner le niveau et le score
            String msg = "Level: " + level + "   Score: " + score;
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            g.drawString(msg , 10, g.getFont().getSize());
            
        }
        else gameOver(g);

    }
    
    /**
     * Dessine les composants du jeu dans le cas du Game Over
     * @param g 
     */
    public void gameOver(Graphics g) {
        // Dessiner le niveau et le score
        String msg = "Level: " + level + "   Score: " + score;
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString(msg, (SCREEN_WIDTH - metrics1.stringWidth(msg)) / 2, g.getFont().getSize());
        
        // Dessiner le message Game Over
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString( "Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
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
                case KeyEvent.VK_LEFT -> {
                    if(direction != 'R') direction = 'L';
                }
                case KeyEvent.VK_RIGHT -> {
                    if(direction != 'L') direction = 'R';
                }
                case KeyEvent.VK_UP -> {
                    if(direction != 'D') direction = 'U';
                }
                case KeyEvent.VK_DOWN -> {
                    if(direction != 'U') direction = 'D';
                }
            }
        }
        
    }
    
}
