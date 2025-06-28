package mg.dirk.vote_system.ui.selections.a_selector;

import java.awt.Dimension;
import java.util.List;

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

    public void addItems(District[] faritras) {
        for (District faritra : faritras) {
            this.addItem(faritra);
        }
    }

    public void addItems(List<District> faritras) {
        for (District faritra : faritras) {
            this.addItem(faritra);
        }
    }

    public void initAllItems() {
        this.removeAllItems();
        this.setAllItems();
    }

    public void setAllItems() {
        DirkDB db = this.getSelector().getAppContext().getDb();
        try {
            this.addItems(db.select(District.class));
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
        if (this.getDistrictComboboxListener() != null)
            this.addItemListener(this.getDistrictComboboxListener());
    }

    public void removeListener() {
        if (this.getDistrictComboboxListener() != null)
            this.removeItemListener(this.getDistrictComboboxListener());
    }

    public DistrictCombobox(ASelector selector) {
        super();
        this.setName("District");
        this.setToolTipText("District");
        this.setSelector(selector);
        this.setDistrictComboboxListener(new DistrictComboboxListener(selector));
        this.initAllItems();
        this.setPreferredSize(new Dimension(150, 24));
        this.setListener();
    }
}
