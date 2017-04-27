package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by yury on 26.04.17.
 */
public class SettingsWindow extends JFrame {
    private static int width = 300, height = 145;

    public SettingsWindow(View display) {
        super("Settings");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel contentPane = new JPanel();

        contentPane.setBounds(0, 0, width, height);
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setLayout(null);

        setContentPane(contentPane);
        setBounds(200, 100, width, height);
        JPanel settingsPane = new JPanel();
        settingsPane.setBounds(0, 0, width, height);
        contentPane.add(settingsPane);

        JLabel titleAS = new JLabel("Austosave:");
        settingsPane.add(titleAS);

        ButtonGroup group = new ButtonGroup();
        JRadioButton enable = new JRadioButton("Enable", true);
        group.add(enable);
        JRadioButton disable = new JRadioButton("Disable", false);
        group.add(disable);
        settingsPane.add(enable);
        settingsPane.add(disable);

        JLabel numFiles = new JLabel("Number of files:");
        settingsPane.add(numFiles);
        JTextField num = new JTextField("3", 3);
        settingsPane.add(num);

        JLabel gapTurns = new JLabel("Time between autosaves:");
        settingsPane.add(gapTurns);
        JTextField gap = new JTextField("5", 3);
        settingsPane.add(gap);

        JLabel titleFr = new JLabel("Tiles highlighting:");
        settingsPane.add(titleFr);

        ButtonGroup group2 = new ButtonGroup();
        JRadioButton en = new JRadioButton("Enable", true);
        group2.add(en);
        JRadioButton dis = new JRadioButton("Disable", false);
        group2.add(dis);
        settingsPane.add(en);
        settingsPane.add(dis);

        JButton ok = new JButton("OK");
        settingsPane.add(ok);
        ok.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                display.throwSettingsEvent(enable.isSelected(), Integer.parseInt(num.getText()),
                        Integer.parseInt(gap.getText()), en.isSelected());
                dispose();
            }
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        settingsPane.setVisible(true);
        contentPane.setVisible(true);
        this.setVisible(true);
    }
}
