package mg.dirk.vote_system.ui.selections.d_selector;

import mg.dirk.vote_system.ui.helpers.tous.TousFaritra;
import mg.dirk.vote_system.ui.selections.ASelector;
import mg.dirk.vote_system.ui.selections.a_selector.FaritraCombobox;

public class DFaritraCombobx extends FaritraCombobox {

    @Override
    public void setAllItems() {
        this.addItem(new TousFaritra());
        super.setAllItems();
    }

    public DFaritraCombobx(ASelector selector) {
        super(selector);
        // TODO Auto-generated constructor stub
    }

}
