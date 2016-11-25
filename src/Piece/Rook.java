package Piece;

/**
 * Created by daniel on 21.11.16.
 */
public class Rook extends Piece {
    char TYPE = 'R';

    public Rook(int _x, int _y, boolean _colour, boolean _alive, boolean moved) {
        super(_x, _y, _colour, _alive);
        hasMoved = moved;
    }

    private boolean checkInBetween(int newX, int newY) {
        if (x != newX && y != newY || x == newX && y == newY) {
            return false;
        }
        if (x == newX) {
            if (y > newY) {
                for (int i = y - 1; i > newY; --i) {
                    try {
                        if (Board.INSTANCE.getPiece(x, i).get().isAlive()) return false;
                    } catch (NullPointerException e) {}
                }
            }
            else {
                for (int i = y + 1; i < newY; ++i) {
                    try {
                        if (Board.INSTANCE.getPiece(x, i).get().isAlive()) return false;
                    } catch (NullPointerException e) {}
                }
            }
        }
        if (y == newX) {
            if (x > newY) {
                for (int i = x - 1; i > newX; --i) {
                    try {
                        if (Board.INSTANCE.getPiece(i, y).get().isAlive()) return false;
                    } catch (NullPointerException e) {}
                }
            }
            else {
                for (int i = x + 1; i < newX; ++i) {
                    try {
                        if (Board.INSTANCE.getPiece(i, y).get().isAlive()) return false;
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
        } catch (NullPointerException e) { return false; }
        return checkInBetween(newX, newY);
    }

    public char getType() { return TYPE; }
}
