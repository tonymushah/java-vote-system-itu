package mg.dirk.vote_system.database.tables;

import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;

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
}
