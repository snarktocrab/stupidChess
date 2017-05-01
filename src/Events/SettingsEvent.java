package Events;

import java.io.ObjectInputStream;
import java.util.EventObject;

/**
 * Created by yury on 27.04.17.
 */
public class SettingsEvent extends EventObject {
    private Settings settings;

    public SettingsEvent(Object _source, Settings s) {
        super(_source);
        settings = new Settings(s);
    }

    public Settings getSettings() { return settings; }
}
