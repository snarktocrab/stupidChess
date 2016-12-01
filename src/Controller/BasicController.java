package Controller;

import Piece.Board;
import View.View;
import java.util.Scanner;
import Network.*;

public class BasicController implements Controller {
    private boolean running;

    Board chessboard = Board.INSTANCE;

    // Singleton
    public static BasicController INSTANCE = new BasicController();
    public Scanner in = new Scanner(System.in);

    View display;

    // For the runtime loop
    public BasicController() {
        running = true;
    }

    // Tells the controller what display to use
    public Net init(View v) {
        display = v;

        v.netPrompt();
        String s = in.nextLine();
        char ch = s.charAt(0);

        switch (ch)
        {
            case 'L':
                return null;
            case 'H':
                s = in.nextLine();
                return new Server(s);
            case 'C':
                s = in.nextLine();
                return new Client(s);
            default:
                System.err.println("Invalid command!");
                break;
        }
        return null;
    }

    // Receives a move from input
    public void getCommand() {

        // Ends the game if checkmate
        if (chessboard.isMate(chessboard.getTurn())) {
            display.mateHandler();
            quit();
            return;
        }

        // Informs the current player of check
        if (chessboard.isCheck(chessboard.getTurn())) {
            display.checkHandler();
        }

        int id = chessboard.needsPromotion(!chessboard.getTurn());

        if (id >= 0) {
            display.promotionHandler();
            String s = in.nextLine();
            chessboard.promote(id, s.charAt(0));

            display.update();
            return;
        }

        String s = in.nextLine();

        // Exit command
        if (s.equals("exit") || s.equals("quit") || s.equals("stop")) {
            this.quit();
            return;
        }

        // Undo command
        if (s.equals("undo")) {
            chessboard.undo();
            display.update();
            return;
        }

        // Control input format
        if (s.length() != 5) {
            System.err.println("Invalid command.");
            return;
        }

        int x1 = s.charAt(0) - 'a', y1 = s.charAt(1) - '1',
                x2 = s.charAt(3) - 'a', y2 = s.charAt(4) - '1';

        if (x1 > 7 || x2 > 7 || y1 > 7 || y2 > 7 || x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
            System.err.println("Invalid command.");
            return;
        }

        // Attempts to move, then take the target tile
        if (!chessboard.move(x1, y1, x2, y2)) {
            if (!chessboard.take(x1, y1, x2, y2)) {
                System.err.println("Illegal move." + x1 + " " + y1 + " " + x2 + " " + y2);
                return;
            }
        }
        display.update();
    }

    public boolean isRunning() { return running; } // Вместо бесконечного цикла
    public void quit() { running = false; } // Завершает программу
}