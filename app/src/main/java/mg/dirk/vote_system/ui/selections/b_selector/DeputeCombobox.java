package mg.dirk.vote_system.ui.selections.b_selector;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.selections.BSelector;
import mg.dirk.vote_system.ui.selections.b_selector.listeners.DeputeComboboxListener;

public class DeputeCombobox extends JComboBox<Depute> {
    private BSelector selector;

    public void setSelector(BSelector selector) {
        this.selector = selector;
    }

    public BSelector getSelector() {
        return selector;
    }

    public void addItems(Depute[] faritras) {
        for (Depute faritra : faritras) {
            this.addItem(faritra);
        }
    }

    public void addItems(List<Depute> faritras) {
        for (Depute faritra : faritras) {
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
            this.addItems(db.select(Depute.class));
        } catch (NoRowsException e) {
            MessageBox.error(e);
        }
    }

    private DeputeComboboxListener deputeComboboxListener;

    public DeputeComboboxListener getDeputeComboboxListener() {
        return deputeComboboxListener;
    }

    public void setDeputeComboboxListener(DeputeComboboxListener deputeComboboxListener) {
        this.deputeComboboxListener = deputeComboboxListener;
    }

    public void setListener() {
        if (this.getDeputeComboboxListener() != null) {
            this.addItemListener(this.getDeputeComboboxListener());
        }
    }

    public void removeListener() {
        if (this.getDeputeComboboxListener() != null) {
            this.removeItemListener(this.getDeputeComboboxListener());
        }
    }

    public DeputeCombobox(BSelector selector) {
        super();
        this.setName("District");
        this.setToolTipText("District");
        this.setDeputeComboboxListener(new DeputeComboboxListener(selector));
        this.setSelector(selector);
        this.initAllItems();
        this.setPreferredSize(new Dimension(150, 24));
        this.setListener();
    }
}
