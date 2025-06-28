package mg.dirk.vote_system.ui.selections.a_selector;

import javax.swing.JComboBox;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.tables.Faritra;
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
        this.addItemListener(this.getFaritraComboxListener());
    }

    public void removeListener() {
        this.removeItemListener(this.getFaritraComboxListener());
    }

    public void setAllItems() {
        this.removeAllItems();
        DirkDB db = this.getSelector().getAppContext().getDb();
        try {
            for (Faritra faritra : db.select(Faritra.class)) {
                this.addItem(faritra);
            }
        } catch (NoRowsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FaritraCombobox(ASelector selector) {
        super();
        this.setName("Faritra");
        this.setToolTipText("Faritra");
        this.setSelector(selector);
        this.setFaritraComboxListener(new FaritraComboboxListener(selector));
        this.setListener();
        this.setAllItems();
    }
}
