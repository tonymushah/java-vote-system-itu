package mg.dirk.vote_system.ui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import mg.dirk.vote_system.ui.message_box.OkButtonListener;

public class MessageBox extends JFrame {
    public static final Dimension messageBoxDimension = new Dimension(500, 125);

    protected MessageBox(String title, String description) {
        this.setTitle(title);
        this.setPreferredSize(messageBoxDimension);
        this.setSize(messageBoxDimension);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);
        panel.add(titlePanel);

        if (description != null) {
            JPanel desciptionPanel = new JPanel();
            for (String string : description.split(" ")) {
                JLabel descriptionJLabel = new JLabel(string);
                desciptionPanel.add(descriptionJLabel);
            }
            panel.add(desciptionPanel);
        }

        JPanel okPanel = new JPanel();
        JButton okButton = new JButton("Ok");
        okButton.setSize(new Dimension(75, 16));
        okButton.addActionListener(new OkButtonListener(this));
        okPanel.add(okButton);
        panel.add(okPanel);

        this.getContentPane().add(panel);

        this.setVisible(true);
    }

    public static void message(String title, String description) {
        new MessageBox(title, description);
    }

    public static void error(Exception exception) {
        exception.printStackTrace();
        new MessageBox("Exception", exception.getMessage());
    }
}
