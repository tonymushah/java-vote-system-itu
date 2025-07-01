package mg.dirk.vote_system.ui.selections.d_selector;

import mg.dirk.vote_system.ui.helpers.tous.TousBureauDeVote;
import mg.dirk.vote_system.ui.selections.ASelector;
import mg.dirk.vote_system.ui.selections.c_selector.BureauDeVoteCombobox;

public class DBureauDeVoteCombobox extends BureauDeVoteCombobox {
    @Override
    public void setAllItems() {
        this.addItem(new TousBureauDeVote());
        super.setAllItems();
    }

    public DBureauDeVoteCombobox(ASelector selector) {
        super(selector);
        // TODO Auto-generated constructor stub
    }

}
