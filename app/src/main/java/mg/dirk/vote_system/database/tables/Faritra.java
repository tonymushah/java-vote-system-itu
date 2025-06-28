package mg.dirk.vote_system.database.tables;

import mg.dirk.vote_system.database.annotations.ForeignKey;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;

@Table(file = "data/faritra.csv")
public class Faritra {
    private int id;
    private String name;
    private int faritany;

    @ForeignKey(targetClass = Faritany.class)
    public int getFaritany() {
        return faritany;
    }

    @PrimaryKey
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setFaritany(int faritany) {
        this.faritany = faritany;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faritra() {
        this.setFaritany(0);
        this.setName(new String());
        this.setId(0);
    }

    @Override
    public String toString() {
        return String.format("%d - %s", this.getId(), this.getName());
    }
}
