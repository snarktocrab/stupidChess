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

    public String getIP() {
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if (i.getHostAddress().contains("192.168")) return i.getHostAddress();
                }
            }
        } catch (SocketException e) { return null; }
        return null;
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
