package mg.dirk.vote_system.database.tables;

import mg.dirk.vote_system.database.annotations.ForeignKey;
import mg.dirk.vote_system.database.annotations.NoThrowOnParse;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;

@Table(file = "data/district.csv")
public class District {
    private int id;
    private String nom;
    private int faritra;
    @NoThrowOnParse
    private int nbDepute;

    public int getNbDepute() {
        return nbDepute;
    }

    public void setNbDepute(int nbDepute) {
        this.nbDepute = nbDepute;
    }

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

    @ForeignKey(targetClass = Faritra.class)
    public int getFaritra() {
        return faritra;
    }

    public void setFaritra(int faritra) {
        this.faritra = faritra;
    }

    public District() {
        this.setNom(new String());
        this.setNbDepute(1);
    }

    public District(int id, String nom, int faritra) {
        this();
        this.setId(id);
        this.setFaritra(faritra);
        this.setNom(nom);
    }

    @Override
    public String toString() {
        return String.format("%d - %s", this.getId(), this.getNom());
    }
}
