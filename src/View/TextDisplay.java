package View;

/**
 * Created by yury on 23.11.16.
 */
import Piece.Piece;
import Piece.Board;

public class TextDisplay implements View {
    public static TextDisplay INSTANCE = new TextDisplay();

    private Board chessboard = Board.INSTANCE;

    // This sets default colour for white figures (this needs for dark backgrounds, where white is black actually)
    private boolean white = false;

    public void startHandler() {
        System.out.println("Game started!");
        update();
    }

    public void update() {
        // Activate this to monitor the state of every board piece
        /*for (int i = 0; i < chessboard.pieces.length; ++i) {
            Piece p = chessboard.pieces[i];
            System.out.println(p.getX() + " " + p.getY() + " " + p.getType() + " " + p.isAlive());
        }
        System.out.println(chessboard.getTurn());*/

        int start_j, inc;
        if (chessboard.getTurn()) {
            start_j = 7; inc = -1;
        }
        else {
            start_j = 0; inc = 1;
        }
        for (int j = start_j; (chessboard.getTurn() && j >= 0) || (!chessboard.getTurn() && j <= 7); j += inc) {
            System.out.print((j + 1) + "|");
            for (int i = 0; i < 8; ++i) {
                try {
                    Piece p = chessboard.getPiece(i, j).get();
                    switch (p.getType()) {
                        case 'K':
                            if (p.getColour() == white) System.out.print((char)0x2654);
                            else System.out.print((char)0x265A);
                            break;
                        case 'N':
                            if (p.getColour() == white) System.out.print((char)0x2658);
                            else System.out.print((char)0x265E);
                            break;
                        case 'B':
                            if (p.getColour() == white) System.out.print((char)0x2657);
                            else System.out.print((char)0x265D);
                            break;
                        case 'p':
                            if (p.getColour() == white) System.out.print((char)0x2659);
                            else System.out.print((char)0x265F);
                            break;
                        case 'Q':
                            if (p.getColour() == white) System.out.print((char)0x2655);
                            else System.out.print((char)0x265B);
                            break;
                        case 'R':
                            if (p.getColour() == white) System.out.print((char)0x2656);
                            else System.out.print((char)0x265C);
                            break;
                    }
                } catch (NullPointerException e) { System.out.print("."); }
            }
            System.out.print("\n");
        }
        System.out.println("  abcdefgh");
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

    public void netPrompt() { System.out.println("Do you like to play 'local' or to become 'server' or 'client'?" +
            " Type server ip in next line\nPlease init the server first!"); }

    public void waitHandler() { System.out.println("Waiting for opponent..."); }

    public void promotionHandler() { System.out.println("You can promote your pawn! Type 'Q' for Queen, 'R' for Rook, 'N' for Knight and 'B' for Bishop"); }
}
