package Network;

import java.io.*;
import java.net.*;

/**
 * Created by yury on 01.12.16.
 */
public class Server extends Net{
    Socket s;
    private ServerSocket server;

    public Server(String ip) { ServerIP = ip; this.init(); }

    public void init() {
        s = null;
        try {
            ServerSocket server = new ServerSocket(8030);
            s = server.accept();
        } catch (IOException e) { System.err.println("Error: " + e); }
    }

    public void quit() {
        try {
            if (s != null) s.close();
            if (server != null) server.close();
        } catch (IOException e) { System.err.println("Error: " + e); }
    }

    public ObjectOutputStream getOutStream() throws IOException { return new ObjectOutputStream(s.getOutputStream()); }
    public ObjectInputStream getInStream() throws IOException { return new ObjectInputStream(s.getInputStream()); }
}
