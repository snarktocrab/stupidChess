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
        net = controller.init(display);

        //Run the game
        while (BasicController.INSTANCE.isRunning()) {
            BasicController.INSTANCE.getCommand();
        }

        if (net != null) net.quit();
    }
}
