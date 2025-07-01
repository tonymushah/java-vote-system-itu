package mg.dirk.vote_system.ui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.ui.affichage3.DeputeTable;
import mg.dirk.vote_system.ui.affichage3.RechercerButtonListener;
import mg.dirk.vote_system.ui.selections.DSelector;

public class Affichage3 extends JPanel {
    private AppContext context;
    private DeputeTable deputeTable;
    private DSelector dSelector;

    public void setDSelector(DSelector dSelector) {
        this.dSelector = dSelector;
    }

    public DSelector getDSelector() {
        return dSelector;
    }

    public void setDeputeTable(DeputeTable deputeTable) {
        this.deputeTable = deputeTable;
    }

    public DeputeTable getDeputeTable() {
        return deputeTable;
    }

    public AppContext getContext() {
        return context;
    }

    public void setContext(AppContext context) {
        this.context = context;
    }

    public void initUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton validerRecherche = new JButton("Rechercher");
        validerRecherche.setPreferredSize(new Dimension(100, 24));
        validerRecherche.addActionListener(new RechercerButtonListener(this));
        this.getDSelector().add(validerRecherche);
        this.add(this.getDSelector());

        JScrollPane table;
        if (this.getDeputeTable() != null) {
            table = new JScrollPane(this.getDeputeTable());
        } else {
            table = new JScrollPane(new PlacehoderTabContent());
        }
        // table.setLayout(new BoxLayout(table, BoxLayout.X_AXIS));
        this.add(table);
    }

    public Affichage3(AppContext context) {
        super();
        this.setContext(context);
        this.setDeputeTable(new DeputeTable());
        this.setDSelector(new DSelector(context));
        try {
            this.getDeputeTable().setDeputes(context.getDb().select(Depute.class));
        } catch (Exception e) {
            MessageBox.error(e);
        }
        this.initUI();
    }
}
