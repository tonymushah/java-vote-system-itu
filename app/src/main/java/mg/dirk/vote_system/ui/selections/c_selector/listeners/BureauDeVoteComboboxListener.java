package mg.dirk.vote_system.ui.selections.c_selector.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import mg.dirk.vote_system.database.tables.BureauDeVote;
import mg.dirk.vote_system.ui.selections.ASelector;
import mg.dirk.vote_system.ui.selections.CSelector;
import mg.dirk.vote_system.ui.selections.DSelector;

public class BureauDeVoteComboboxListener implements ItemListener {
    private ASelector selector;

    public ASelector getSelector() {
        return selector;
    }

    public void setSelector(ASelector selector) {
        this.selector = selector;
    }

    public BureauDeVoteComboboxListener(ASelector selector) {
        super();
        this.setSelector(selector);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object item = e.getItem();
        if (e.getStateChange() == ItemEvent.SELECTED && item instanceof BureauDeVote) {
            ASelector selector = this.getSelector();
            if (selector instanceof CSelector) {
                ((CSelector) selector).setSelectedBureauDeVote((BureauDeVote) item);
            } else if (selector instanceof DSelector) {
                ((DSelector) selector).setSelectedBureauDeVote((BureauDeVote) item);
            }
        }
    }

}
