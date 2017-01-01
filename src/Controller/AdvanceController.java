package Controller;

import Logging.Logger;
import Piece.*;
import View.*;

/**
 * Created by yury on 13.12.16.
 */
public class AdvanceController extends Controller {
    //Singleton
    public static AdvanceController INSTANCE = new AdvanceController();

    // For the runtime loop
    private AdvanceController() { running = true; }

    public String[] gameType() {
        logger.log("Entered Controller - 'gameType'", true);
        logger.log("Some text");
        // TODO: Add code here
        logger.log("Exited", false);
        return null;
    }

    public Turn getCommand() {
        // TODO: Add code here
        return null;
    }
}
