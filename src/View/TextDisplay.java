package View;

/**
 * Created by yury on 23.11.16.
 */
import Piece.Piece;
import Piece.Board;

public class TextDisplay implements View {
    public static TextDisplay INSTANCE = new TextDisplay();

    Board chessboard = Board.INSTANCE;

    public void update() {
        // Activate this to monitor the state of every board piece
        /*for (int i = 0; i < chessboard.pieces.length; ++i) {
            Piece p = chessboard.pieces[i];
            System.out.println(p.getX() + " " + p.getY() + " " + p.getType() + " " + p.isAlive());
        }
        System.out.println(chessboard.getTurn());*/

        for (int j = 7; j >= 0; --j) {
            for (int i = 0; i < 8; ++i) {
                try {
                    Piece p = chessboard.getPiece(i, j).get();
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

    public void checkHandler() {
        System.out.println("Check!");
    }

    public void mateHandler() {
        String s = "Checkmate!\n";
        if (!chessboard.getTurn())
            s += "White ";
        else
            s += "Black ";
        s += "wins!";
        System.out.println(s);
    }

    public void netPrompt() { System.out.println("Type 'L' to play local game, 'H' to become a host and 'C' to become a client." +
            " And then, type server ip in next line (if you are a server type 'server'\nPlease init the server first!"); }

    public void promotionHandler() { System.out.println("You can promote your pawn! Type 'Q' for Queen, 'R' for Rook, 'N' for Knight and 'B' for Bishop"); }
}
