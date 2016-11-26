package Piece;

/**
 * Created by daniel on 21.11.16.
 */
public class Pawn extends Piece {
    char TYPE = 'p';
    public Pawn(int _x, int _y, boolean _colour, boolean _alive, boolean moved) {
        super(_x, _y, _colour, _alive);
        hasMoved = moved;
    }

    public boolean checkMove(int newX, int newY) {
        if (colour) {
            return newX == x && (
                    !hasMoved && newY - y == 2 && Board.INSTANCE.getPiece(x, y + 1).get() == null || newY - y == 1
            );
        }
        else {
            return newX == x && (
                    !hasMoved && newY - y == -2 && Board.INSTANCE.getPiece(x, y - 1).get() == null || newY - y == -1
            );
        }
    }

    public boolean checkAttack(int newX, int newY) {
        try {
            if (colour)
                return Math.abs(newX - x) == 1 && newY - y == 1 &&
                        Board.INSTANCE.getPiece(newX, newY).get().getColour() != colour;
            else
                return Math.abs(newX - x) == 1 && newY - y == -1 &&
                        Board.INSTANCE.getPiece(newX, newY).get().getColour() != colour;
        } catch (NullPointerException e) {
            System.err.println("Attacking empty tile");
            if (colour)
                return Math.abs(newX - x) == 1 && newY - y == 1;
            else
                return Math.abs(newX - x) == 1 && newY - y == -1;
        }
    }

    public char getType() { return TYPE; }
}
