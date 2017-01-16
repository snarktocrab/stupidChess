package View;

import javax.swing.*;

/**
 * Created by yury on 23.11.16.
 */
public interface View {
    void update();
    void checkHandler();
    void mateHandler();
    String promotionHandler();
    void waitHandler();
    String netPrompt();
    String  clientPrompt();
    void serverPrompt(String ip);
    void startHandler();
    boolean colourPrompt();

    JPanel getSettingsPanel();
    ChessPanel getChessPanel();
}
