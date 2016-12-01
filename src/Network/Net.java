package Network;

import java.io.*;

/**
 * Created by yury on 01.12.16.
 */
public abstract class Net {
    String ServerIP;
    public abstract void init(String ip);
    public abstract void quit();
    public abstract ObjectInputStream getInStream() throws IOException;
    public abstract ObjectOutputStream getOutStream() throws IOException;
}
