package Piece;

/**
 * Created by daniel on 21.11.16.
 */
public class Bishop extends Piece {
    char TYPE = 'B';

    public Bishop(int _x, int _y, boolean _colour, boolean _alive, boolean moved) {
        super(_x, _y, _colour, _alive);
        hasMoved = moved;
    }

    public boolean checkInBetween(int newX, int newY) {
        if (x - y != newX - newY && x + y != newX + newY || x == newX && y == newY) {
            return false;
        }
        if (x - y == newX - newY) {
            if (x < newX) {
                for (int i = x + 1, j = y + 1; i < newX; i++, j++) {
                    try {
                        if (Board.INSTANCE.getPiece(i, j).get().isAlive()) return false;
                    } catch (NullPointerException e) {}
                }
            }
            if (x > newX) {
                for (int i = x - 1, j = y - 1; i > newX; i--, j--) {
                    try {
                        if (Board.INSTANCE.getPiece(i, j).get().isAlive()) return false;
                    } catch (NullPointerException e) {}
                }
            }
        }
        if (x + y == newX + newY) {
            if (x < newX) {
                for (int i = x + 1, j = y - 1; i < newX; i++, j--) {
                    try {
                        if (Board.INSTANCE.getPiece(i, j).get().isAlive()) return false;
                    } catch (NullPointerException e) {}
                }
            }
            if (x > newX) {
                for (int i = x - 1, j = y + 1; i > newX; i--, j++) {
                    try {
                        if (Board.INSTANCE.getPiece(i, j).get().isAlive()) return false;
                    } catch (NullPointerException e) {}
                }
            }
        }
        return true;
    }
    public boolean checkMove(int newX, int newY) {
        return Board.INSTANCE.getPiece(newX, newY).get() == null && checkInBetween(newX, newY);
    }

    public boolean checkAttack(int newX, int newY) {
        try {
            if (Board.INSTANCE.getPiece(newX, newY).get().getColour() == colour) {
                return false;
            }
        } catch (NullPointerException e) { /*System.err.println("Attacking empty tile");*/ }
        return checkInBetween(newX, newY);
    }

    public char getType() { return TYPE; }
}
