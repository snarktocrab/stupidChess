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
        boolean colour = true;

        // Tell the board and controller where to output
        controller.init(display);

        String[] gameParams = controller.gameType();
        if (gameParams[0].equals("server")) {
            net = Server.INSTANCE;
            net.init(gameParams[1]);
            System.out.println("Game started!");
            colour = true;
        }
        else if (gameParams[0].equals("client")) {
            net = Client.INSTANCE;
            net.init(gameParams[1]);
            System.out.println("Game started!");
            colour = false;
        }

        //Run the game
        while (controller.isRunning()) {
            Turn currTurn;

            if (net == null) {
                controller.getCommand();
            }
            else if (chessboard.getTurn() == colour) {
                currTurn = controller.getCommand();
                if (currTurn != null)
                    net.sendTurn(currTurn);
            }
            else {
                display.waitHandler();
                currTurn = net.receiveTurn();
                controller.turnHandler(currTurn);
            }
        }

        if (net != null) net.quit();
    }
}
