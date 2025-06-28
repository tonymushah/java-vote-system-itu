package mg.dirk.vote_system.database;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;

public class DBTest {
    private DirkDB db;

    public DBTest() {
        this.db = new DirkDB();
    }

    @Test
    public void simpleTest() throws NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            UndefinedTableAnnotationException, UndefinedPrimaryKeyException, IOException, ParseException {
        this.db.importClass(TestTable.class);
    }

    @Test
    public void insertTest() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            SecurityException, UndefinedTableAnnotationException, UndefinedPrimaryKeyException, IOException {
        this.db.insertBatch(new TestTable(1, "new shit was added"), new TestTable(2, "Hi!"));
    }

    @Test
    public void foreignKey()
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
            UndefinedTableAnnotationException, UndefinedPrimaryKeyException, IOException, NoRowsException,
            InvalidForeignKeyTarget, ReferredValueNotFoundException {
        this.db.clear();

        TestTable[] testTables = { new TestTable(1, "First"), new TestTable(2, "Second") };
        this.db.insertBatchNoSave((Object[]) testTables);
        List<TestTable> ts_db = this.db.select(TestTable.class);
        assertEquals(testTables.length, ts_db.size());

        TestTableForeign[] testTableForeigns = { new TestTableForeign(1, "Some", 1),
                new TestTableForeign(2, "String", 1), new TestTableForeign(3, "Sdsad", 1),
                new TestTableForeign(4, "dads", 2), new TestTableForeign(5, "adsadas", 2) };
        this.db.insertBatchNoSave((Object[]) testTableForeigns);
        List<TestTableForeign> tsf_db = this.db.select(TestTableForeign.class);
        assertEquals(testTableForeigns.length, tsf_db.size());

        List<TestTableForeign> ttf1 = this.db.get_relationships(testTables[0], TestTableForeign.class, "getTest_table");
        assertEquals(ttf1.size(), 3);

        TestTable ref = this.db.get_reference(testTableForeigns[1], TestTable.class, "getTest_table");
        assertEquals(testTables[0], ref);

        this.db.saveToFile();
    }
}
