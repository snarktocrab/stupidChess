package Piece;

import java.io.Serializable;

/**
 * Created by daniel on 30.11.16.
 */
public class Turn implements Serializable {
    public char type; // m - move, t - take, O - castle queenside, o - castle kingside, p - promote move, P - promote take, q - quit, u - undo
    public char newPiece;
    public int pieceID, targID;
    public int x, y, x2, y2;
    public boolean moved;
    public String filename;

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

    // Promotion move
    public Turn(char t, int startX, int startY, int endX, int endY, int id, char title) {
        type = t;
        x = startX;
        y = startY;
        x2 = endX;
        y2 = endY;
        pieceID = id;
        newPiece = title;
    }

    // Promotion take
    public Turn(char t, int startX, int startY, int endX, int endY, int id, int targId, char title) {
        type = t;
        x = startX;
        y = startY;
        x2 = endX;
        y2 = endY;
        pieceID = id;
        targID = targId;
        newPiece = title;
    }

    // Save and load
    public Turn(char t, String file) {
        type = t;
        filename = file;
    }

    // Copy turn
    public Turn(Turn t) {
        type = t.type;
        x = t.x;
        y = t.y;
        x2 = t.x2;
        y2 = t.y2;
        pieceID = t.pieceID;
        targID = t.targID;
        newPiece = t.newPiece;
        moved = t.moved;
        if (t.filename != null) filename = new String(t.filename); // Copy string
    }
}
