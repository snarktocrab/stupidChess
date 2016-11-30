package Piece; /**
 * Created by daniel on 21.11.16.
 */

import java.lang.ref.WeakReference;
import java.util.Stack;

public class Board {

    boolean whiteTurn;

    //String lastTurn;

    // Singleton
    public static final Board INSTANCE = new Board();
    //public static final Board INSTANCE = new Board(new Piece[]{new Pawn(0, 6, true, true, true), new King(0, 0, true, true, true),
    //new King(7, 7, false, true, true)}, true);


    // Фигуры
    public Piece[] pieces;

    public Stack<Turn> log = new Stack<>();

    // newPieces - состояние доски, turn - принадлежность хода
    public Board(Piece[] newPieces, boolean turn) {
        pieces = newPieces;
        whiteTurn = turn;
        //lastTurn = "-";
    }

    public Board() {
        whiteTurn = true;
        pieces = new Piece[32];
        //lastTurn = "-";
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

    // Returns a reference to a piece by id
    public WeakReference<Piece> getPiece(int id) {
        return new WeakReference<Piece>(pieces[id]);
    }

    // Проверяет, находится ли клетка под ударом (нужно для короля)
    public boolean isThreatened(int x, int y, boolean colour) {
        for (Piece piece : pieces) {
            if (piece.isAlive() && piece.getColour() != colour && piece.checkAttack(x, y))
                return true;
        }
        return false;
    }

    // Moves piece from (x1, y1) to (x2, y2), returns true if successful
    public boolean move(int x1, int y1, int x2, int y2) {
        try {
            Piece p = getPiece(x1,y1).get();
            if (p.getType() == 'K' && x1 - x2 == 2 && p.checkMove(x2, y2)) {
                p.move(x2, y2);
                whiteTurn = !whiteTurn;
                log.push(new Turn('O', x1, y1, p.getID(), false));
                return true;
            }
            if (p.getType() == 'K' && x1 - x2 == -2 && p.checkMove(x2, y2)) {
                p.move(x2, y2);
                whiteTurn = !whiteTurn;
                log.push(new Turn('o', x1, y1, p.getID(), false));
                return true;
            }
            if (p.checkMove(x2, y2) && p.getColour() == whiteTurn) {
                p.move(x2, y2);
                whiteTurn = !whiteTurn;

                // Generating move signature
                log.push(new Turn('m', x1, y1, p.getID(), p.getHasMoved()));
                /*if (!p.getHasMoved()) {
                    p.setHasMoved(true);
                    lastTurn = "m" + x1 + y1 + x2 + y2 + "@";
                }
                else
                    lastTurn = "m" + x1 + y1 + x2 + y2 + "$";*/
                if (isCheck(!whiteTurn)) {
                    System.err.println("Moving into check");
                    undo();
                    return false;
                }
                return true;
            }
        } catch (NullPointerException e) { System.err.println("Attempted to move from empty tile " + x2 + " " + y2);}
        return false;
    }

    // Attempt to take (x2, y2) with (x1, y1), returns true if successful
    public boolean take(int x1, int y1, int x2, int y2) {
        try {
            Piece p = getPiece(x1,y1).get();
            if (p.checkAttack(x2, y2) && p.getColour() == whiteTurn) {
                int id = getPiece(x2, y2).get().getID();
                getPiece(x2, y2).get().die();
                p.move(x2, y2);
                whiteTurn = !whiteTurn;

                // Generates move for undo
                log.push(new Turn('t', x1, y1, p.getID(), getPiece(x2, y2).get().getID(), p.getHasMoved()));
                /*if (!p.getHasMoved()) {
                    p.setHasMoved(true);
                    lastTurn = "t" + x1 + y1 + x2 + y2 + "@" + id; // "@ - фигура подвинулась в этом ходу, $ - нет"
                }
                else
                    lastTurn = "t" + x1 + y1 + x2 + y2 + "$" + id;*/
                if (isCheck(!whiteTurn)) {
                    System.err.println("Moving into check");
                    undo();
                }
                return true;
            }
        } catch (NullPointerException e) {}
        return false;
    }

    public boolean isCheck(boolean curr_colour) {
        Piece king = null;
        for (Piece p : pieces) { // Ищем своего короля
            if (p.getType() == 'K' && p.colour == curr_colour) {
                king = p;
                break;
            }
        }
        if (isThreatened(king.x, king.y, king.colour))
            return true;
        return false;
    }

    public boolean isMate(boolean curr_colour) {
        if (!isCheck(curr_colour)) return false;
        // Checks all living loyal pieces, attempts to move them to all possible tiles - if at least one move can
        // prevent check, then returns false
        for (Piece p : pieces) {
            if (p.getColour() == curr_colour && p.isAlive()) {
                for (int i = 0; i < 8; ++i) {
                    for (int j = 0; j < 8; ++j) {
                        if (p.checkMove(i, j)) {
                            log.push(new Turn('m', p.getX(), p.getY(), p.getID(), p.getHasMoved()));
                            p.move(i, j);
                            whiteTurn = !whiteTurn;
                            if (!isCheck(!whiteTurn)) {
                                undo();
                                return false;
                            }
                            undo();
                        }
                        try {
                            if (p.checkAttack(i, j)) {
                                log.push(new Turn('t', p.getX(), p.getY(), p.getID(), getPiece(i, j).get().getID(), p.getHasMoved()));
                                getPiece(i, j).get().die();
                                p.move(i, j);
                                whiteTurn = !whiteTurn;
                                if (!isCheck(!whiteTurn)) {
                                    undo();
                                    return false;
                                }
                                undo();
                            }
                        } catch (NullPointerException e) {}
                    }
                }
            }
        }
        return true;
    }

    public void undo()
    {
        if (log.empty()) {
            System.err.println("Nothing to undo!");
            return;
        }

        Turn lastMove = log.pop();

        if (lastMove.type == 'o') {
            Piece king = getPiece(lastMove.pieceID).get();
            Piece rook = getPiece(king.getX() - 1, king.getY()).get();
            rook.move(king.getX() + 1, king.getY());
            king.move(king.getX() - 2, king.getY());
            whiteTurn = !whiteTurn;
            return;
        }

        if (lastMove.type == 'O') {
            Piece king = getPiece(lastMove.pieceID).get();
            Piece rook = getPiece(king.getX() + 1, king.getY()).get();
            rook.move(king.getX() - 2, king.getY());
            king.move(king.getX() + 2, king.getY());
            rook.setHasMoved(false);
            king.setHasMoved(false);
            whiteTurn = !whiteTurn;
            return;
        }

        try {
            Piece p = getPiece(lastMove.pieceID).get();
            p.move(lastMove.x, lastMove.y);
            p.setHasMoved(lastMove.moved);
        } catch (NullPointerException e) { System.err.println("Cannot undo, invalid move signature."); }

        if (lastMove.type == 't') {
            try {
                getPiece(lastMove.targID).get().respawn();
            } catch (NullPointerException e) {
                System.err.println("Cannot undo, attempting to respawn nonexistent piece.");
            }
        }
        whiteTurn = !whiteTurn;
    }

    public int needsPromotion(boolean colour) {
        if (colour) {
            for (Piece p : pieces)
                if (p.getType() == 'p' && p.isAlive() && p.colour == colour && p.y == 7)
                    return p.getID();
        }
        else {
            for (Piece p : pieces)
                if (p.getType() == 'p' && p.isAlive() && p.colour == colour && p.y == 0)
                    return p.getID();
        }
        return -1;
    }

    public void promote(int id, char type) {
        /*int i;
        for (i = 0; i < pieces.length; ++i) {
            if (pieces[i].getID() == id) break;
        }

        if (i == pieces.length) {
            System.err.println("Attempted to promote nonexistent piece at id " + id);
            return;
        }*/

        switch (type) {
            case 'R':
                pieces[id] = new Rook(pieces[id].getX(), pieces[id].getY(), pieces[id].colour, true, true);
                break;
            case 'N':
                pieces[id] = new Knight(pieces[id].getX(), pieces[id].getY(), pieces[id].colour, true, true);
                break;
            case 'B':
                pieces[id] = new Bishop(pieces[id].getX(), pieces[id].getY(), pieces[id].colour, true, true);
                break;
            case 'Q':
                pieces[id] = new Queen(pieces[id].getX(), pieces[id].getY(), pieces[id].colour, true, true);
                break;
            default:
                System.err.println("Illegal promotion, use RNBQ");
        }
    }
    public boolean getTurn() { return whiteTurn; }
}
