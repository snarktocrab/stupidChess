package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Piece.*;

/**
 * Created by yury on 13.12.16.
 */
public class ScreenDisplay extends JFrame implements View {
    private int width = 545, height = 545 + 26;
    private ChessPanel boardPane;
    private JPanel settingsPane;

    private MessageDialog out;

    private Board chessboard = Board.INSTANCE;

    // Singleton
    public static ScreenDisplay INSTANCE = new ScreenDisplay();
    private ScreenDisplay() {
        super("Chess");
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
        // Activate this to monitor the state of every board piece
        /*for (int i = 0; i < chessboard.pieces.length; ++i) {
            Piece p = chessboard.pieces[i];
            System.out.println(p.getX() + " " + p.getY() + " " + p.getType() + " " + p.isAlive());
        }
        System.out.println(chessboard.getTurn());*/

        for (int i = 0; i < 32; ++i) {
            Piece p = chessboard.getPiece(i).get();
            if (!p.isAlive()) continue;
            boardPane.drawFigure(p.getType(), p.getColour(), p.getX(), p.getY());
        }
    }

    public void checkHandler() {
        if (out == null)
            out = new MessageDialog(this, "Check!");
        out.setMessage("Check!");
        out.setVisible(true);
    }

    public void mateHandler() {
        String s = "Checkmate!\n";
        if (!chessboard.getTurn())
            s += "White ";
        else
            s += "Black ";
        s += "wins!";

        if (out == null)
            out = new MessageDialog(this, s);
        out.setMessage(s);
        out.setVisible(true);
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
        settingsPane.setVisible(false);
        boardPane.setVisible(true);
        update();
    }
}

class ChessPanel extends JPanel {
    private BufferedImage currentBoard;
    private BufferedImage boardImg;
    private BufferedImage[] figuresImg = new BufferedImage[12];

    private Graphics g;

    public ChessPanel() {
        try {
            currentBoard = ImageIO.read(new File("images/board.jpg"));
        } catch (IOException e) {}

        loadImages();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentBoard, 0, 0, null);
    }

    public void drawFigure(char type, boolean colour, int x, int y) {
        int startX = 27 + 60 * x + x, startY = 27 + 60 * (7 - y) + (7 - y);

        if (g == null) g = getGraphics();

        switch (type) {
            case 'K':
                if (colour) g.drawImage(figuresImg[10], startX, startY, null);
                else g.drawImage(figuresImg[11], startX, startY, null);
                break;
            case 'N':
                if (colour) g.drawImage(figuresImg[4], startX, startY, null);
                else g.drawImage(figuresImg[5], startX, startY, null);
                break;
            case 'B':
                if (colour) g.drawImage(figuresImg[6], startX, startY, null);
                else g.drawImage(figuresImg[7], startX, startY, null);
                break;
            case 'p':
                if (colour) g.drawImage(figuresImg[0], startX, startY, null);
                else g.drawImage(figuresImg[1], startX, startY, null);
                break;
            case 'Q':
                if (colour) g.drawImage(figuresImg[8], startX, startY, null);
                else g.drawImage(figuresImg[9], startX, startY, null);
                break;
            case 'R':
                if (colour) g.drawImage(figuresImg[2], startX, startY, null);
                else g.drawImage(figuresImg[3], startX, startY, null);
                break;
        }
    }

    private void loadImages() {
        try {
            figuresImg[0] = ImageIO.read(new File("images/pawn-w.png"));
            figuresImg[1] = ImageIO.read(new File("images/pawn-b.png"));
            figuresImg[2] = ImageIO.read(new File("images/rook-w.png"));
            figuresImg[3] = ImageIO.read(new File("images/rook-b.png"));
            figuresImg[4] = ImageIO.read(new File("images/knight-w.png"));
            figuresImg[5] = ImageIO.read(new File("images/knight-b.png"));
            figuresImg[6] = ImageIO.read(new File("images/bishop-w.png"));
            figuresImg[7] = ImageIO.read(new File("images/bishop-b.png"));
            figuresImg[8] = ImageIO.read(new File("images/queen-w.png"));
            figuresImg[9] = ImageIO.read(new File("images/queen-b.png"));
            figuresImg[10] = ImageIO.read(new File("images/king-w.png"));
            figuresImg[11] = ImageIO.read(new File("images/king-b.png"));
        } catch (IOException e) {}
    }
}

class MessageDialog extends JDialog {
    private int w = 260, h = 160 + 26;
    private String message;

    public void setMessage(String s) { message = s; }

    public MessageDialog(JFrame owner, String s) {
        super(owner, "Message", true);

        message = s;

        add(new JLabel("<html><h1><b>" + message + "</b></h1></html>"), BorderLayout.CENTER);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        });

        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        setSize(w, h);
    }
}