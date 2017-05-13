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
    private static Saver saver = Saver.INSTANCE;
    private static Logger logger = Logger.INSTANCE;
    private static View display = ScreenDisplay.INSTANCE;
    private static Controller controller = AdvanceController.INSTANCE;
    private static Board chessboard = Board.INSTANCE;
    private static Net net = null;

    private static Settings settings;

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

        display.addSettingsEventListener(new SettingsEventListener() {
            public void updateSettings(SettingsEvent e) {
                settings = new Settings(e.getSettings());
                logger.recordSettings(settings);
            }
            public Settings getCurrentSettings() { return settings; }
        });

        LogEventListener logEventListener = new LogEventListener() {
            public void logMessage(LogEvent e) {
                logger.log(e.getMessage());
            }
            public void logFunction(LogEvent e, boolean b) {
                logger.log(e.getMessage(), b);
            }
            public void logError(LogEvent e) {
                logger.err(e.getMessage(), e.getEx());
            }
            public String getCurrentPath() {
                return logger.getCurr_path();
            }
        };
        controller.addLogEventListener(logEventListener);
        display.addLogEventListener(logEventListener);

        SaveLoadListener saveLoadListener = new SaveLoadListener() {
            public void save(SaveLoadEvent e) {
                saver.save(e.getFilename());
            }
            public void load(SaveLoadEvent e) {
                saver.load(e.getFilename());
            }
            public void loadNet() { saver.loadNet(); }
        };
        controller.addSaveLoadListener(saveLoadListener);
        display.addSaveLoadListener(saveLoadListener);

        // Uncomment this if you want to get path to your .jar file
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        path = new String(); //For running in IntelliJ not in terminal

        logger.init(path);
        controller.init();

        // Reading settings
        try {
            FileInputStream fs = new FileInputStream(logger.getCurr_path() + "/settings.opt");
            ObjectInputStream in = new ObjectInputStream(fs);
            settings = new Settings((Settings)in.readObject());

        } catch (IOException e) { System.err.println("Unable to read settings!\n" + e); }
        catch (ClassNotFoundException e) { System.err.println("Class Exception!\n" + e);}

        logger.log("Starting main function");
        logger.log("Collecting information about game type...", true);

        String s = display.netPrompt();

        if (s == null) { // Exiting our game if we didn't get any option
            logger.quit();
            System.exit(0);
        }

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

        saver.setNetwork(net);
        if (net != null) net.addLogEventListener(logEventListener);

        display.startHandler();

        //Run the game
        int cntTurn = 0, fileNumber = 0;
        while (controller.isRunning()) {
            Turn currTurn;

            if (net == null || chessboard.getTurn() == colour) {
                currTurn = controller.getCommand();
                logger.log(currTurn);

                if (settings.isAutosaveEnabled && currTurn != null && cntTurn % settings.gapTurns == 0) {
                    saver.save("AUTOSAVE" + Integer.toString(fileNumber + 1));
                    fileNumber++;
                    fileNumber %= settings.numFiles;
                    cntTurn++;
                    cntTurn %= settings.gapTurns;
                }

                if (net != null && currTurn != null)
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
