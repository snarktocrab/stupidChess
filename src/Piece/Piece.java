package Piece;

/**
 * Created by daniel on 21.11.16.
 */
public abstract class Piece {
    boolean hasMoved = true;
    protected int x, y, thisID;
    protected static int id = 0;
    protected boolean colour, alive;
    char TYPE = '0';

    public Piece() {}

    // Создаем фигуру по параметрам
    public Piece(int _x, int _y, boolean _colour, boolean _alive) {
        x = _x;
        y = _y;
        thisID = id;
        id += 1;
        colour = _colour;
        alive = _alive;
    }

    public void move(int newX, int newY) {
        x = newX;
        y = newY;
        hasMoved = true;
    }

    // Проверяет, может ли фигура сходить в клетку x, y
    abstract boolean checkMove(int x, int y);

    // Проверяет, может ли фигура взять фигуру в клетке 
    abstract boolean checkAttack(int x, int y);

    public int getX() { return x; }
    public int getY() { return y; }
    public int getID() { return thisID; }
    public boolean getColour() { return colour; }
    public boolean isAlive() { return alive; }
    public void setHasMoved(boolean moved) { hasMoved = moved; }
    public boolean getHasMoved() { return hasMoved; }
    public abstract char getType();

    // Мой любимый метод
    public void die() { alive = false; }
    public void respawn() { alive = true; }

    public void setId(int newID) { thisID = newID; }
}
