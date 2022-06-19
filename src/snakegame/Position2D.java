/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;

/**
 *
 * @author riyou
 */
public class Position2D {
    
    public int x;
    public int y;
    Position2D(int _x, int _y) {
        x = _x;
        y = _y;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position2D other = (Position2D) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }
}
