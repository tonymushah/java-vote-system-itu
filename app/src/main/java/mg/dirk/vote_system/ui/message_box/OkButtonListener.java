package mg.dirk.vote_system.ui.message_box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class OkButtonListener implements ActionListener {
    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public OkButtonListener(JFrame frame) {
        super();
        this.setFrame(frame);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.getFrame().setVisible(false);
        this.getFrame().dispose();
    }

}
