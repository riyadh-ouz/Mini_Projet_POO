/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;

import java.util.LinkedList;

/**
 *
 * @author riyou
 */
public class Snake {
    
    public LinkedList<Position2D> body;
    int unit_size;
    boolean ate;
    
    Snake(Position2D p, int _unit_size) {
        body = new LinkedList();
        body.addFirst(p);
        unit_size = _unit_size;
        ate = false;
    }
    
    public Position2D tete() {
        return body.getFirst();
    }
    
    public void move(char direction) {
        
        switch (direction) {
            case 'U' -> this.body.addFirst(new Position2D(this.tete().x, this.tete().y - unit_size));
            case 'D' -> this.body.addFirst(new Position2D(this.tete().x, this.tete().y + unit_size));
            case 'L' -> this.body.addFirst(new Position2D(this.tete().x - unit_size, this.tete().y));
            case 'R' -> this.body.addFirst(new Position2D(this.tete().x + unit_size, this.tete().y));
        }
        if (ate) ate = false;
        else body.removeLast();
    }
    
    public boolean collide(Position2D p) {
        return (tete().x == p.x && tete().y == p.y);
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
}
