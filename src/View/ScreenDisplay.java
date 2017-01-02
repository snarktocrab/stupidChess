package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
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
        boardPane.setVisible(false);
        contentPane.add(settingsPane);
        settingsPane.setBounds(0, 0, width, height);
        settingsPane.setVisible(true);

        this.setVisible(true);
    }

    public void update() {
        // Activate this to monitor the state of every board piece
        /*for (int i = 0; i < chessboard.pieces.length; ++i) {
            Piece p = chessboard.pieces[i];
            System.out.println(p.getX() + " " + p.getY() + " " + p.getType() + " " + p.isAlive());
        }*/
        //System.out.println("turn");

        boardPane.resetBoard();

        for (int i = 0; i < 32; ++i) {
            Piece p = chessboard.getPiece(i).get();
            if (!p.isAlive()) continue;
            boardPane.drawFigure(p.getType(), p.getColour(), p.getX(), p.getY());
        }

        boardPane.repaint();
    }

    public void checkHandler() {
        //TODO: Add code here
    }

    public void mateHandler() {
        //TODO: Add code here
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

    public JPanel getSettingsPanel() { return settingsPane; }
    public ChessPanel getChessPanel() { return boardPane; }
}

class ChessPanel extends JPanel {
    private BufferedImage currentBoard;
    private BufferedImage boardImg;
    private BufferedImage[] figuresImg = new BufferedImage[12];

    private Graphics g;

    private static final int tileWidth = 60, tileHeight = 60;
    private static final Point startPoint = new Point(27, 27);

    public ChessPanel() {
        try {
            boardImg = ImageIO.read(new File("images/board.jpg"));
        } catch (IOException e) {}

        loadImages();
    }

    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        g.drawImage(currentBoard, 0, 0, null);
    }

    public void resetBoard() {
        ColorModel cm = boardImg.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = boardImg.copyData(null);
        currentBoard = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void drawFigure(char type, boolean colour, int x, int y) {
        int startX = startPoint.x + tileWidth * x + x, startY = startPoint.y + tileHeight * (7 - y) + (7 - y);

        if (g == null) g = getGraphics();
        Graphics2D g2 = currentBoard.createGraphics();

        switch (type) {
            case 'K':
                if (colour) g2.drawImage(figuresImg[10], startX, startY, null);
                else g2.drawImage(figuresImg[11], startX, startY, null);
                break;
            case 'N':
                if (colour) g2.drawImage(figuresImg[4], startX, startY, null);
                else g2.drawImage(figuresImg[5], startX, startY, null);
                break;
            case 'B':
                if (colour) g2.drawImage(figuresImg[6], startX, startY, null);
                else g2.drawImage(figuresImg[7], startX, startY, null);
                break;
            case 'p':
                if (colour) g2.drawImage(figuresImg[0], startX, startY, null);
                else g2.drawImage(figuresImg[1], startX, startY, null);
                break;
            case 'Q':
                if (colour) g2.drawImage(figuresImg[8], startX, startY, null);
                else g2.drawImage(figuresImg[9], startX, startY, null);
                break;
            case 'R':
                if (colour) g2.drawImage(figuresImg[2], startX, startY, null);
                else g2.drawImage(figuresImg[3], startX, startY, null);
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