package Controller;

import Network.*;
import Piece.Turn;
import View.View;

public interface Controller {
    // Tell the controller where to output
    void init(View v);

    String[] gameType();

    // Input a command, relay it to Board
    Turn getCommand();

    boolean isRunning();

    // End the game
    void quit();
}
