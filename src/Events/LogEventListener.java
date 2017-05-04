package Events;

import java.util.EventListener;

/**
 * Created by yury on 04.05.17.
 */
public interface LogEventListener extends EventListener {
    void logMessage(LogEvent e);
    void logFunction(LogEvent e, boolean b);
    void logError(LogEvent e);
    String getCurrentPath();
}
