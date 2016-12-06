package Logging;

import Piece.Turn;

import java.io.*;
import java.lang.*;
import java.lang.reflect.Field;

/**
 * Created by yury on 03.12.16.
 */
public class Logger {
    public static Logger INSTANCE = new Logger();

    private FileWriter out;

    private boolean isDebug;

    public void init(String s) {
        try {
            if (s.toLowerCase().equals("debug")) {
                out = new FileWriter("debug.txt", false);
                isDebug = true;
            }
            else {
                out = new FileWriter("log.txt", false);
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
                out.write("Turn received\n");
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
