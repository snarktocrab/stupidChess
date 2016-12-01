package Piece;

import java.io.Serializable;

/**
 * Created by daniel on 30.11.16.
 */
public class Turn implements Serializable {
    char type; // m - move, t - take, O - castle queenside, o - castle kingside, p - promote, q - quit, u - undo
    char newPiece;
    int pieceID, targID;
    int x, y, x2, y2;
    boolean moved;

    public Turn() {}

    // Move
    public Turn(char t, int startX, int startY, int endX, int endY, int id, boolean hasBeenMovedThisTurn) {
        type = t;
        x = startX;
        y = startY;
        x2 = endX;
        y2 = endY;
        pieceID = id;
        moved = hasBeenMovedThisTurn;
    }

    // Take
    public Turn(char t, int startX, int startY, int endX, int endY, int id, int targId, boolean hasBeenMovedThisTurn) {
        type = t;
        x = startX;
        y = startY;
        x2 = endX;
        y2 = endY;
        pieceID = id;
        targID = targId;
        moved = hasBeenMovedThisTurn;
    }

    // Quit
    public Turn(char t) {
        type = t;
    }

    // Promotion
    public Turn(char t, int id, char title) {
        type = t;
        pieceID = id;
        newPiece = title;
    }

    public char getType() { return type; }
}
