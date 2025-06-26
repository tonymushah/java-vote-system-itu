package mg.dirk.vote_system.database;

import java.util.HashMap;
import java.util.List;

public class TinyDB {
    private HashMap<Class<? extends Object>, List<Object>> tables;

    public HashMap<Class<? extends Object>, List<Object>> getTables() {
        return tables;
    }

    public void setTables(HashMap<Class<? extends Object>, List<Object>> tables) {
        this.tables = tables;
    }

    public TinyDB() {
        this.setTables(new HashMap<Class<? extends Object>, List<Object>>());
    }

    public void insertClass(Class<? extends Object> class1) {
    }
}
