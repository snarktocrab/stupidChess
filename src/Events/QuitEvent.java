package Events;

import java.util.EventObject;

/**
 * Created by yury on 12.06.17.
 */
public class QuitEvent extends EventObject {
    public QuitEvent(Object source) {
        super(source);
    }
}
