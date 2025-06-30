package mg.dirk.vote_system.ui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.ui.affichage2.DistrictDeputeTable;

public class Affichage2 extends JPanel {
    private AppContext context;
    private DistrictDeputeTable districtDeputeTable;

    public void setContext(AppContext context) {
        this.context = context;
    }

    public AppContext getContext() {
        return context;
    }

    public void setDistrictDeputeTable(DistrictDeputeTable districtDeputeTable) {
        this.districtDeputeTable = districtDeputeTable;
    }

    public DistrictDeputeTable getDistrictDeputeTable() {
        return districtDeputeTable;
    }

    public void initUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel title = new JPanel();
        title.add(new JLabel("Listes des depute elu"));
        this.add(title);

        JScrollPane table;
        if (this.getDistrictDeputeTable() != null) {
            table = new JScrollPane(this.getDistrictDeputeTable());
        } else {
            table = new JScrollPane(new PlacehoderTabContent());
        }
        // table.setLayout(new BoxLayout(table, BoxLayout.X_AXIS));

        this.add(table);
    }

    public void refreshTable() {
        if (this.getDistrictDeputeTable() != null) {
            this.getDistrictDeputeTable().refresh();
        }
    }

    public Affichage2(AppContext context) {
        this.setContext(context);
        this.setDistrictDeputeTable(new DistrictDeputeTable(context));
        this.initUI();
    }
}
