package Network;

import java.io.*;
import java.net.*;

import Logging.Logger;
import Piece.Turn;

public class Client implements Net{
    public static Client INSTANCE = new Client();

    private Socket s;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String ServerIP;
    private Logger logger = Logger.INSTANCE;

    public void init(String ip) {
        logger.log("Initializing client...", true);
        ServerIP = ip;
        try {
            s = new Socket(InetAddress.getByName(ServerIP), 8030);
            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());
            logger.log("Client was successfully initialized!", false);
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
        logger.log("Preparing to send turn...", true);
        try {
            out.writeObject(t);
            out.flush();
        } catch (IOException e) { System.err.println("Error: " + e); }
        logger.log("Turn was successfully sent!", false);
    }

    public Turn receiveTurn() {
        logger.log("Waiting for turn...", true);
        try {
            Turn t = (Turn)in.readObject();
            logger.log("Turn was collected!", false);
            return t;
        } catch (IOException e) { System.err.println("Error: " + e); }
        catch (ClassNotFoundException e) { System.err.println("Class Error: " + e); }
        return null;
    }
}
