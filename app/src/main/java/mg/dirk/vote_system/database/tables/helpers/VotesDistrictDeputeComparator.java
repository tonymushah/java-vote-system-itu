package mg.dirk.vote_system.database.tables.helpers;

import java.util.Comparator;

public class VotesDistrictDeputeComparator implements Comparator<DistrictDepute> {

    @Override
    public int compare(DistrictDepute arg0, DistrictDepute arg1) {
        if (arg0.getNbVotes() < arg1.getNbVotes()) {
            return -1;
        } else if (arg0.getNbVotes() > arg1.getNbVotes()) {
            return 1;
        } else {
            return 0;
        }
    }

}
