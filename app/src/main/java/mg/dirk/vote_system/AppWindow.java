package mg.dirk.vote_system;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class AppWindow extends JFrame {
    private AppContext appContext;
    public static final Dimension defaultDimension = new Dimension(800, 600);

    public AppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    private void start() {
        this.setAppContext(new AppContext());
        this.setPreferredSize(defaultDimension);
        this.setSize(defaultDimension);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("vote-system-itu");
        this.setVisible(true);
    }

    public AppWindow() {
        super();
        start();
    }
}
