package mg.dirk.vote_system.database.tables.helpers;

import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.database.tables.District;

public class DistrictDepute {
    private District district;
    private Depute depute;
    private int nbVotes;

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Depute getDepute() {
        return depute;
    }

    public void setDepute(Depute depute) {
        this.depute = depute;
    }

    public int getNbVotes() {
        return nbVotes;
    }

    public void setNbVotes(int nbVotes) {
        this.nbVotes = nbVotes;
    }

    public DistrictDepute() {
        super();
    }

    public boolean isValid() {
        return this.getDistrict() != null && this.getDepute() != null;
    }

    public DistrictDepute(District district, Depute depute, int nbVotes) {
        this.setDistrict(district);
        this.setDepute(depute);
        this.setNbVotes(nbVotes);
    }

}
