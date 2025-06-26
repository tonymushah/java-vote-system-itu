package mg.dirk.vote_system.database.tables;

import mg.dirk.vote_system.database.annotations.ForeignKey;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.annotations.Table;

@Table(file = "data/votes.csv")
public class Vote {
    private int id;
    private int depute;
    private int bureau_de_vote;
    private int nb_vote;

    @PrimaryKey
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ForeignKey(targetClass = Depute.class)
    public int getDepute() {
        return depute;
    }

    public void setDepute(int depute) {
        this.depute = depute;
    }

    @ForeignKey(targetClass = BureauDeVote.class)
    public int getBureau_de_vote() {
        return bureau_de_vote;
    }

    public void setBureau_de_vote(int bureau_de_vote) {
        this.bureau_de_vote = bureau_de_vote;
    }

    public int getNb_vote() {
        return nb_vote;
    }

    public void setNb_vote(int nb_vote) {
        this.nb_vote = nb_vote;
    }

    public Vote() {
    }

    public Vote(int id, int depute, int bureau_de_vote, int nb_vote) {
        this.setId(id);
        this.setDepute(depute);
        this.setBureau_de_vote(bureau_de_vote);
        this.setNb_vote(nb_vote);
    }

}
