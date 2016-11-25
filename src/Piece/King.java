package Piece;

/**
 * Created by daniel on 21.11.16.
 */
public class King extends Piece {
    char TYPE = 'K';

    public King(int _x, int _y, boolean _colour, boolean _alive, boolean moved) {
        super(_x, _y, _colour, _alive);
        hasMoved = moved;
    }

    boolean checkMove(int newX, int newY) {
        if (newX == x - 2 && newY == y &&
                !hasMoved &&
                Board.INSTANCE.getPiece(x - 4, y) != null &&
                Board.INSTANCE.getPiece(x - 4, y).get().TYPE == 'R' &&
                Board.INSTANCE.getPiece(x - 4, y).get().colour == colour &&
                !Board.INSTANCE.getPiece(x - 4, y).get().hasMoved &&
                Board.INSTANCE.getPiece(x - 1, y).get() == null &&
                Board.INSTANCE.getPiece(x - 2, y).get() == null &&
                Board.INSTANCE.getPiece(x - 3, y).get() == null &&
                !Board.INSTANCE.isThreatened(x, y, colour) &&
                !Board.INSTANCE.isThreatened(x - 1, y, colour) &&
                !Board.INSTANCE.isThreatened(x - 2, y, colour))
        {
            return true;
        }
        if (newX == x + 2 && newY == y &&
                !hasMoved && Board.INSTANCE.getPiece(x + 3, y) != null &&
                Board.INSTANCE.getPiece(x + 3, y).get().TYPE == 'R' &&
                Board.INSTANCE.getPiece(x + 3, y).get().colour == colour &&
                !Board.INSTANCE.getPiece(x + 3, y).get().hasMoved &&
                Board.INSTANCE.getPiece(x + 1, y).get() == null &&
                Board.INSTANCE.getPiece(x + 2, y).get() == null &&
                !Board.INSTANCE.isThreatened(x, y, colour) &&
                !Board.INSTANCE.isThreatened(x + 1, y, colour) &&
                !Board.INSTANCE.isThreatened(x + 2, y, colour)
                ) return true;
        return !(newX == x && newY == y ||
                Math.abs(newX - x) > 1 ||
                Math.abs(newY - y) > 1 ||
                Board.INSTANCE.isThreatened(newX, newY, colour) ||
                Board.INSTANCE.getPiece(newX, newY) != null
        );
    }

    boolean checkAttack(int newX, int newY) {
        return !(newX == x && newY == y ||
                Math.abs(newX - x) > 1 ||
                Math.abs(newY - y) > 1 ||
                Board.INSTANCE.isThreatened(newX, newY, colour) ||
                Board.INSTANCE.getPiece(newX, newY) == null ||
                Board.INSTANCE.getPiece(newX, newY).get().getColour() == colour
        );
    }

    public void move(int newX, int newY) {
        if (Math.abs(newX - x) == 2) {
            if (newX < x) {
                Board.INSTANCE.getPiece(x - 4, newY).get().move(newX + 1, y);
            }
            else {
                Board.INSTANCE.getPiece(x + 3, newY).get().move(newX - 1, y);
            }
        }
        x = newX;
        y = newY;
    }

    public char getType() { return TYPE; }
}
