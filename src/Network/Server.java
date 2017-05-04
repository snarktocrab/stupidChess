package Network;

import Events.LogEvent;
import Events.LogEventListener;
import Logging.Logger;
import Piece.Turn;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.LinkedList;

public class Server implements Net{
    public static Server INSTANCE = new Server();

    private Socket s;
    private ServerSocket server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private LinkedList<LogEventListener> logListeners = new LinkedList<>();

    public void init(String ip) {
        throwLogEvent("Initializing server...", true);
        s = null;
        try {
            server = new ServerSocket(8030);
            s = server.accept();
            ObjectOutputStream outt = new ObjectOutputStream(s.getOutputStream());
            out = outt;
            InputStream i = s.getInputStream();
            ObjectInputStream inn = new ObjectInputStream(i);
            in = inn;
            throwLogEvent("Client was successfully initialized!", false);
        } catch (IOException e) { System.err.println("InitError: " + e); throwLogEvent("InitError: ", e);}
    }

    public void addLogEventListener(LogEventListener listener) { logListeners.add(listener); }

    public void quit() {
        try {
            if (s != null) s.close();
            if (server != null) server.close();
        } catch (IOException e) { System.err.println("QuitError: " + e); throwLogEvent("QuitError: ", e);}
    }

    public String getIP() {
        throwLogEvent("Getting IP address...", true);
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if (i.getHostAddress().startsWith("192.168")) {
                        throwLogEvent("Got it!", false);
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
        throwLogEvent("Preparing to send turn...", true);
        try {
            out.writeObject(t);
            out.flush();
            throwLogEvent("Turn has been successfully sent!", false);
        } catch (IOException e) { System.err.println("Error while sending turn: " + e); throwLogEvent("Error while sending turn: ", e);}
    }

    public Turn receiveTurn() {
        throwLogEvent("Waiting for turn...", true);
        try {
            Turn t = (Turn)in.readObject();
            throwLogEvent("Turn has been collected!", false);
            return t;
        } catch (IOException e) { System.err.println("Error while receiving turn: " + e); throwLogEvent("Error while receiving turn: ", e);}
        catch (ClassNotFoundException e) { System.err.println("Class Error: " + e); throwLogEvent("Class Error: ", e);}
        return null;
    }

    private void throwLogEvent(String msg) {
        for (LogEventListener listener : logListeners)
            listener.logMessage(new LogEvent(this, msg));
    }
    private void throwLogEvent(String msg, boolean enter) {
        for (LogEventListener listener : logListeners)
            listener.logFunction(new LogEvent(this, msg), enter);
    }
    private void throwLogEvent(String msg, Exception ex) {
        for (LogEventListener listener : logListeners)
            listener.logError(new LogEvent(this, msg, ex));
    }
}
