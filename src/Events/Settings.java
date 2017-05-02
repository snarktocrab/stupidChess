package Events;

import java.io.Serializable;

/**
 * Created by yury on 29.04.17.
 */
public class Settings implements Serializable {
    public boolean isAutosaveEnabled, isHighlightingEnabled;
    public int numFiles, gapTurns;

    public Settings(boolean as, int num, int gap, boolean highlighting) {
        isAutosaveEnabled = as;
        numFiles = num;
        gapTurns = gap;
        isHighlightingEnabled = highlighting;
    }

    public Settings(Settings s) {
        isAutosaveEnabled = s.isAutosaveEnabled;
        isHighlightingEnabled = s.isHighlightingEnabled;
        numFiles = s.numFiles;
        gapTurns = s.gapTurns;
    }
}
