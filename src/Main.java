import Controller.BasicController;
import Controller.Controller;

/**
 * Created by daniel on 22.11.16.
 */
public class Main {
    public static void main(String[] args) {
        Controller c = new BasicController();
        while (true) {
            c.getMove();
        }
    }
}
