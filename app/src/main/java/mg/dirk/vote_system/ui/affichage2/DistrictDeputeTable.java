package mg.dirk.vote_system.ui.affichage2;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JTable;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;
import mg.dirk.vote_system.database.tables.District;
import mg.dirk.vote_system.database.tables.helpers.DistrictDepute;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.helpers.GenericTableModel;

public class DistrictDeputeTable extends JTable {
    private AppContext context;

    public void setContext(AppContext context) {
        this.context = context;
    }

    public AppContext getContext() {
        return context;
    }

    public DistrictDeputeTable(AppContext context) {
        this.setContext(context);
        this.init();
    }

    private void init() {
        try {
            List<DistrictDepute> deputes = District.getElecteDeputes(context.getDb());
            this.setModel(new GenericTableModel(deputes.toArray(new DistrictDepute[deputes.size()])));
        } catch (InvalidClassException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | NoRowsException | UndefinedPrimaryKeyException | UndefinedTableAnnotationException
                | InvalidForeignKeyTarget | ReferredValueNotFoundException e) {
            MessageBox.error(e);
        }
    }

    public void refresh() {
        this.removeAll();
        this.init();
    }
}
