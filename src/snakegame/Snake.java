/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;

import java.util.LinkedList;
import java.awt.Color;

/**
 *
 * @author riyou
 */
public class Snake {
    
    private LinkedList<Position2D> body;
    private int unit_size;
    private Color headColor;
    private Color bodyColor;
    private boolean ate;
    
    Snake(Position2D p, int _unit_size, Color _headColor, Color _bodyColor) {
        this.body = new LinkedList();
        this.body.addFirst(p);
        this.unit_size = _unit_size;
        
        this.headColor = _headColor;
        this.bodyColor = _bodyColor;
        
        this.ate = false;
    }
    
    public Position2D getHead() {
        return body.getFirst();
    }
    
    public LinkedList<Position2D> getBody() {
        return this.body;
    }
    
    public void move(char direction) {
        
        switch (direction) {
            case 'U' -> this.body.addFirst(new Position2D(this.getHead().x, this.getHead().y - unit_size));
            case 'D' -> this.body.addFirst(new Position2D(this.getHead().x, this.getHead().y + unit_size));
            case 'L' -> this.body.addFirst(new Position2D(this.getHead().x - unit_size, this.getHead().y));
            case 'R' -> this.body.addFirst(new Position2D(this.getHead().x + unit_size, this.getHead().y));
        }
        if (ate) ate = false;
        else body.removeLast();
    }
    
    public boolean collide(Position2D p) {
        return (getHead().equals(p));
    }
    
    public boolean eatItSelf() {
        
        for(int i = 1; i < body.size(); i++) {
            if (collide(body.get(i))) return true;
        }
        return false;
    }
    
    public void eat() {
        ate = true;
    }
    
    public Color getBodyColor() {
        return this.bodyColor;
    }
    
    public Color getHeadColor() {
        return this.headColor;
    }
    
    public void setBodyColor(Color c) {
        this.bodyColor = c;
    }
    
    public void setHeadColor(Color c) {
        this.headColor = c;
    }
}
