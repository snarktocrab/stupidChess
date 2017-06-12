package Events;

import java.util.EventListener;

/**
 * Created by daniel on 23.04.17.
 */
public interface GameEventListener extends EventListener {
    void updateDisplay(UpdateEvent e);
    void check(CheckEvent e);
    boolean mate(MateEvent e);
    void promotion(PromotionEvent e);
}
