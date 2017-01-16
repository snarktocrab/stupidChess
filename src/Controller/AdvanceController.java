package Controller;

import Piece.*;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

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

    public void init(View v) {
        super.init(v);

        wasInformed = false;
        hasMateChecked = false;
        isFirstClick = true;

        currPlayer = chessboard.getTurn();

        JPanel boardPane = v.getChessPanel();
        boardPane.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
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
                    display.update();
                    isFirstClick = false;
                }
                else {
                    chessboard.setBoardState();
                    chessboard.setSelectedFigure(null);
                    display.update();
                    isFirstClick = true;
                    newCommand += "-";
                }

                String s = "" + (char)(boardX + 'a') + (boardY + 1);

                newCommand += s;

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }

            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
    }

    public String[] gameType() {
        logger.log("Collecting information about game type...", true);

        String s = display.netPrompt();
        String[] params = new String[2];
        if (s == null) {
            quit();
            params[0] = "null";
            return params;
        }
        params[0] = s;
        if (params[0].equals("client")) {
            params[1] = display.clientPrompt();
        }
        else {
            params[1] = "";
        }

        logger.log("Collected!", false);
        return params;
    }

    public Turn getCommand() {
        if (chessboard.getTurn() != currPlayer) {
            currPlayer = chessboard.getTurn();
            wasInformed = false;
            hasMateChecked = false;
        }

        // Ends the game if checkmate
        if (!hasMateChecked && chessboard.isMate(chessboard.getTurn())) {
            display.mateHandler();
            super.quit();
            return new Turn('q');
        }
        hasMateChecked = true;

        // Informs the current player of check
        if (!wasInformed && chessboard.isCheck(chessboard.getTurn())) {
            display.checkHandler();
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
        display.update();

        int id = chessboard.needsPromotion(!chessboard.getTurn());

        if (id >= 0) {
            String ss = display.promotionHandler();
            chessboard.promote(id, ss.charAt(0));

            display.update();
            Turn prevt = chessboard.log.pop();
            if (prevt.type == 'm')
                return new Turn('p', prevt.x, prevt.y, prevt.x2, prevt.y2, prevt.pieceID, ss.charAt(0));
            return new Turn('P', prevt.x, prevt.y, prevt.x2, prevt.y2, prevt.pieceID, prevt.targID, ss.charAt(0));
        }

        return chessboard.log.peek();
    }
}
