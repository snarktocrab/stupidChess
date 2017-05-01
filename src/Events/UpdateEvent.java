package Events;

import java.util.EventObject;
import java.util.Objects;

/**
 * Created by daniel on 23.04.17.
 */
public class UpdateEvent extends EventObject {
    public UpdateEvent(Object source) {
        super(source);
    }
}
