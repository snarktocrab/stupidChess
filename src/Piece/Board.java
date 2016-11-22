package Piece; /**
 * Created by daniel on 21.11.16.
 */

import java.lang.ref.WeakReference;

public class Board {
    boolean whiteTurn;

    static Piece p = new Pawn(0, 0, true, true, true);

    // Singleton
    public static final Board INSTANCE = new Board(new Piece[]{p}, true);

    // Фигуры
    public Piece[] pieces;

    // newPieces - состояние доски, turn - принадлежность хода
    public Board(Piece[] newPieces, boolean turn) {
        pieces = newPieces;
        whiteTurn = turn;
    }

    // Возвращает ссылку на фигуру в x, y, при условии, что фигура жива
    public WeakReference<Piece> getPiece(int x, int y) {
        for (Piece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y && piece.isAlive()) {
                return new WeakReference<Piece>(piece);
            }
        }
        return new WeakReference<Piece>(null);
    }

    // Проверяет, находится ли клетка под ударом (нужно для короля)
    public boolean isThreatened(int x, int y, boolean colour) {
        for (Piece piece : pieces) {
            if (piece.isAlive() && piece.getColour() != colour && piece.checkAttack(x, y))
                return true;
        }
        return false;
    }

    public boolean move(int x1, int y1, int x2, int y2) {
        if (getPiece(x1, y1) != null && getPiece(x1, y1).get().checkMove(x2, y2) && getPiece(x1, y1).get().getColour() == whiteTurn) {
            getPiece(x1, y1).get().move(x2, y2);
            return true;
        }
        return false;
    }

    public void take(int x1, int y1, int x2, int y2) {
        if (getPiece(x1, y1) != null && getPiece(x1, y1).get().checkMove(x2, y2)) {
            getPiece(x2, y2).get().die();
            getPiece(x1, y1).get().move(x2, y2);
        }
    }
}
