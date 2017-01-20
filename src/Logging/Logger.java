package Logging;

import Piece.Turn;

import java.io.*;
import java.lang.*;

public class Logger {
    public static Logger INSTANCE = new Logger();

    private FileWriter out, l;
    private int tabs;
    private String curr_path;

    private Logger() {}

    public void init(String path) {
        tabs = 0;

        if (path.equals("")) path = "chessLogs";
        else path += "/chessLogs";
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();

        curr_path = new String(path); // Copies the string

        try {
            l = new FileWriter(path + "/log.txt", false);
            out = new FileWriter(path + "/gamelog.txt", false);
            l.write("Path to JAR file: " + path + "\n");
            l.flush();
        } catch (IOException e) { System.err.println("Log Error: " + e); }

        if (out == null || l == null) { System.err.println("Unable to open file"); }
    }

    public void log (Turn t) {
        try {
            switch (t.type) {
                case 'o':
                    out.write("O-O\n");
                    break;
                case 'O':
                    out.write("O-O-O\n");
                    break;
                case 't':
                    out.write("" + (char) (t.x + 'a') + (t.y + 1) + "x" + (char) (t.x2 + 'a') + (t.y2 + 1) + "\n");
                    break;
                case 'm':
                    out.write("" + (char) (t.x + 'a') + (t.y + 1) + "-" + (char) (t.x2 + 'a') + (t.y2 + 1) + "\n");
                    break;
                case 'p':
                    out.write("" + (char) (t.x + 'a') + (t.y + 1) + "-" + (char) (t.x2 + 'a') + (t.y2 + 1) + "\n");
                    out.write("Promote to: " + t.newPiece + "\n");
                    break;
                case 'P':
                    out.write("" + (char) (t.x + 'a') + (t.y + 1) + "x" + (char) (t.x2 + 'a') + (t.y2 + 1) + "\n");
                    out.write("Promote to: " + t.newPiece + "\n");
                    break;
                default:
                    break;
            }
            out.flush();
        } catch (IOException e) { System.err.println("ERROR: " + e); }
        catch (NullPointerException e) {}
    }

    public void log(String msg) {
        try {
            for(int i = 0; i < tabs; ++i) l.write("\t");
            l.write(msg + "\n");
            l.flush();
        } catch (IOException e) { System.err.println("Log Error: " + e); }
        catch (NullPointerException e) {}
        //System.out.println(msg);
    }

    public void log(String msg, boolean entered) {
        if (!entered) tabs--;

        try {
            for(int i = 0; i < tabs; ++i) l.write("\t");
            l.write(msg + "\n");
            l.flush();
        } catch (IOException e) { System.err.println("Log Error: " + e); }
        catch (NullPointerException e) {}

        if (entered) tabs++;
        //System.out.println(msg);
    }

    public void quit() {
        try {
            if (out != null) out.close();
            if (l != null) l.close();
        } catch (IOException e) { System.err.println("Log Error: " + e); }
    }

    public String getCurr_path() { return curr_path; }
}