package mg.dirk.vote_system.database;

import mg.dirk.vote_system.database.annotations.ForeignKey;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;

@Table(file = "data/test/test-foreign.csv")
public class TestTableForeign {
    private int id;
    private String name;
    private int test_table;

    @PrimaryKey
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ForeignKey(targetClass = TestTable.class)
    public int getTest_table() {
        return test_table;
    }

    public void setTest_table(int test_table) {
        this.test_table = test_table;
    }

    public TestTableForeign() {
        this.setId(0);
        this.setName(new String());
        this.setTest_table(0);
    }

    public TestTableForeign(int id, String name, int test_table) {
        this.setId(id);
        this.setName(name);
        this.setTest_table(test_table);
    }

}
