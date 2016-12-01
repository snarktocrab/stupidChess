package Network;

import java.io.*;
import java.net.*;

/**
 * Created by yury on 01.12.16.
 */
public class Client extends Net{
    public static Client INSTANCE = new Client();

    Socket s;

    public void init(String ip) {
        ServerIP = ip;
        try {
            s = new Socket(InetAddress.getByName(ServerIP), 8030);

        } catch (UnknownHostException e) { System.err.println("Host error: " + e); }
        catch (IOException e) { System.err.println("Error: " + e); }
    }

    public void quit() {
        try {
            if (s != null) s.close();
        } catch (IOException e) { System.err.println("Error: " + e); }
    }

    public ObjectOutputStream getOutStream() throws IOException { return new ObjectOutputStream(s.getOutputStream()); }
    public ObjectInputStream getInStream() throws IOException { return new ObjectInputStream(s.getInputStream()); }
}
