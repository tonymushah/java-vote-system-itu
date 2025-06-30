package mg.dirk.vote_system.database.tables;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.annotations.ForeignKey;
import mg.dirk.vote_system.database.annotations.NoThrowOnParse;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;
import mg.dirk.vote_system.database.tables.helpers.DistrictDepute;
import mg.dirk.vote_system.database.tables.helpers.VotesDistrictDeputeComparator;
import mg.dirk.vote_system.database.tables.helpers.VotesWGroupeDistrictDeputeComparator;

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

    public List<BureauDeVote> getBureauDeVotes(DirkDB db)
            throws InvalidClassException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            UndefinedPrimaryKeyException, UndefinedTableAnnotationException, InvalidForeignKeyTarget {
        return db.get_relationships(this, BureauDeVote.class, "getDistrict");
    }

    public List<Vote> getVotes(DirkDB db)
            throws InvalidClassException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            UndefinedPrimaryKeyException, UndefinedTableAnnotationException, InvalidForeignKeyTarget {
        return db.get_relationships(this.getBureauDeVotes(db), Vote.class,
                "getBureau_de_vote");
    }

    public List<DistrictDepute> getElectedDepute(DirkDB db)
            throws InvalidClassException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            UndefinedPrimaryKeyException, UndefinedTableAnnotationException, InvalidForeignKeyTarget,
            ReferredValueNotFoundException {
        HashMap<Depute, Integer> toUseVotes = new HashMap<>();
        for (Vote vote : this.getVotes(db)) {
            Depute depute = db.get_reference(vote, Depute.class, "getDepute");
            int votes = toUseVotes.getOrDefault(depute, 0).intValue();
            toUseVotes.put(depute, votes + vote.getNb_vote());
        }

        List<DistrictDepute> deputes_votes = new ArrayList<>();
        for (Entry<Depute, Integer> entry : toUseVotes.entrySet()) {
            deputes_votes.add(new DistrictDepute(this, entry.getKey(), entry.getValue()));
        }

        if (deputes_votes.size() == 0) {
            return deputes_votes;
        }
        // [x] Add sort mdr
        // TODO Check if sort works
        deputes_votes.sort((new VotesDistrictDeputeComparator()).reversed());
        if (this.getNbDepute() == 1) {
            List<DistrictDepute> deputes = new ArrayList<>();
            int max = deputes_votes.getFirst().getNbVotes();
            for (DistrictDepute districtDepute : deputes_votes) {
                if (districtDepute.getNbVotes() == max) {
                    deputes.add(districtDepute);
                } else {
                    break;
                }
            }
            deputes.sort((new VotesWGroupeDistrictDeputeComparator(db)).reversed());
            return deputes.subList(0, 1);
        } else {
            return deputes_votes.subList(0, this.getNbDepute());
        }

    }

    public List<Depute> getDeputes(DirkDB db) throws InvalidClassException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, UndefinedPrimaryKeyException,
            UndefinedTableAnnotationException, InvalidForeignKeyTarget, ReferredValueNotFoundException {
        HashSet<Depute> deputes = new HashSet<>();
        for (BureauDeVote dBureauDeVote : this.getBureauDeVotes(db)) {
            deputes.addAll(dBureauDeVote.getDeputes(db));
        }
        return new ArrayList<>(deputes);
    }

    public static List<DistrictDepute> getElecteDeputes(DirkDB db)
            throws InvalidClassException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            NoRowsException, UndefinedPrimaryKeyException, UndefinedTableAnnotationException, InvalidForeignKeyTarget,
            ReferredValueNotFoundException {
        List<DistrictDepute> deputes_votes = new ArrayList<>();
        for (District district : db.select(District.class)) {
            deputes_votes.addAll(district.getElectedDepute(db));
        }
        return deputes_votes;
    }

}
