package mg.dirk.vote_system.ui.selections.a_selector;

import javax.swing.JComboBox;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.tables.Faritany;
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

    public void setAllItems() {
        this.removeAllItems();
        DirkDB db = this.getSelector().getAppContext().getDb();
        try {
            for (Faritany faritany : db.select(Faritany.class)) {
                this.addItem(faritany);
            }
        } catch (NoRowsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        this.addItemListener(this.getFaritanyComboboxListener());
    }

    public void removeListener() {
        this.removeItemListener(this.getFaritanyComboboxListener());
    }

    public FaritanyCombobox(ASelector selector) {
        super();
        this.setName("Faritany");
        this.setToolTipText("Faritany");
        this.setSelector(selector);
        this.setFaritanyComboboxListener(new FaritanyComboboxListener(selector));
        this.setListener();
        this.setAllItems();
        this.setSelectedItem(selector);
    }

}
