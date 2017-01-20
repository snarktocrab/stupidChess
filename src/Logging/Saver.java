package Logging;

import Events.*;
import Piece.Board;
import Piece.Piece;
import Piece.Turn;

import java.io.*;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by yury on 20.01.17.
 */
public class Saver {
    public static Saver INSTANCE = new Saver();

    private Logger logger = Logger.INSTANCE;
    private LinkedList<SaveLoadListener> listeners = new LinkedList<>();
    private boolean isNetworkActive;

    private Saver() {}

    public void addSaveLoadListener(SaveLoadListener saveLoadListener) {
        listeners.add(saveLoadListener);
    }

    public void removeSaveLoadListener(SaveLoadListener sll) {
        listeners.remove(sll);
    }

    public void save(String filename) {
        logger.log("Saving game...", true);
        String path = logger.getCurr_path() + "/saves";
        File folder = new File(path);
        if (!folder.exists()) folder.mkdir();

        Board b = Board.INSTANCE;

        try {
            FileOutputStream fs = new FileOutputStream(logger.getCurr_path() + "/saves/" + filename + ".sav");
            ObjectOutputStream sv = new ObjectOutputStream(fs);
            sv.writeObject(b.getTurn());
            for (int i = 0; i < 32; ++i) {
                sv.writeObject(b.getPiece(i).get());
            }
            sv.writeObject(b.log);
        } catch (IOException e) { System.err.println("Save Error: " + e); }
        if (isNetworkActive) throwEvent(new SaveLoadEvent(this, 's', filename));
        logger.log("Successfully!", false);
    }

    public void load(String filename) {
        logger.log("Loading game...", true);
        Board.INSTANCE.setSelectedFigure(null);
        String path = logger.getCurr_path() + "/saves";

        Board b = Board.INSTANCE;

        path += "/" + filename;
        try {
            FileInputStream fs = new FileInputStream(path);
            ObjectInputStream ld = new ObjectInputStream(fs);
            b.setTurn((boolean)ld.readObject());
            Piece p;
            for (int i = 0; i < 32; ++i) {
                p = (Piece)ld.readObject();
                b.getPiece(i).get().changeParams(p);
            }
            b.log = (Stack<Turn>)ld.readObject();
        } catch (IOException e) { System.err.println("Load Error: " + e); }
        catch (ClassNotFoundException e) { System.err.println("Class not found Error: " + e); }
        if (isNetworkActive) throwEvent(new SaveLoadEvent(this, 'l', filename));
        logger.log("Successfully", false);
    }

    public void setNetworkActivity(boolean activity) { isNetworkActive = activity; }

    private void throwEvent(SaveLoadEvent event) {
        for (SaveLoadListener next : listeners) {
            if (event.getType() == 's')
                next.saveOpponent(event);
            else if (event.getType() == 'l')
                next.loadOpponent(event);
        }
    }
}