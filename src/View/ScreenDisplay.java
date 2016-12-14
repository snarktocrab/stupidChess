package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by yury on 13.12.16.
 */
public class ScreenDisplay extends JFrame implements View {
    private int width = 400, height = 400 + 30;
    public ChessPanel boardPane;
    public JPanel settingsPane;

    // Singleton
    public static ScreenDisplay INSTANCE = new ScreenDisplay();
    private ScreenDisplay() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();

        contentPane.setBounds(0, 0, width, height);
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setLayout(null);

        setContentPane(contentPane);
        setBounds(20, 20, width, height);

        boardPane = new ChessPanel();
        settingsPane = new JPanel();
        contentPane.add(boardPane);
        boardPane.setBounds(0, 0, width, height);
        contentPane.add(settingsPane);
        settingsPane.setVisible(false);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
        this.setVisible(true);

        update();
    }

    public void update() {
        boardPane.repaint();
    }

    public void checkHandler() {
        // TODO: Add code here
    }

    public void mateHandler() {
        // TODO: Add code here
    }

    public void promotionHandler() {
        // TODO: Add code here
    }

    public void waitHandler() {
        // TODO: Add code here
    }

    public void netPrompt() {
        // TODO: Add code here
        // Here we will use settingsPane
    }

    public void clientPrompt() {
        // TODO: Add code here
    }

    public void serverPrompt(String ip) {
        // TODO: Add code here
    }

    public void startHandler() {
        // TODO: Add code here
    }
}

class ChessPanel extends JPanel {
    private BufferedImage[] images = new BufferedImage[13];

    public ChessPanel() {
        try {
            images[0] = ImageIO.read(new File("images/board.jpg"));
        } catch (IOException e) {}
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(images[0], 0, 0, null);
    }
}
