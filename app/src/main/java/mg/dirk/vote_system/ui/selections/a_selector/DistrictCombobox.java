package mg.dirk.vote_system.ui.selections.a_selector;

import javax.swing.JComboBox;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.tables.District;
import mg.dirk.vote_system.ui.selections.ASelector;

public class DistrictCombobox extends JComboBox<District> {
    private ASelector selector;

    public void setSelector(ASelector selector) {
        this.selector = selector;
    }

    public ASelector getSelector() {
        return selector;
    }

    public void setAllItems() {
        this.removeAllItems();
        DirkDB db = this.getSelector().getAppContext().getDb();
        try {
            for (District faritany : db.select(District.class)) {
                this.addItem(faritany);
            }
        } catch (NoRowsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public DistrictCombobox(ASelector selector) {
        this.setSelector(selector);
    }
}
