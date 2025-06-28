package mg.dirk.vote_system.database.tables;

import java.util.Date;

import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;

@Table(file = "data/groupe-polique.csv")
public class GroupePolitique {
    private int id;
    private String nom;
    private Date date_de_creation;

    public Date getDate_de_creation() {
        return date_de_creation;
    }

    @PrimaryKey
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setDate_de_creation(Date date_de_creation) {
        this.date_de_creation = date_de_creation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public GroupePolitique() {
        this.setNom(new String());
        this.setDate_de_creation(new Date());
    }

    public GroupePolitique(int id, String nom, Date date_de_creation) {
        this.setId(id);
        this.setNom(nom);
        this.setDate_de_creation(date_de_creation);
    }

    @Override
    public String toString() {
        return String.format("%d - %s", this.getId(), this.getNom());
    }
}
