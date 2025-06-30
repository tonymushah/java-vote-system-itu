package mg.dirk.vote_system.database.tables;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.annotations.ForeignKey;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;

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

    public List<District> getDistricts(DirkDB db)
            throws InvalidClassException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            UndefinedPrimaryKeyException, UndefinedTableAnnotationException, InvalidForeignKeyTarget {
        return db.get_relationships(this, District.class, "getFaritra");
    }

    public List<Depute> getDeputes(DirkDB db) throws InvalidClassException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, UndefinedPrimaryKeyException,
            UndefinedTableAnnotationException, InvalidForeignKeyTarget, ReferredValueNotFoundException {
        HashSet<Depute> deputes = new HashSet<>();
        for (District district : this.getDistricts(db)) {
            deputes.addAll(district.getDeputes(db));
        }
        return new ArrayList<>(deputes);
    }

}
