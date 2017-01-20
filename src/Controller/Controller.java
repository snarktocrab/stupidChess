package Controller;

import Logging.Logger;
import Logging.Saver;
import Piece.*;
import View.*;

public abstract class Controller {
    protected boolean running;

    protected Board chessboard = Board.INSTANCE;
    protected View display;
    protected Logger logger = Logger.INSTANCE;
    protected Saver saver = Saver.INSTANCE;

    // Tell the controller where to output
    public void init(View v) { display = v; }

    abstract public String[] gameType();

    // Input a command, relay it to Board
    abstract public Turn getCommand();
    public void turnHandler(Turn t) {
        logger.log("Handling turn...");
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
            case 's':
                saver.save(t.filename);
                break;
            case 'l':
                saver.load(t.filename);
                display.update();
                break;
            default:
                return;
        }
        display.update();
    }

    // Instead of infinity loop
    public boolean isRunning() { return running; }

    // Quits the program
    public void quit() { running = false; }
}
