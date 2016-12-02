package Controller;

import Piece.Board;
import Piece.Piece;
import Piece.Turn;
import View.View;
import java.util.Scanner;
import Network.*;

public class BasicController implements Controller {
    private boolean running;

    Board chessboard = Board.INSTANCE;

    // Singleton
    public static BasicController INSTANCE = new BasicController();
    public Scanner in = new Scanner(System.in);

    View display;

    // For the runtime loop
    public BasicController() {
        running = true;
    }

    // Tells the controller what display to use
    public void init(View v) {
        display = v;
    }

    public String[] gameType() {
        display.netPrompt();
        String s, params[] = new String[2];
        s = in.nextLine().toLowerCase();
        params[0] = s;
        if (s.equals("local") || s.equals("server")) {
            params[1] = "";
        }
        else if (s.equals("client")) {
            params[1] = in.nextLine();
        }
        return params;
    }

    // Receives a move from input
    public Turn getCommand() {

        // Ends the game if checkmate
        if (chessboard.isMate(chessboard.getTurn())) {
            display.mateHandler();
            return new Turn('q');
        }

        // Informs the current player of check
        if (chessboard.isCheck(chessboard.getTurn())) {
            display.checkHandler();
        }

        String s = in.nextLine();

        // Exit command
        if (s.equals("exit") || s.equals("quit") || s.equals("stop")) {
            quit();
            return new Turn('q');
        }

        // Undo command
        if (s.equals("undo")) {
            chessboard.undo();
            display.update();
            return new Turn('u');
        }

        // Control input format
        if (s.length() != 5) {
            System.err.println("Invalid command.");
            return null;
        }

        int x1 = s.charAt(0) - 'a', y1 = s.charAt(1) - '1',
                x2 = s.charAt(3) - 'a', y2 = s.charAt(4) - '1';

        if (x1 > 7 || x2 > 7 || y1 > 7 || y2 > 7 || x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
            System.err.println("Invalid command.");
            return null;
        }

        // Attempts to move, then take the target tile
        Piece p = chessboard.getPiece(x1, y1).get();
        if (p == null) return null;
        Turn t = new Turn ('m', x1, y1, x2, y2, p.getID(), p.getHasMoved());
        if (!chessboard.move(t)) {
            if (!chessboard.take(t)) {
                System.err.println("Illegal move." + x1 + " " + y1 + " " + x2 + " " + y2);
            }
        }
        display.update();

        int id = chessboard.needsPromotion(!chessboard.getTurn());

        if (id >= 0) {
            display.promotionHandler();
            String ss = in.nextLine();
            chessboard.promote(id, ss.charAt(0));

            display.update();
            Turn prevt = chessboard.log.pop();
            if (prevt.type == 'm')
                return new Turn('p', prevt.x, prevt.y, prevt.x2, prevt.y2, prevt.pieceID, ss.charAt(0));
            return new Turn('P', prevt.x, prevt.y, prevt.x2, prevt.y2, prevt.pieceID, prevt.targID, ss.charAt(0));
        }

        return chessboard.log.peek();
    }

    public void turnHandler(Turn t) {
        switch (t.type) {
            case 'q':
                quit();
                return;
            case 'p':
                chessboard.move(t);
                chessboard.promote(t.pieceID, t.newPiece);
                break;
            case 'P':
                chessboard.take(t);
                chessboard.promote(t.pieceID, t.newPiece);
            case 'm':case 'o':case 'O':
                chessboard.move(t);
                break;
            case 't':
                chessboard.take(t);
                break;
            case 'u':
                chessboard.undo();
                break;
            default:
                return;
        }
    }

    public boolean isRunning() { return running; } // Instead of infinity loop
    public void quit() { running = false; } // Quits the program
}