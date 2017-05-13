package Events;

import java.util.EventObject;

/**
 * Created by yury on 20.01.17.
 */
public class SaveLoadEvent extends EventObject {
    private String filename;

    public SaveLoadEvent(Object source, String _filename) {
        super(source);
        filename = _filename;
    }

    public String getFilename() { return filename; }
}
