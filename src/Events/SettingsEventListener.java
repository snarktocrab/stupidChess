package Events;

import java.util.EventListener;

/**
 * Created by yury on 27.04.17.
 */
public interface SettingsEventListener extends EventListener {
    void updateSettings(SettingsEvent e);
    Settings getCurrentSettings();
}
