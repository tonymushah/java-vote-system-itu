package mg.dirk.vote_system.ui.selections.c_selector;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.tables.BureauDeVote;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.selections.ASelector;
import mg.dirk.vote_system.ui.selections.c_selector.listeners.BureauDeVoteComboboxListener;

public class BureauDeVoteCombobox extends JComboBox<BureauDeVote> {
    private ASelector selector;

    public void setSelector(ASelector selector) {
        this.selector = selector;
    }

    public ASelector getSelector() {
        return selector;
    }

    public void addItems(BureauDeVote[] faritras) {
        for (BureauDeVote faritra : faritras) {
            this.addItem(faritra);
        }
    }

    public void addItems(List<BureauDeVote> faritras) {
        for (BureauDeVote faritra : faritras) {
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
            this.addItems(db.select(BureauDeVote.class));
        } catch (NoRowsException e) {
            MessageBox.error(e);
        }
    }

    private BureauDeVoteComboboxListener bureauDeVoteComboboxListener;

    public void setBureauDeVoteComboboxListener(BureauDeVoteComboboxListener bureauDeVoteComboboxListener) {
        this.bureauDeVoteComboboxListener = bureauDeVoteComboboxListener;
    }

    public BureauDeVoteComboboxListener getBureauDeVoteComboboxListener() {
        return bureauDeVoteComboboxListener;
    }

    public void setListener() {
        if (this.getBureauDeVoteComboboxListener() != null) {
            this.addItemListener(this.getBureauDeVoteComboboxListener());
        }
    }

    public void removeListener() {
        if (this.getBureauDeVoteComboboxListener() != null) {
            this.removeItemListener(this.getBureauDeVoteComboboxListener());
        }
    }

    public BureauDeVoteCombobox(ASelector selector) {
        super();
        this.setName("District");
        this.setToolTipText("District");
        this.setBureauDeVoteComboboxListener(new BureauDeVoteComboboxListener(selector));
        this.setSelector(selector);
        this.initAllItems();
        this.setPreferredSize(new Dimension(150, 24));
        this.setListener();
    }
}
