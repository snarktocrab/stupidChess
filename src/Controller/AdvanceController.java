package Controller;

import Piece.*;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by yury on 13.12.16.
 */
public class AdvanceController extends Controller {
    private static final int tileWidth = 60, tileHeight = 60;
    private static final Point startPoint = new Point(27, 27);

    //Singleton
    public static AdvanceController INSTANCE = new AdvanceController();

    // For the runtime loop
    private AdvanceController() { running = true; }

    public void init(View v) {
        super.init(v);

        JPanel settingsPane = v.getSettingsPanel();
        settingsPane.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        JPanel boardPane = v.getChessPanel();
        boardPane.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                int x = mouseEvent.getX(), y = mouseEvent.getY();

                // We are outside the board
                if (x < startPoint.x || y < startPoint.y || x > 8 + startPoint.x + 8 * tileWidth || y > 8 + startPoint.y + 8 * tileHeight)
                    return;

                x -= startPoint.x;
                y -= startPoint.y;
                int borderX = x / (tileWidth + 1), borderY = y / (tileHeight + 1);
                System.out.println("" + (char)(borderX + 'a') + (8 - borderY));
            }

            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
    }

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
