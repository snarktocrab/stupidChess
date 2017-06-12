package Events;

import java.util.EventListener;

/**
 * Created by yury on 12.06.17.
 */
public interface QuitResetListener extends EventListener {
    void reset(ResetEvent e);
    void quit(QuitEvent e);
}
