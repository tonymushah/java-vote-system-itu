package mg.dirk.vote_system.database.tables;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.annotations.ForeignKey;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;

@Table(file = "data/depute.csv")
public class Depute {
    private int id;
    private String nom;
    private int district;
    private int groupe_politique;

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

    public Depute() {
        this.setNom(new String());
    }

    @ForeignKey(targetClass = GroupePolitique.class)
    public int getGroupe_politique() {
        return groupe_politique;
    }

    public void setGroupe_politique(int groupe_politique) {
        this.groupe_politique = groupe_politique;
    }

    public Depute(int id, String nom, int district, int groupe_politique) {
        this.setId(id);
        this.setDistrict(district);
        this.setGroupe_politique(groupe_politique);
        this.setNom(nom);
    }

    public int getVotes(DirkDB db)
            throws InvalidClassException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            UndefinedPrimaryKeyException, UndefinedTableAnnotationException, InvalidForeignKeyTarget {
        int votes = 0;
        for (Vote vote : db.get_relationships(this, Vote.class, "getDepute")) {
            votes = votes + vote.getNb_vote();
        }
        return votes;
    }
}
