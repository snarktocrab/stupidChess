package Network;

import Piece.Turn;

import java.io.*;
import java.net.*;

/**
 * Created by yury on 01.12.16.
 */
public class Server extends Net{
    public static Server INSTANCE = new Server();

    private Socket s;
    private ServerSocket server;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void init(String ip) {
        ServerIP = ip;
        s = null;
        try {
            server = new ServerSocket(8030);
            s = server.accept();
            ObjectOutputStream outt = new ObjectOutputStream(s.getOutputStream());
            out = outt;
            InputStream i = s.getInputStream();
            ObjectInputStream inn = new ObjectInputStream(i);
            in = inn;
        } catch (IOException e) { System.err.println("Error: " + e); }
    }

    public void quit() {
        try {
            if (s != null) s.close();
            if (server != null) server.close();
        } catch (IOException e) { System.err.println("Error: " + e); }
    }

    public ObjectOutputStream getOutStream() { return out; }
    public ObjectInputStream getInStream() { return in; }

    public void sendTurn(Turn t) {
        try {
            out.writeObject(t);
            out.flush();
        } catch (IOException e) { System.err.println("Error: " + e); }
    }

    public Turn receiveTurn() {
        try {
            Turn t = (Turn)in.readObject();
            return t;
        } catch (IOException e) { System.err.println("Error: " + e); }
        catch (ClassNotFoundException e) { System.err.println("Class Error: " + e); }
        return null;
    }
}
