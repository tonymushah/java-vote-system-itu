package mg.dirk.vote_system.database.tables;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;

@Table(file = "data/faritany.csv")
public class Faritany {
    private int id;
    private String nom;

    @PrimaryKey
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return String.format("%d - %s", this.getId(), this.getNom());
    }

    public List<Faritra> getFaritra(DirkDB db)
            throws InvalidClassException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            UndefinedPrimaryKeyException, UndefinedTableAnnotationException, InvalidForeignKeyTarget {
        return db.get_relationships(this, Faritra.class, "getFaritany");
    }

    public List<Depute> getDeputes(DirkDB db) throws InvalidClassException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, UndefinedPrimaryKeyException,
            UndefinedTableAnnotationException, InvalidForeignKeyTarget, ReferredValueNotFoundException {
        HashSet<Depute> deputes = new HashSet<>();
        for (Faritra faritra : this.getFaritra(db)) {
            deputes.addAll(faritra.getDeputes(db));
        }
        return new ArrayList<>(deputes);
    }
}
