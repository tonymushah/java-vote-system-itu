package mg.dirk.vote_system.ui.selections.a_selector;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.tables.Faritra;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.selections.ASelector;
import mg.dirk.vote_system.ui.selections.a_selector.listeners.FaritraComboboxListener;

public class FaritraCombobox extends JComboBox<Faritra> {
    private ASelector selector;

    public void setSelector(ASelector selector) {
        this.selector = selector;
    }

    public ASelector getSelector() {
        return selector;
    }

    private FaritraComboboxListener faritraComboxListener;

    public void setFaritraComboxListener(FaritraComboboxListener faritraComboxListener) {
        this.faritraComboxListener = faritraComboxListener;
    }

    public FaritraComboboxListener getFaritraComboxListener() {
        return faritraComboxListener;
    }

    public void setListener() {
        if (this.getFaritraComboxListener() != null)
            this.addItemListener(this.getFaritraComboxListener());
    }

    public void removeListener() {
        if (this.getFaritraComboxListener() != null)
            this.removeItemListener(this.getFaritraComboxListener());
    }

    public void addItems(Faritra[] faritras) {
        for (Faritra faritra : faritras) {
            this.addItem(faritra);
        }
    }

    public void addItems(List<Faritra> faritras) {
        for (Faritra faritra : faritras) {
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
            this.addItems(db.select(Faritra.class));
        } catch (NoRowsException e) {
            MessageBox.error(e);
        }
    }

    public FaritraCombobox(ASelector selector) {
        super();
        this.setName("Faritra");
        this.setToolTipText("Faritra");
        this.setSelector(selector);
        this.setFaritraComboxListener(new FaritraComboboxListener(selector));
        this.initAllItems();
        this.setPreferredSize(new Dimension(150, 24));
        this.setListener();
    }
}
