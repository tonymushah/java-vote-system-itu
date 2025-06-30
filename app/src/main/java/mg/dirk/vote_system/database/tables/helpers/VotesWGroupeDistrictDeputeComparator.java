package mg.dirk.vote_system.database.tables.helpers;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;
import mg.dirk.vote_system.database.tables.GroupePolitique;

public class VotesWGroupeDistrictDeputeComparator extends VotesDistrictDeputeComparator {
    private DirkDB db;

    public void setDb(DirkDB db) {
        this.db = db;
    }

    public DirkDB getDb() {
        return db;
    }

    public VotesWGroupeDistrictDeputeComparator(DirkDB db) {
        super();
        this.setDb(db);
    }

    @Override
    public int compare(DistrictDepute arg0, DistrictDepute arg1) {

        try {
            GroupePolitique arg0GroupePolitique = arg0.getDepute().getGroupePolitique(this.getDb());
            GroupePolitique arg1GroupePolitique = arg1.getDepute().getGroupePolitique(this.getDb());
            return arg0GroupePolitique.getDate_de_creation().compareTo(arg1GroupePolitique.getDate_de_creation());
        } catch (InvalidClassException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | UndefinedPrimaryKeyException | UndefinedTableAnnotationException | InvalidForeignKeyTarget
                | ReferredValueNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return super.compare(arg0, arg1);
        }

    }
}
