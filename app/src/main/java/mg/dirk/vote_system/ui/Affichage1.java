package mg.dirk.vote_system.ui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.ui.affichage1.ButtonInsertListener;
import mg.dirk.vote_system.ui.selections.CSelector;

public class Affichage1 extends CSelector {

    private JTextField nombre;
    private Affichage2 affichage2;

    public void setAffichage2(Affichage2 affichage2) {
        this.affichage2 = affichage2;
    }

    public Affichage2 getAffichage2() {
        return affichage2;
    }

    public void setNombre(JTextField nombre) {
        this.nombre = nombre;
    }

    public JTextField getNombre() {
        return nombre;
    }

    @Override
    public void initComboboxes() {
        super.initComboboxes();
        JTextField nombre = new JTextField();
        nombre.setPreferredSize(new Dimension(150, 24));
        this.setNombre(nombre);
    }

    @Override
    public void initUI() {
        super.initUI();

        // Nombre panel
        JPanel nombrePanel = new JPanel();
        nombrePanel.add(new JLabel("Nombre de vote:"));
        nombrePanel.add(this.getNombre());
        this.add(nombrePanel);

        // Button import
        JPanel buttonInsertPanel = new JPanel();
        JButton button = new JButton("Ajouter");
        button.addActionListener(new ButtonInsertListener(this));
        buttonInsertPanel.add(button);
        this.add(buttonInsertPanel);

    }

    public void triggerInsertEvent() {
        System.out.println("Insert vote");
        if (this.getAffichage2() != null) {
            this.getAffichage2().refreshTable();
        }
    }

    public Affichage1(AppContext context) {
        super(context);
    }

}
