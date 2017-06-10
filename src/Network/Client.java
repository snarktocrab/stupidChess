package Network;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

import Events.LogEvent;
import Events.LogEventListener;
import Logging.Logger;
import Piece.Turn;

public class Client implements Net{
    public static Client INSTANCE = new Client();

    private Socket s;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String ServerIP;
    private LinkedList<LogEventListener> logListeners = new LinkedList<>();

    public void init(String ip) {
        throwLogEvent("Initializing client...", true);
        ServerIP = ip;
        try {
            s = new Socket(InetAddress.getByName(ServerIP), 8030);
            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());
            throwLogEvent("Client has been successfully initialized!", false);
        } catch (UnknownHostException e) { System.err.println("Host error: " + e); throwLogEvent("Host error: ", e);}
        catch (IOException e) { System.err.println("InitError: " + e); throwLogEvent("InitError: ", e);}
    }

    public void addLogEventListener(LogEventListener listener) { logListeners.add(listener); }

    public void quit() {
        try {
            if (s != null) s.close();
        } catch (IOException e) { System.err.println("QuitError: " + e); throwLogEvent("QuitError: ", e);}
    }

    public String getIP() { return null; }

    public ObjectOutputStream getOutStream() { return out; }
    public ObjectInputStream getInStream() { return in; }

    public void sendTurn(Turn t) {
        throwLogEvent("Preparing to send turn...", true);
        try {
            out.writeObject(t);
            out.flush();
        } catch (IOException e) { System.err.println("Error while sending turn: " + e); throwLogEvent("Error while sending turn: ", e);}
        throwLogEvent("Turn has been successfully sent!", false);
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
