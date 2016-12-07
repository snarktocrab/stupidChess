import Controller.*;
import Network.*;
import Piece.*;
import View.*;
import Logging.*;

/**
 * Created by daniel on 22.11.16.
 */
public class Main {
    private static View display = TextDisplay.INSTANCE;
    private static Controller controller = BasicController.INSTANCE;
    private static Board chessboard = Board.INSTANCE;
    private static Net net = null;
    private static Logger logger = Logger.INSTANCE;
    public static void main(String[] args) {
        boolean colour = true;

        // Tell the board and controller where to output
        controller.init(display);
        logger.init("logging");

        String[] gameParams = controller.gameType();
        if (gameParams[0].equals("server")) {
            net = Server.INSTANCE;
            net.init(gameParams[1]);
            colour = true;
        }
        else if (gameParams[0].equals("client")) {
            net = Client.INSTANCE;
            net.init(gameParams[1]);
            colour = false;
        }

        display.startHandler();

        //Run the game
        while (controller.isRunning()) {
            Turn currTurn;

            if (net == null) {
                currTurn = controller.getCommand();
                logger.log(currTurn, "(our)");
            }
            else if (chessboard.getTurn() == colour) {
                currTurn = controller.getCommand();
                logger.log(currTurn, "(our)");
                if (currTurn != null)
                    net.sendTurn(currTurn);
            }
            else {
                display.waitHandler();
                currTurn = net.receiveTurn();
                logger.log(currTurn, "(from opponent)");
                controller.turnHandler(currTurn);
            }
        }

        if (net != null) net.quit();
        logger.quit();
    }
}
