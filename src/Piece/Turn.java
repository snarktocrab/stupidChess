package Piece;

/**
 * Created by daniel on 30.11.16.
 */
public class Turn {
    char type;
    int pieceID, targID;
    int x, y, x2, y2;
    boolean moved;

    public Turn() {}
    public Turn(char t, int startX, int startY, int id, boolean hasBeenMovedThisTurn) {
        type = t;
        x = startX;
        y = startY;
        pieceID = id;
        moved = hasBeenMovedThisTurn;
    }
    public Turn(char t, int startX, int startY, int id, int targId, boolean hasBeenMovedThisTurn) {
        type = t;
        x = startX;
        y = startY;
        pieceID = id;
        targID = targId;
        moved = hasBeenMovedThisTurn;
    }
}
