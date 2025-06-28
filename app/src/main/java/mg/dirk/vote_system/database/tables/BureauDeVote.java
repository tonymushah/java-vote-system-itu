package mg.dirk.vote_system.database.tables;

import mg.dirk.vote_system.database.annotations.ForeignKey;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;

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

    @Override
    public String toString() {
        return String.format("%d - %s", this.getId(), this.getNom());
    }
}
