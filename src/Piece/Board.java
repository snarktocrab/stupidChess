package Piece; /**
 * Created by daniel on 21.11.16.
 */

import java.lang.ref.WeakReference;

public class Board {
    boolean whiteTurn;

    // Singleton
    public static final Board INSTANCE = new Board();

    // Фигуры
    public Piece[] pieces;

    // newPieces - состояние доски, turn - принадлежность хода
    public Board(Piece[] newPieces, boolean turn) {
        pieces = newPieces;
        whiteTurn = turn;
    }

    public Board() {
        whiteTurn = true;
        pieces = new Piece[32];
        // Пешки
        for (int i = 0; i < 8; ++i)
            pieces[i] = new Pawn(i, 1, true, true, false);
        for (int i = 8; i < 16; ++i)
            pieces[i] = new Pawn(i - 8, 6, false, true, false);
        // Слоны
        pieces[16] = new Bishop(2, 0, true, true, false);
        pieces[17] = new Bishop(5, 0, true, true, false);
        pieces[18] = new Bishop(2, 7, false, true, false);
        pieces[19] = new Bishop(5, 7, false, true, false);
        // Ладьи
        pieces[20] = new Rook(0, 0, true, true, false);
        pieces[21] = new Rook(7, 0, true, true, false);
        pieces[22] = new Rook(0, 7, false, true, false);
        pieces[23] = new Rook(7, 7, false, true, false);
        // Короли и королевы
        pieces[24] = new Queen(3, 0, true, true, false);
        pieces[25] = new Queen(3, 7, false, true, false);
        pieces[26] = new King(4, 0, true, true, false);
        pieces[27] = new King(4, 7, false, true, false);
        // Кони
        pieces[28] = new Knight(1, 0, true, true, false);
        pieces[29] = new Knight(6, 0, true, true, false);
        pieces[30] = new Knight(1, 7, false, true, false);
        pieces[31] = new Knight(6, 7, false, true, false);
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
        try {
            if (getPiece(x1, y1).get().checkMove(x2, y2) && getPiece(x1, y1).get().getColour() == whiteTurn) {
                getPiece(x1, y1).get().move(x2, y2);
                whiteTurn = !whiteTurn;
                return true;
            }
        } catch (NullPointerException e) { System.out.println("NOPE");}
        return false;
    }

    public boolean take(int x1, int y1, int x2, int y2) {
        try {
            if (getPiece(x1, y1).get().checkAttack(x2, y2)) {
                getPiece(x2, y2).get().die();
                getPiece(x1, y1).get().move(x2, y2);
            }
            else return false;
        } catch (NullPointerException e) {}
        return true;
    }
}
