package mg.dirk.vote_system.ui;

import javax.swing.JPanel;

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

    }

    public void refreshTable() {
        if (this.getDistrictDeputeTable() != null) {
            this.getDistrictDeputeTable().refresh();
        }
    }

    public Affichage2(AppContext context) {

    }
}
