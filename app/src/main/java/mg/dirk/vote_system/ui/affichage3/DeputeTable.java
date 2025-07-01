package mg.dirk.vote_system.ui.affichage3;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.ui.helpers.GenericTableModel;

public class DeputeTable extends JTable {
    private List<Depute> deputes;
    private AppContext appContext;

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public List<Depute> getDeputes() {
        return deputes;
    }

    public void setDeputes(List<Depute> deputes) {
        this.deputes = deputes;
        DirkDB db = this.getAppContext().getDb();
        if (db == null) {
            this.setModel(new GenericTableModel(deputes.toArray(new Depute[deputes.size()])));
        } else {
            try {
                List<DeputeTableRecord> deputeTableRecords = new ArrayList<>();
                for (Depute depute : this.deputes) {
                    deputeTableRecords.add(new DeputeTableRecord(depute.getNom(),
                            depute.getGroupePolitique(db).getNom(), depute.getVotesTotal(db)));
                }
                this.setModel(new GenericTableModel(
                        deputeTableRecords.toArray(new DeputeTableRecord[deputeTableRecords.size()])));
            } catch (Exception e) {
                this.setModel(new GenericTableModel(deputes.toArray(new Depute[deputes.size()])));
            }
        }

    }

    public DeputeTable(AppContext context) {
        super();
        this.setAppContext(context);
        this.setDeputes(new ArrayList<>());
    }

    public DeputeTable(AppContext context, List<Depute> deputes) {
        this(context);
        this.setDeputes(deputes);
    }

}
