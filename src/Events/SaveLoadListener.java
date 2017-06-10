package Events;

import java.util.*;

/**
 * Created by yury on 20.01.17.
 */
public interface SaveLoadListener extends EventListener {
    void save(SaveLoadEvent e);
    void load(SaveLoadEvent e);
    void loadNet();
}
