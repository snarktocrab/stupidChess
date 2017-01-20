package Events;

import java.util.EventObject;

/**
 * Created by yury on 20.01.17.
 */
public class SaveLoadEvent extends EventObject {
    private char type;
    private String filename;

    public SaveLoadEvent(Object source, char _type, String _filename) { // 'l' - Load, 's' - Save
        super(source);
        type = _type;
        filename = _filename;
    }

    public char getType() { return type; }
    public String getFilename() { return filename; }
}
