package Network;

import java.io.*;
import java.net.*;
import Piece.Turn;

public class Client extends Net{
    public static Client INSTANCE = new Client();

    private Socket s;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void init(String ip) {
        ServerIP = ip;
        try {
            s = new Socket(InetAddress.getByName(ServerIP), 8030);
            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());
        } catch (UnknownHostException e) { System.err.println("Host error: " + e); }
        catch (IOException e) { System.err.println("Error: " + e); }
    }

    public void quit() {
        try {
            if (s != null) s.close();
        } catch (IOException e) { System.err.println("Error: " + e); }
    }

    public String getIP() { return null; }

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
