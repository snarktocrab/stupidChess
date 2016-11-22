package Piece;

/**
 * Created by daniel on 21.11.16.
 */
public class Bishop extends Piece {
    char TYPE = 'B';
    public boolean checkInBetween(int newX, int newY) {
        if (x - y != newX - newY || x + y != newX + newY || x == newX && y == newY) {
            return false;
        }
        if (x - y == newX - newY) {
            if (x < newX) {
                for (int i = x, j = y; i < newX; i++, j++) {
                    try {
                        if (Board.INSTANCE.getPiece(i, j).get().isAlive()) return false;
                    } catch (NullPointerException e) {}
                }
            }
            if (x > newX) {
                for (int i = x, j = y; i > newX; i--, j--) {
                    try {
                        if (Board.INSTANCE.getPiece(i, j).get().isAlive()) return false;
                    } catch (NullPointerException e) {}
                }
            }
        }
        if (x + y == newX + newY) {
            if (x < newX) {
                for (int i = x, j = y; i < newX; i++, j--) {
                    try {
                        if (Board.INSTANCE.getPiece(i, j).get().isAlive()) return false;
                    } catch (NullPointerException e) {}
                }
            }
            if (x > newX) {
                for (int i = x, j = y; i > newX; i--, j++) {
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
        } catch (NullPointerException e) { return false; }
        return checkInBetween(newX, newY);
    }
}
