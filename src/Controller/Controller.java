package Controller;

import View.View;

public interface Controller {
    // Tell the controller where to output
    void init(View v);

    // Input a command, relay it to Board
    void getCommand();

    // End the game
    void quit();
}
