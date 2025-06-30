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

@Table(file = "data/bureau-de-vote.csv")
public class BureauDeVote {
    private int id;
    private String nom;
    private int district;

    @PrimaryKey
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @ForeignKey(targetClass = District.class)
    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public BureauDeVote() {
        this.setNom(new String());
    }

    public BureauDeVote(int id, String nom, int district) {
        this.setId(id);
        this.setNom(nom);
        this.setDistrict(district);
    }

    public List<Vote> getVotes(DirkDB db)
            throws InvalidClassException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            UndefinedPrimaryKeyException, UndefinedTableAnnotationException, InvalidForeignKeyTarget {
        return db.get_relationships(this, Vote.class, "getBureau_de_vote");
    }

    public List<Depute> getDeputes(DirkDB db) throws InvalidClassException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, UndefinedPrimaryKeyException,
            UndefinedTableAnnotationException, InvalidForeignKeyTarget, ReferredValueNotFoundException {
        List<Vote> votes = this.getVotes(db);
        return new ArrayList<>((new HashSet<>(db.get_reference(votes, Depute.class, "getDepute"))));
    }

    @Override
    public String toString() {
        return String.format("%d - %s", this.getId(), this.getNom());
    }
}
