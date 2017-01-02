package Controller;

import Piece.*;

/**
 * Created by yury on 13.12.16.
 */
public class AdvanceController extends Controller {
    //Singleton
    public static AdvanceController INSTANCE = new AdvanceController();

    // For the runtime loop
    private AdvanceController() { running = true; }

    public String[] gameType() {
        logger.log("Collecting information about game type...", true);
        // TODO: Add code here
        logger.log("Collected!", false);
        return null;
    }

    public Turn getCommand() {
        logger.log("Getting command...");
        // TODO: Add code here
        return null;
    }
}
