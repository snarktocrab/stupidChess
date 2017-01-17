package Logging;

import Piece.Turn;

import java.io.*;
import java.lang.*;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Logger {
    public static Logger INSTANCE = new Logger();

    private FileWriter out, l;
    private int tabs;

    public void init(String path) {
        tabs = 0;

        /*Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH) + 1, day = calendar.get(Calendar.DAY_OF_MONTH),
                hour = calendar.get(Calendar.HOUR_OF_DAY), min = calendar.get(Calendar.MINUTE);

        String fileName = year + "-" + (month / 10) + (month % 10) + "-" + (day / 10) + (day % 10) + "_" +
                (hour / 10) + (hour % 10) + ":" + (min / 10) + (min % 10) + ".txt";

        path = path + "/logs/" + fileName;*/

        try {
            //l = new FileWriter(path, false);
            out = new FileWriter("gamelog.txt", false);
        } catch (IOException e) { System.err.println("Log Error: " + e); }

        //if (out == null || l == null) { System.err.println("Unable to open file"); }
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
        /*try {
            for(int i = 0; i < tabs; ++i) l.write("\t");
            l.write(msg + "\n");
            l.flush();
        } catch (IOException e) { System.err.println("Log Error: " + e); }
        catch (NullPointerException e) {}*/
        System.out.println(msg);
    }

    public void log(String msg, boolean entered) {
        /*if (!entered) tabs--;

        try {
            for(int i = 0; i < tabs; ++i) l.write("\t");
            l.write(msg + "\n");
            l.flush();
        } catch (IOException e) { System.err.println("Log Error: " + e); }
        catch (NullPointerException e) {}

        if (entered) tabs++;*/
        System.out.println(msg);
    }

    public void quit() {
        try {
            if (out != null) out.close();
            if (l != null) l.close();
        } catch (IOException e) { System.err.println("Log Error: " + e); }
    }
}