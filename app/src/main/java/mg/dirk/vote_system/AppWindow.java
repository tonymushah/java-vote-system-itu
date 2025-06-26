package mg.dirk.vote_system;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import mg.dirk.vote_system.ui.PlacehoderTabContent;

public class AppWindow extends JFrame {
    private AppContext appContext;
    public static final Dimension defaultDimension = new Dimension(800, 600);

    public AppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    private void initFrame() {

        this.setPreferredSize(defaultDimension);
        this.setSize(defaultDimension);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("vote-system-itu");
    }

    private void setUi() {
        Container container = this.getContentPane();
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Votez", new PlacehoderTabContent());
        tabs.addTab("Classement", new PlacehoderTabContent());
        container.add(tabs);
    }

    private void makeVisible() {
        this.setVisible(true);
    }

    public AppWindow() {
        super();
        this.setAppContext(new AppContext());
        initFrame();
        this.setUi();
        this.makeVisible();
    }
}
