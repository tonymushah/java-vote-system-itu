package mg.dirk.vote_system.database.tables;

import mg.dirk.vote_system.database.annotations.ForeignKey;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;

@Table(file = "data/district.csv")
public class District {
    private int id;
    private String nom;
    private int faritra;

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
    }

    public District(int id, String nom, int faritra) {
        this.setId(id);
        this.setFaritra(faritra);
        this.setNom(nom);
    }
}
