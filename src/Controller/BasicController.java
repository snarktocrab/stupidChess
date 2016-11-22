package Controller;

import Piece.Board;

import java.io.Console;

/**
 * Created by daniel on 22.11.16.
 */
public class BasicController implements Controller {
    Console c;

    public BasicController() {
        c = System.console();
    }

    public void getMove() {
        while (true) {
            String s = c.readLine();
            if (s.length() != 5) {
                c.format("Invalid command.\n");
                continue;
            }
            int x1 = (int) (s.charAt(0) - '1'), y1 = (int) (s.charAt(1) - 'a'), x2 = (int) (s.charAt(3) - '0'), y2 = (int) (s.charAt(5) - '0');
            if (x1 > 7 || x2 > 7 || y1 > 7 || y2 > 7 || x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
                c.format("Invalid command.\n");
                continue;
            }
            if (!makeMove(x1, y1, x2, y2)) {
                c.format("Illegal move.\n");
            } else break;
        }
    }

    public boolean makeMove(int x1, int y1, int x2, int y2) {
        return Board.INSTANCE.move(x1, y1, x2, y2);
    }
}
