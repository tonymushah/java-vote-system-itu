package mg.dirk.vote_system.ui.selections.b_selector.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.ui.selections.BSelector;

public class DeputeComboboxListener implements ItemListener {

    private BSelector bSelector;

    public BSelector getBSelector() {
        return bSelector;
    }

    public void setBSelector(BSelector bSelector) {
        this.bSelector = bSelector;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object item = e.getItem();
        if (e.getStateChange() == ItemEvent.SELECTED && item instanceof Depute) {
            this.getBSelector().setSelectedDepute((Depute) item);
        }
    }

    public DeputeComboboxListener(BSelector selector) {
        super();
        this.setBSelector(selector);
    }
}
