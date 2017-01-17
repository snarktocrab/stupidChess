package Network;

import Piece.Turn;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class Server extends Net{
    public static Server INSTANCE = new Server();

    private Socket s;
    private ServerSocket server;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void init(String ip) {
        logger.log("Initializing server...", true);
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
            logger.log("Client was successfully initialized!", false);
        } catch (IOException e) { System.err.println("Error: " + e); }
    }

    public void quit() {
        try {
            if (s != null) s.close();
            if (server != null) server.close();
        } catch (IOException e) { System.err.println("Error: " + e); }
    }

    public String getIP() {
        logger.log("Getting IP address...", true);
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if (i.getHostAddress().contains("192.168")) {
                        logger.log("Got it!", false);
                        return i.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) { return null; }
        return null;
    }

    public ObjectOutputStream getOutStream() { return out; }
    public ObjectInputStream getInStream() { return in; }

    public void sendTurn(Turn t) {
        logger.log("Preparing to send turn...", true);
        try {
            out.writeObject(t);
            out.flush();
            logger.log("Turn was successfully sent!", false);
        } catch (IOException e) { System.err.println("Error: " + e); }
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
