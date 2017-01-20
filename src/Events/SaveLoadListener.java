package Events;

import java.util.*;

/**
 * Created by yury on 20.01.17.
 */
public interface SaveLoadListener extends EventListener {
    void saveOpponent(SaveLoadEvent event);
    void loadOpponent(SaveLoadEvent event);
}
