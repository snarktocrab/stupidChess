package Events;

import java.io.ObjectInputStream;
import java.util.EventObject;

/**
 * Created by yury on 27.04.17.
 */
public class SettingsEvent extends EventObject {
    private boolean enableAutosave, enableHighlighting;
    private int numFiles, gapTurns;

    public SettingsEvent(Object _source, boolean autosave, int nFiles, int gTurns, boolean highlighting) {
        super(_source);
        enableAutosave = autosave;
        numFiles = nFiles;
        gapTurns = gTurns;
        enableHighlighting = highlighting;
    }

    public boolean isAutosaveEnabled() { return enableAutosave; }
    public int getNumFiles() { return numFiles; }
    public int getGapTurns() { return gapTurns; }
    public boolean isHighlightingEnabled() { return enableHighlighting; }
}
