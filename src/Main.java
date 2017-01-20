import Controller.*;
import Network.*;
import Piece.*;
import View.*;
import Logging.*;
import java.io.*;

public class Main {
    private static Logger logger = Logger.INSTANCE;
    private static View display = ScreenDisplay.INSTANCE;
    private static Controller controller = AdvanceController.INSTANCE;
    private static Board chessboard = Board.INSTANCE;
    private static Net net = null;

    public static void main(String[] args) {
        boolean colour = true;

        // Uncomment this if you want to get path to your .jar file
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        path = ""; //For running in IntelliJ not in terminal

        // Tell the board and controller where to output
        logger.init(path);
        controller.init(display);

        logger.log("Starting main function");

        String[] gameParams = controller.gameType();
        if (gameParams[0].equals("server")) {
            net = Server.INSTANCE;
            String ip = net.getIP();
            if (ip != null) display.serverPrompt(ip);
            net.init(gameParams[1]);
            colour = display.colourPrompt();

            try {
                net.getOutStream().writeObject(colour);
                net.getOutStream().flush();
            } catch (IOException e) { System.err.println("Error: " + e); }
        }
        else if (gameParams[0].equals("client")) {
            net = Client.INSTANCE;
            net.init(gameParams[1]);

            try {
                colour = !(boolean)net.getInStream().readObject();
            } catch (IOException e) { System.err.println("Error: " + e); }
            catch (ClassNotFoundException e) { System.err.println("Class Error: " + e); }
        }

        Saver.INSTANCE.setNetworkActivity(net != null);

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

        logger.log("Exiting our program");
        if (net != null) net.quit();
        logger.quit();
        System.exit(0);
    }
}
