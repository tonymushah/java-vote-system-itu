package mg.dirk.vote_system.ui.affichage3;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.ui.helpers.GenericTableModel;

public class DeputeTable extends JTable {
    private List<Depute> deputes;

    public List<Depute> getDeputes() {
        return deputes;
    }

    public void setDeputes(List<Depute> deputes) {
        this.deputes = deputes;
        this.setModel(new GenericTableModel(deputes.toArray(new Depute[deputes.size()])));
    }

    public DeputeTable() {
        super();
        this.setDeputes(new ArrayList<>());
    }

    public DeputeTable(List<Depute> deputes) {
        super();
        this.setDeputes(deputes);
    }

}
