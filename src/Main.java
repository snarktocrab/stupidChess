import Controller.*;
import Network.*;
import Piece.*;
import View.*;
import Logging.*;
import java.io.*;

public class Main {
    private static Logger logger = Logger.INSTANCE;
    private static View display = ScreenDisplay.INSTANCE;
    private static Controller controller = BasicController.INSTANCE;
    private static Board chessboard = Board.INSTANCE;
    private static Net net = null;
    public static void main(String[] args) {
        boolean colour = true;

        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        path = ""; //For running in IntelliJ not in terminal

        // Tell the board and controller where to output
        logger.init(path);
        controller.init(display);

        logger.log("Starting main function", true);

        String[] gameParams = controller.gameType();
        if (gameParams[0].equals("server")) {
            net = Server.INSTANCE;
            String ip = net.getIP();
            display.serverPrompt(ip);
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
                logger.log(currTurn);
            }
            else if (chessboard.getTurn() == colour) {
                currTurn = controller.getCommand();
                logger.log(currTurn);
                if (currTurn != null)
                    net.sendTurn(currTurn);
            }
            else {
                display.waitHandler();
                currTurn = net.receiveTurn();
                logger.log(currTurn);
                controller.turnHandler(currTurn);
            }
        }

        logger.log("Exiting our program", false);
        if (net != null) net.quit();
        logger.quit();
    }
}
