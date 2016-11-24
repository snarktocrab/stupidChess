import Controller.BasicController;
import Controller.Controller;
import Piece.Board;

/**
 * Created by daniel on 22.11.16.
 */
public class Main {
    public static void main(String[] args) {
        while (BasicController.INSTANCE.isRunning()) {
            BasicController.INSTANCE.getMove();
        }
    }
}
