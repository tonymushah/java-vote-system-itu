package mg.dirk.vote_system.ui.selections.a_selector;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.tables.Faritany;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.selections.ASelector;
import mg.dirk.vote_system.ui.selections.a_selector.listeners.FaritanyComboboxListener;

public class FaritanyCombobox extends JComboBox<Faritany> {
    private ASelector selector;

    public void setSelector(ASelector selector) {
        this.selector = selector;
    }

    public ASelector getSelector() {
        return selector;
    }

    public void addItems(Faritany[] faritras) {
        for (Faritany faritra : faritras) {
            this.addItem(faritra);
        }
    }

    public void addItems(List<Faritany> faritras) {
        for (Faritany faritra : faritras) {
            this.addItem(faritra);
        }
    }

    public void initAllItems() {
        this.removeAllItems();
        this.setAllItems();
    }

    public void setAllItems() {
        this.removeAllItems();
        DirkDB db = this.getSelector().getAppContext().getDb();
        try {
            this.addItems(db.select(Faritany.class));
        } catch (NoRowsException e) {
            MessageBox.error(e);
        }
    }

    private FaritanyComboboxListener faritanyComboboxListener;

    private FaritanyComboboxListener getFaritanyComboboxListener() {
        return faritanyComboboxListener;
    }

    private void setFaritanyComboboxListener(FaritanyComboboxListener faritanyComboboxListener) {
        this.faritanyComboboxListener = faritanyComboboxListener;
    }

    public void setListener() {
        if (this.getFaritanyComboboxListener() != null)
            this.addItemListener(this.getFaritanyComboboxListener());
    }

    public void removeListener() {
        if (this.getFaritanyComboboxListener() != null) {
            this.removeItemListener(this.getFaritanyComboboxListener());
        }

    }

    public FaritanyCombobox(ASelector selector) {
        super();
        this.setName("Faritany");
        this.setToolTipText("Faritany");
        this.setSelector(selector);
        this.setFaritanyComboboxListener(new FaritanyComboboxListener(selector));
        this.initAllItems();
        this.setPreferredSize(new Dimension(150, 24));
        this.setListener();
    }

}
