package Events;

import java.util.EventObject;

/**
 * Created by yury on 04.05.17.
 */
public class LogEvent extends EventObject {
    private String message;
    Exception ex;

    public LogEvent(Object o, String message) {
        super(o);
        this.message = message;
        ex = null;
    }

    public LogEvent(Object o, String message, Exception ex) {
        super(o);
        this.message = message;
        this.ex = ex;
    }

    public String getMessage() { return message; }
    public Exception getEx() { return ex; }
}
