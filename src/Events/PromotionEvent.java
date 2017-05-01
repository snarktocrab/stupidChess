package Events;

import java.util.EventObject;

/**
 * Created by daniel on 23.04.17.
 */
public class PromotionEvent extends EventObject {
    public int id;
    public PromotionEvent(Object source, int _id) {
        super(source);
        id = _id;
    }
}
