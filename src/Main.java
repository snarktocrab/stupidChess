import Controller.*;
import Network.*;
import Piece.Board;
import Piece.Turn;
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
        if (gameParams[0].equals("server")) {
            net = Server.INSTANCE;
            net.init(gameParams[1]);
        }
        else if (gameParams[0].equals("client")) {
            net = Client.INSTANCE;
            net.init(gameParams[1]);
        }

        //Run the game
        while (controller.isRunning()) {
            Turn currTurn;

            if (net == null) {
                controller.getCommand();
            }
            else if (gameParams[0].equals("server")) {
                // Firstly we move, secondly we receive turn from client
                currTurn = controller.getCommand();
                // TODO: Add normal handler of out moves (if we don't move or take don't pass the turn!)
                currTurn = net.receiveTurn();
                // TODO: Add turn handler here
            }
            else { // gameParams[0] -> "client"
                // Firstly we receive turn from server, secondly we move
                currTurn = net.receiveTurn();
                // TODO: Add turn handler here
                currTurn = controller.getCommand();
                // TODO: Add normal handler of out moves (if we don't move or take don't pass the turn!)
            }

        }

        if (net != null) net.quit();
    }
}
