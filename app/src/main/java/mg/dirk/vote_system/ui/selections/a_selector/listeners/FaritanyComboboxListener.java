package mg.dirk.vote_system.ui.selections.a_selector.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import mg.dirk.vote_system.database.tables.Faritany;
import mg.dirk.vote_system.ui.selections.ASelector;

public class FaritanyComboboxListener implements ItemListener {
    private ASelector aSelector;

    public ASelector getASelector() {
        return aSelector;
    }

    public void setASelector(ASelector aSelector) {
        this.aSelector = aSelector;
    }

    public FaritanyComboboxListener(ASelector aSelector) {
        super();
        this.setASelector(aSelector);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object item = e.getItem();
        if (e.getStateChange() == ItemEvent.SELECTED && item instanceof Faritany) {
            this.getASelector().setSelectedFaritany((Faritany) item);
        }
    }

}
