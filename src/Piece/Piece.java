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
        Board.INSTANCE.whiteTurn = !Board.INSTANCE.whiteTurn;
    }
    public void take(int newX, int newY) {
        x = newX;
        y = newY;
    }

    // Проверяет, может ли фигура сходить в клетку x, y
    abstract boolean checkMove(int x, int y);

    // Проверяет, может ли фигура взять фигуру в клетке 
    abstract boolean checkAttack(int x, int y);

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean getColour() { return colour; }
    public boolean isAlive() { return alive; }
    public abstract char getType();

    // Мой любимый метод
    public void die() { alive = false; }
}
