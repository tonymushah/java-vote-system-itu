package mg.dirk.vote_system.ui.selections;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.AppWindow;

public class CSelectorTest extends JFrame {
    private CSelector selector;

    public CSelector getSelector() {
        return selector;
    }

    public void setSelector(CSelector selector) {
        this.selector = selector;
    }

    private void setUi() {
        Container container = this.getContentPane();
        container.add(this.getSelector());
    }

    private void initFrame() {
        this.setPreferredSize(AppWindow.defaultDimension);
        this.setSize(AppWindow.defaultDimension);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("vote-system-itu");
    }

    private void makeVisible() {
        this.setVisible(true);
    }

    public CSelectorTest(AppContext context) {
        super();
        this.setSelector(new CSelector(context));
        this.initFrame();
        this.setUi();
        this.makeVisible();
    }

    public static void main(String[] args) {
        new CSelectorTest(new AppContext());
    }
}
