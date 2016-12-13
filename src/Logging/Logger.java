package Logging;

import Piece.Turn;

import java.io.*;
import java.lang.*;
import java.lang.reflect.Field;

public class Logger {
    public static Logger INSTANCE = new Logger();

    private FileWriter out;

    private boolean isDebug;

    public void init(String mode, String path) {
        try {
            if (mode.toLowerCase().equals("debug")) {
                out = new FileWriter(path + "/debug.txt", false);
                isDebug = true;
            }
            else {
                out = new FileWriter(path + "/log.txt", false);
                isDebug = false;
            }

        } catch (IOException e) { System.err.println("Log Error: " + e); }

        if (out == null) { System.err.println("Unable to open file"); }
    }

    public void log(Turn t, String our) {
        try {
            if (isDebug) {
                if (t.type == 'p') {
                    out.write("Received turn" + " " + our + " " + ":\ntype: " + t.type + "\nnewPiece: " + t.newPiece + "\npieceID: " + t.pieceID + "\ntargID: " + t.targID +
                            "\nx: " + t.x + "; y: " + t.y + "\nx2: " + t.x2 + "; y2: " + t.y2 + "\nmoved: " + t.moved);
                } else {
                    out.write("Received turn" + " " + our + " " + ":\ntype: " + t.type + "\npieceID: " + t.pieceID + "\ntargID: " + t.targID +
                            "\nx: " + t.x + "; y: " + t.y + "\nx2: " + t.x2 + "; y2: " + t.y2 + "\nmoved: " + t.moved);
                }
                out.write("\n----------------------\n");
            }
            else {
                switch (t.type) {
                    case 'o':
                        out.write("O-O\n");
                        break;
                    case 'O':
                        out.write("O-O-O\n");
                        break;
                    case 't':
                        out.write("" + (char)(t.x + 'a') + (t.y + 1) + "x" + (char)(t.x2 + 'a') + (t.y2 + 1) + "\n");
                        break;
                    case 'm':
                        out.write("" + (char)(t.x + 'a') + (t.y + 1) + "-" + (char)(t.x2 + 'a') + (t.y2 + 1) + "\n");
                        break;
                    case 'p':
                        out.write("" + (char)(t.x + 'a') + (t.y + 1) + "-" + (char)(t.x2 + 'a') + (t.y2 + 1) + "\n");
                        out.write("Promote to: " + t.newPiece + "\n");
                        break;
                    case 'P':
                        out.write("" + (char)(t.x + 'a') + (t.y + 1) + "x" + (char)(t.x2 + 'a') + (t.y2 + 1) + "\n");
                        out.write("Promote to: " + t.newPiece + "\n");
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) { System.err.println("Log Error: " + e); }
        catch (NullPointerException e) {}
    }

    public void quit() {
        try {
            if (out != null) out.close();
        } catch (IOException e) { System.err.println("Log Error: " + e); }
    }
}
