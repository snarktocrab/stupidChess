package Controller;

import Events.*;
import Logging.Logger;
import Logging.Saver;
import Piece.*;
import View.*;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

public abstract class Controller {
    protected boolean running;

    protected Board chessboard = Board.INSTANCE;
    protected View display;
    protected Logger logger = Logger.INSTANCE;
    protected Saver saver = Saver.INSTANCE;
    protected LinkedList<GameEventListener> listeners = new LinkedList<>();

    // Tell the controller where to output
    public abstract void init();

    public abstract void mouseClicked(MouseEvent mouseEvent);

    // Input a command, relay it to Board
    public abstract Turn getCommand();
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
                saver.save(t.filename, true);
                break;
            case 'l':
                saver.load(t.filename, true);
                display.update();
                break;
            default:
                return;
        }
        throwUpdateEvent();
    }

    public void addGameEventListener(GameEventListener listener) {
        listeners.add(listener);
    }

    // Instead of infinity loop
    public boolean isRunning() { return running; }

    // Quits the program
    public void quit() { running = false; }

    public void throwUpdateEvent() {
        for (GameEventListener listener : listeners) listener.updateDisplay(new UpdateEvent(this));
    }
    public void throwCheckEvent() {
        for (GameEventListener listener : listeners) listener.check(new CheckEvent(this));
    }
    public void throwMateEvent() {
        for (GameEventListener listener : listeners) listener.mate(new MateEvent(this));
    }
    public void throwPromotionEvent(int id) {
        for (GameEventListener listener : listeners) listener.promotion(new PromotionEvent(this, id));
    }
}
