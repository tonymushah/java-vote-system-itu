package mg.dirk.vote_system.ui.selections;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.AppWindow;

public class ASelectorTest extends JFrame {
    private ASelector selector;

    public ASelector getSelector() {
        return selector;
    }

    public void setSelector(ASelector selector) {
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

    public ASelectorTest(AppContext context) {
        super();
        this.setSelector(new ASelector(context));
        this.initFrame();
        this.setUi();
        this.makeVisible();
    }

    public static void main(String[] args) {
        new ASelectorTest(new AppContext());
    }
}
