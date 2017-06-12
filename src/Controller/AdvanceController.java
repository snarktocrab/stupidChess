package Controller;

import Piece.*;
import Events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by yury on 13.12.16.
 */
public class AdvanceController extends Controller {
    private static final int tileWidth = 60, tileHeight = 60;
    private static final Point startPoint = new Point(27, 27);

    private String newCommand = "";
    private boolean currPlayer, wasInformed, hasMateChecked, isFirstClick;

    //Singleton
    public static AdvanceController INSTANCE = new AdvanceController();

    // For the runtime loop
    private AdvanceController() { running = true; }

    public void init() {
        throwLogEvent("Initializing controller");
        wasInformed = false;
        hasMateChecked = false;
        isFirstClick = true;

        currPlayer = chessboard.getTurn();
        throwLogEvent("Controller has been successfully initialized!");
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        throwLogEvent("Handling mouseClicked...");
        int x = mouseEvent.getX(), y = mouseEvent.getY();

        // We are outside the board
        if (x < startPoint.x || y < startPoint.y || x > 8 + startPoint.x + 8 * tileWidth || y > 8 + startPoint.y + 8 * tileHeight)
            return;

        x -= startPoint.x;
        y -= startPoint.y;
        int boardX = x / (tileWidth + 1), boardY = 7 - y / (tileHeight + 1);

        if (isFirstClick) {
            Piece p = chessboard.getPiece(boardX, boardY).get();

            // If we first click on empty tile or this figure isn't ours
            if (p == null || p.getColour() != chessboard.getTurn()) return;
            chessboard.setSelectedFigure(p);
            throwUpdateEvent();
            isFirstClick = false;
        }
        else {
            chessboard.setBoardState();
            chessboard.setSelectedFigure(null);
            throwUpdateEvent();
            isFirstClick = true;
            newCommand += "-";
        }

        String s = "" + (char)(boardX + 'a') + (boardY + 1);

        newCommand += s;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
    }

    public Turn getCommand() {
        if (chessboard.getTurn() != currPlayer) {
            currPlayer = chessboard.getTurn();
            wasInformed = false;
            hasMateChecked = false;
        }

        // Ends the game if checkmate
        if (!hasMateChecked && chessboard.isMate(chessboard.getTurn())) {
            if (throwMateEvent()) { // User wants to play again
                throwResetEvent();
                return new Turn('r');
            }
            else {
                super.quit();
                return new Turn('q');
            }
        }
        hasMateChecked = true;

        // Informs the current player of check
        if (!wasInformed && chessboard.isCheck(chessboard.getTurn())) {
            throwCheckEvent();
            wasInformed = true;
        }

        // Control input format
        if (newCommand.length() != 5) {
            return null;
        }

        // Getting command from screen
        // System.out.println(newCommand);
        // Эту строку не удалять - она создаёт копию строки newCommand в s вместо того, чтобы
        // создавать на неё ссылку.
        String s = new String(newCommand);
        newCommand = "";


        int x1 = s.charAt(0) - 'a', y1 = s.charAt(1) - '1',
                x2 = s.charAt(3) - 'a', y2 = s.charAt(4) - '1';

        if (x1 > 7 || x2 > 7 || y1 > 7 || y2 > 7 || x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
            System.err.println("ERROR: Invalid command.");
            return null;
        }

        // Attempts to move, then take the target tile
        Piece p = chessboard.getPiece(x1, y1).get();
        if (p == null) return null;
        Turn t = new Turn ('m', x1, y1, x2, y2, p.getID(), p.getHasMoved());
        if (!chessboard.move(t)) {
            t = new Turn ('t', x1, y1, x2, y2, p.getID(), p.getHasMoved());
            if (!chessboard.take(t)) {
                chessboard.setBoardState();
                chessboard.setSelectedFigure(null);
                //System.err.println("ERROR: Illegal move." + (char)(x1 + 'a') + (y1 + 1) + "-" + (char)(x2 + 'a') + (y2 + 1));
                return null;
            }
        }
        throwUpdateEvent();

        int id = chessboard.needsPromotion(!chessboard.getTurn());

        if (id >= 0) {
            throwPromotionEvent(id);
            Turn prevt = chessboard.log.pop();
            if (prevt.type == 'm')
                return new Turn('p', prevt.x, prevt.y, prevt.x2, prevt.y2, prevt.pieceID, chessboard.getPiece(id).get().getType());
            return new Turn('P', prevt.x, prevt.y, prevt.x2, prevt.y2, prevt.pieceID, prevt.targID, chessboard.getPiece(id).get().getType());
        }

        return chessboard.log.peek();
    }
}