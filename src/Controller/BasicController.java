package Controller;

import Piece.Board;
import View.TextDisplay;
import Piece.Piece;

import java.io.Console;
import java.lang.ref.WeakReference;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BasicController implements Controller {
    Console c;

    // Singleton
    public static BasicController INSTANCE = new BasicController();
    public Scanner in = new Scanner(System.in);

    public BasicController() {
        c = System.console();
    }

    public void getMove() {
        while (true) {
            String s = in.nextLine();
            if (s.equals("show")) {
                TextDisplay.INSTANCE.update();
                continue;
            }

            if (s.length() != 5) {
                System.out.println("Invalid command.");
                continue;
            }

            int x1 = (int) (s.charAt(0) - 'a'), y1 = (int) (s.charAt(1) - '1'),
                    x2 = (int) (s.charAt(3) - 'a'), y2 = (int) (s.charAt(4) - '1');

            if (x1 > 7 || x2 > 7 || y1 > 7 || y2 > 7 || x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
                System.out.println("Invalid command.");
                continue;
            }
            // Теперь пешки могут есть (другие фигуры тоже, но некоторые не ходят
            // TODO: Исправить ходы фигур
            // Сначала выполняем проверку может ли взять, если нет то может ли сходить
            if (!Board.INSTANCE.take(x1, y1, x2, y2)) {
                if (!makeMove(x1, y1, x2, y2)) {
                    System.out.println("Illegal move." + x1 + " " + y1 + " " + x2 + " " + y2);
                }
            } else break;
        }
    }

    public boolean makeMove(int x1, int y1, int x2, int y2) {
        return Board.INSTANCE.move(x1, y1, x2, y2);
    }
}