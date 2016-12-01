import Controller.*;
import Network.*;
import Piece.Board;
import View.*;

/**
 * Created by daniel on 22.11.16.
 */
public class Main {
    static View display = TextDisplay.INSTANCE;
    static Controller controller = BasicController.INSTANCE;
    static Board chessboard = Board.INSTANCE;
    static Net net = null;
    public static void main(String[] args) {

        // Tell the board and controller where to output
        controller.init(display);

        String[] gameParams = controller.gameType();

        //Run the game
        while (controller.isRunning()) {
            controller.getCommand();
        }

        if (net != null) net.quit();
    }
}
