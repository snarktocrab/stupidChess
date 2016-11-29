package View;

/**
 * Created by yury on 23.11.16.
 */

import Controller.BasicController;
import Piece.Piece;
import Piece.Board;

import java.io.Console;
import java.lang.management.BufferPoolMXBean;

public class TextDisplay implements View {
    public static TextDisplay INSTANCE = new TextDisplay();
    //Console c;

    public TextDisplay() {
        this.init();
    }

    public void init() {
        //c = System.console();
    }

    public void update() {
        /*for (int i = 0; i < Board.INSTANCE.pieces.length; ++i) {
            Piece p = Board.INSTANCE.pieces[i];
            System.out.println(p.getX() + " " + p.getY() + " " + p.getType() + " " + p.isAlive());
        }
        System.out.println(Board.INSTANCE.getTurn());*/
        // Раскоментировать это если нужен отладочный вывод состояния всех фигур
        for (int j = 7; j >= 0; --j) {
            for (int i = 0; i < 8; ++i) {
                try {
                    Piece p = Board.INSTANCE.getPiece(i, j).get();
                    switch (p.getType()) {
                        case 'K':
                            if (p.getColour()) System.out.print((char)0x2654);
                            else System.out.print((char)0x265A);
                            break;
                        case 'N':
                            if (p.getColour()) System.out.print((char)0x2658);
                            else System.out.print((char)0x265E);
                            break;
                        case 'B':
                            if (p.getColour()) System.out.print((char)0x2657);
                            else System.out.print((char)0x265D);
                            break;
                        case 'p':
                            if (p.getColour()) System.out.print((char)0x2659);
                            else System.out.print((char)0x265F);
                            break;
                        case 'Q':
                            if (p.getColour()) System.out.print((char)0x2655);
                            else System.out.print((char)0x265B);
                            break;
                        case 'R':
                            if (p.getColour()) System.out.print((char)0x2656);
                            else System.out.print((char)0x265C);
                            break;
                    }
                } catch (NullPointerException e) { System.out.print("."); }
            }
            System.out.print("\n");
        }

        if (Board.INSTANCE.isMate(Board.INSTANCE.getTurn())) {
            String s = "Game over! ";
            if (!Board.INSTANCE.getTurn()) // Уже передали ход
                s += "White ";
            else
                s += "Black ";
            s += "wins!";
            System.out.println(s);
            BasicController.INSTANCE.quit();
        }
    }

    public void checkHandler() {
        System.out.println("Check!");
    }
}
