package mg.dirk.vote_system.ui.selections.a_selector;

import java.awt.Dimension;

import javax.swing.JComboBox;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.tables.District;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.selections.ASelector;
import mg.dirk.vote_system.ui.selections.a_selector.listeners.DistrictComboboxListener;

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
            MessageBox.error(e);
        }
    }

    private DistrictComboboxListener districtComboboxListener;

    public DistrictComboboxListener getDistrictComboboxListener() {
        return districtComboboxListener;
    }

    public void setDistrictComboboxListener(DistrictComboboxListener districtComboboxListener) {
        this.districtComboboxListener = districtComboboxListener;
    }

    public void setListener() {
        this.addItemListener(this.getDistrictComboboxListener());
    }

    public void removeListener() {
        this.removeItemListener(this.getDistrictComboboxListener());
    }

    public DistrictCombobox(ASelector selector) {
        super();
        this.setName("District");
        this.setToolTipText("District");
        this.setSelector(selector);
        this.setDistrictComboboxListener(new DistrictComboboxListener(selector));
        this.setListener();
        this.setAllItems();
        this.setPreferredSize(new Dimension(150, 24));
    }
}
