package mg.dirk.vote_system.ui.selections.d_selector;

import mg.dirk.vote_system.ui.helpers.tous.TousDistrict;
import mg.dirk.vote_system.ui.selections.ASelector;
import mg.dirk.vote_system.ui.selections.a_selector.DistrictCombobox;

public class DDistrictCombobox extends DistrictCombobox {

    @Override
    public void setAllItems() {
        this.addItem(new TousDistrict());
        super.setAllItems();
    }

    public DDistrictCombobox(ASelector selector) {
        super(selector);
        // TODO Auto-generated constructor stub
    }

}
