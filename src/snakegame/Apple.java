/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;

import java.awt.Color;

/**
 *
 * @author riyou
 */
public class Apple {
    
    private Position2D position;
    private Color color;
    
    Apple(Position2D p, Color c) {
        this.position = p;
        this.color = c;
    }
    
    public Position2D getPosition() {
        return this.position;
    }
    
    public void setPosition(Position2D p) {
        this.position = p;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setColor(Color c) {
        this.color = c;
    }
    
    public int getX() {
        return this.position.x;
    }
    
    public int getY() {
        return this.position.y;
    }
}
