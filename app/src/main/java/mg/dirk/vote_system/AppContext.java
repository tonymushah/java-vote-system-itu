package mg.dirk.vote_system;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.tables.BureauDeVote;
import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.database.tables.District;
import mg.dirk.vote_system.database.tables.Faritany;
import mg.dirk.vote_system.database.tables.Faritra;
import mg.dirk.vote_system.database.tables.GroupePolitique;
import mg.dirk.vote_system.database.tables.Vote;

public class AppContext {
    private DirkDB db;

    public DirkDB getDb() {
        return db;
    }

    public void setDb(DirkDB db) {
        this.db = db;
    }

    public AppContext() {
        this(new DirkDB(BureauDeVote.class, Depute.class, District.class, Faritany.class, Faritra.class,
                GroupePolitique.class, Vote.class));
    }

    public AppContext(DirkDB db) {
        this.setDb(db);
    }

}
