import Controller.*;
import Events.*;
import Network.*;
import Piece.*;
import View.*;
import Logging.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class Main {
    private static Logger logger = Logger.INSTANCE;
    private static View display = ScreenDisplay.INSTANCE;
    private static Controller controller = AdvanceController.INSTANCE;
    private static Board chessboard = Board.INSTANCE;
    private static Net net = null;

    public static void main(String[] args) {
        boolean colour = true;

        JPanel boardPane = display.getChessPanel();
        boardPane.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                controller.mouseClicked(mouseEvent);
            }
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        controller.addGameEventListener(new GameEventListener() {
            public void updateDisplay(UpdateEvent e) {
                display.update();
            }
            public void check(CheckEvent e) {
                display.checkHandler();
            }
            public void mate(MateEvent e) {
                display.mateHandler();
            }
            public void promotion(PromotionEvent e) {
                String type = display.promotionHandler();
                chessboard.promote(e.id, type.charAt(0));
                display.update();
            }
        });

        // Uncomment this if you want to get path to your .jar file
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        path = new String(); //For running in IntelliJ not in terminal

        logger.init(path);
        controller.init();

        logger.log("Starting main function");
        logger.log("Collecting information about game type...", true);

        String s = display.netPrompt();
        String[] gameParams = new String[2];
        gameParams[0] = s;
        if (gameParams[0].equals("client")) {
            gameParams[1] = display.clientPrompt();
        }
        else {
            gameParams[1] = "";
        }

        logger.log("Collected!", false);

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
