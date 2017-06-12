package View;

import Events.*;

import javax.swing.*;

/**
 * Created by yury on 23.11.16.
 */
public interface View {
    void update();
    void checkHandler();
    boolean mateHandler();
    String promotionHandler();
    void waitHandler();
    String netPrompt();
    String  clientPrompt();
    void serverPrompt(String ip);
    void startHandler();
    boolean colourPrompt();

    JPanel getSettingsPanel();
    ChessPanel getChessPanel();

    void addSettingsEventListener(SettingsEventListener listener);
    void throwSettingsEvent(Settings s);
    void addLogEventListener(LogEventListener listener);
    void addSaveLoadListener(SaveLoadListener listener);
    void addQuitResetListener(QuitResetListener listener);
    void throwResetEvent();
    void throwQuitEvent();
}
