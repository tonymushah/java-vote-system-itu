package mg.dirk.vote_system.ui;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.AppWindow;

public class Affichage2Test extends JFrame {
    private Affichage2 affichage;

    public void setAffichage(Affichage2 affichage) {
        this.affichage = affichage;
    }

    public Affichage2 getAffichage() {
        return affichage;
    }

    private void setUi() {
        Container container = this.getContentPane();
        container.add(this.getAffichage());
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

    public Affichage2Test(AppContext context) {
        super();
        this.setAffichage(new Affichage2(context));
        this.initFrame();
        this.setUi();
        this.makeVisible();
    }

    public static void main(String[] args) {
        new Affichage2Test(new AppContext());
    }
}
