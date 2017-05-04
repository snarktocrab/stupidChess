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
    protected Saver saver = Saver.INSTANCE;
    protected LinkedList<GameEventListener> listeners = new LinkedList<>();
    protected LinkedList<LogEventListener> logListeners = new LinkedList<>();

    // Tell the controller where to output
    public abstract void init();

    public abstract void mouseClicked(MouseEvent mouseEvent);

    // Input a command, relay it to Board
    public abstract Turn getCommand();
    public void turnHandler(Turn t) {
        throwLogEvent("Handling turn...", true);
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
            case 'l':
                saver.loadNet();
                display.update();
                break;
            default:
                return;
        }
        throwUpdateEvent();
        throwLogEvent("Successfully!", false);
    }

    public void addGameEventListener(GameEventListener listener) {
        listeners.add(listener);
    }
    public void addLogEventListener(LogEventListener listener) { logListeners.add(listener); }

    // Instead of infinity loop
    public boolean isRunning() { return running; }

    // Quits the program
    public void quit() { running = false; }

    protected void throwUpdateEvent() {
        for (GameEventListener listener : listeners)
            listener.updateDisplay(new UpdateEvent(this));
    }
    protected void throwCheckEvent() {
        for (GameEventListener listener : listeners)
            listener.check(new CheckEvent(this));
    }
    protected void throwMateEvent() {
        for (GameEventListener listener : listeners)
            listener.mate(new MateEvent(this));
    }
    protected void throwPromotionEvent(int id) {
        for (GameEventListener listener : listeners)
            listener.promotion(new PromotionEvent(this, id));
    }
    protected void throwLogEvent(String msg) {
        for (LogEventListener listener : logListeners)
            listener.logMessage(new LogEvent(this, msg));
    }
    protected void throwLogEvent(String msg, boolean enter) {
        for (LogEventListener listener : logListeners)
            listener.logFunction(new LogEvent(this, msg), enter);
    }
    protected void throwLogEvent(String msg, Exception ex) {
        for (LogEventListener listener : logListeners)
            listener.logError(new LogEvent(this, msg, ex));
    }
}
