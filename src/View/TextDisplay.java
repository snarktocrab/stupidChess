package View;

/**
 * Created by yury on 23.11.16.
 */

import Piece.Piece;
import Piece.Board;

import java.io.Console;

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
        for (int i = 0; i < 32; ++i) {
            Piece p = Board.INSTANCE.pieces[i];
            System.out.println(p.getX() + " " + p.getY() + " " + p.getType());
        }
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
    }
}
