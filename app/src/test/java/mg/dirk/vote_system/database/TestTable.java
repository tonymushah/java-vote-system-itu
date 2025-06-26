package mg.dirk.vote_system.database;

import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;

@Table(file = "data/test/test-table.csv")
public class TestTable {
    private int id;
    private String name;

    @PrimaryKey
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestTable() {

    }

    public TestTable(int id, String name) {
        this.setId(id);
        this.setName(name);
    }
}
