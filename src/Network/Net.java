package Network;

import java.io.*;

import Logging.Logger;
import Piece.Turn;

/**
 * Created by yury on 01.12.16.
 */
public interface Net {
    void init(String ip);
    void quit();
    String getIP();
    ObjectInputStream getInStream();
    ObjectOutputStream getOutStream();
    void sendTurn(Turn t);
    Turn receiveTurn();
}
