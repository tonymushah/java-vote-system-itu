package mg.dirk.vote_system.ui.selections.d_selector;

import mg.dirk.vote_system.ui.helpers.tous.TousFaritany;
import mg.dirk.vote_system.ui.selections.ASelector;
import mg.dirk.vote_system.ui.selections.a_selector.FaritanyCombobox;

public class DFaritanyCombobox extends FaritanyCombobox {

    @Override
    public void setAllItems() {
        this.addItem(new TousFaritany());
        super.setAllItems();
    }

    public DFaritanyCombobox(ASelector selector) {
        super(selector);
        // TODO Auto-generated constructor stub
    }

}
