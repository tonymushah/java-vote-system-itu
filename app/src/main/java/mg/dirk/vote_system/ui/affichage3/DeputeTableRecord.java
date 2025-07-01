package mg.dirk.vote_system.ui.affichage3;

public class DeputeTableRecord {
    private String depute;
    private String groupePolitique;
    private int votes;

    public String getDepute() {
        return depute;
    }

    public void setDepute(String depute) {
        this.depute = depute;
    }

    public String getGroupePolitique() {
        return groupePolitique;
    }

    public void setGroupePolitique(String groupePolitique) {
        this.groupePolitique = groupePolitique;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public DeputeTableRecord(String depute, String groupePolitique, int votes) {
        this.setDepute(depute);
        this.setGroupePolitique(groupePolitique);
        this.setVotes(votes);
    }

}
