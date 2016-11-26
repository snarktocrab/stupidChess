package Piece;

/**
 * Created by daniel on 21.11.16.
 */
import java.lang.Math;

public class Knight extends Piece {
    char TYPE = 'N';

    public Knight(int _x, int _y, boolean _colour, boolean _alive, boolean moved) {
        super(_x, _y, _colour, _alive);
        hasMoved = moved;
    }

    private boolean canMoveTo(int newX, int newY) {
        return ((Math.abs(newX - x) == 2 && Math.abs(newY - y) == 1) || (Math.abs(newX - x) == 1 && Math.abs(newY - y) == 2));
    }
    public boolean checkMove(int newX, int newY) {
        return Board.INSTANCE.getPiece(newX, newY).get() == null && canMoveTo(newX, newY);
    }
    public boolean checkAttack(int newX, int newY) {
        try {
            if (Board.INSTANCE.getPiece(newX, newY).get().getColour() == colour) {
                return false;
            }
        } catch (NullPointerException e) {
            System.err.println("Attacking empty tile");
        }
        return canMoveTo(newX, newY);
    }

    public char getType() { return TYPE; }
}
