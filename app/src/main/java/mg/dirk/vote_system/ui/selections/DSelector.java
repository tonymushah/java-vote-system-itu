package mg.dirk.vote_system.ui.selections;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.database.tables.BureauDeVote;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;
import mg.dirk.vote_system.database.tables.District;
import mg.dirk.vote_system.database.tables.Faritany;
import mg.dirk.vote_system.database.tables.Faritra;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.selections.c_selector.BureauDeVoteCombobox;

public class DSelector extends ASelector {
    private BureauDeVote selectedBureauDeVote;

    @Override
    public void setSelectedFaritany(Faritany selectedFaritany) {
        boolean isInitiallyLocked = this.isLocked();
        if (!isInitiallyLocked) {
            this.lock();
        }
        super.setSelectedFaritany(selectedFaritany);
        if (selectedFaritany == null) {
            if (!isInitiallyLocked) {
                this.unlock();
            }
            return;
        }
        try {
            List<BureauDeVote> bureaux = this.getAppContext().getDb().get_relationships(
                    this.getAppContext().getDb()
                            .get_relationships(this.getAppContext().getDb().get_relationships(selectedFaritany,
                                    Faritra.class, "getFaritany"),
                                    District.class, "getFaritra"),
                    BureauDeVote.class,
                    "getDistrict");

            this.getBureauDeVoteCombobox().removeAllItems();
            this.getBureauDeVoteCombobox().addItems(bureaux);

            this.setSelectedFaritra(null);
            this.setSelectedDistrict(null);
            this.setSelectedBureauDeVote(null);
        } catch (InvalidClassException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | UndefinedPrimaryKeyException | UndefinedTableAnnotationException | InvalidForeignKeyTarget e) {
            this.resetSelections();
            e.printStackTrace();
            MessageBox.error(e);
        }
        if (!isInitiallyLocked) {
            this.unlock();
        }
    }

    @Override
    public void setSelectedFaritra(Faritra selectedFaritra) {
        boolean isInitiallyLocked = this.isLocked();
        if (!isInitiallyLocked) {
            this.lock();
        }
        super.setSelectedFaritra(selectedFaritra);
        if (selectedFaritra == null) {
            if (!isInitiallyLocked) {
                this.unlock();
            }
            return;
        }
        try {
            List<BureauDeVote> bureaux = this.getAppContext().getDb().get_relationships(
                    this.getAppContext().getDb()
                            .get_relationships(selectedFaritra, District.class, "getFaritra"),
                    BureauDeVote.class,
                    "getDistrict");

            this.getBureauDeVoteCombobox().removeAllItems();
            this.getBureauDeVoteCombobox().addItems(bureaux);

            this.setSelectedDistrict(null);
            this.setSelectedBureauDeVote(null);
        } catch (InvalidClassException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | UndefinedPrimaryKeyException | UndefinedTableAnnotationException | InvalidForeignKeyTarget e) {
            this.resetSelections();
            e.printStackTrace();
            MessageBox.error(e);
        }
        if (!isInitiallyLocked) {
            this.unlock();
        }
    }

    @Override
    public void setSelectedDistrict(District selectedDistrict) {
        boolean isInitiallyLocked = this.isLocked();
        if (!isInitiallyLocked) {
            this.lock();
        }
        super.setSelectedDistrict(selectedDistrict);
        if (selectedDistrict == null) {
            if (!isInitiallyLocked) {
                this.unlock();
            }
            return;
        }
        try {
            List<BureauDeVote> bureaux = this.getAppContext().getDb().get_relationships(selectedDistrict,
                    BureauDeVote.class,
                    "getDistrict");

            this.getBureauDeVoteCombobox().removeAllItems();
            this.getBureauDeVoteCombobox().addItems(bureaux);
            this.setSelectedBureauDeVote(null);
        } catch (InvalidClassException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | UndefinedPrimaryKeyException | UndefinedTableAnnotationException | InvalidForeignKeyTarget e) {
            this.resetSelections();
            e.printStackTrace();
            MessageBox.error(e);
        }
        if (!isInitiallyLocked) {
            this.unlock();
        }
    }

    public void setSelectedBureauDeVote(BureauDeVote selectedBureauDeVote) {
        if (selectedBureauDeVote == null) {
            this.selectedBureauDeVote = null;
        } else if (this.selectedBureauDeVote != selectedBureauDeVote && selectedBureauDeVote != null
                && this.getBureauDeVoteCombobox() != null) {
            System.out.println(selectedBureauDeVote);
            this.selectedBureauDeVote = selectedBureauDeVote;
            boolean isInitiallyLocked = this.isLocked();
            if (!isInitiallyLocked) {
                this.lock();
            }
            try {
                District district = this.getAppContext().getDb().get_reference(selectedBureauDeVote, District.class,
                        "getDistrict");
                this.setSelectedDistrict(district);

                this.getBureauDeVoteCombobox().setSelectedItem(selectedBureauDeVote);
            } catch (InvalidClassException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | UndefinedPrimaryKeyException | UndefinedTableAnnotationException | InvalidForeignKeyTarget
                    | ReferredValueNotFoundException e) {
                this.resetSelections();
                e.printStackTrace();
                MessageBox.error(e);
            }
            if (!isInitiallyLocked) {
                this.unlock();
            }
        }
    }

    public BureauDeVote getSelectedBureauDeVote() {
        return selectedBureauDeVote;
    }

    private BureauDeVoteCombobox bureauDeVoteCombobox;

    public void setBureauDeVoteCombobox(BureauDeVoteCombobox bureauDeVoteCombobox) {
        this.bureauDeVoteCombobox = bureauDeVoteCombobox;
    }

    public BureauDeVoteCombobox getBureauDeVoteCombobox() {
        return bureauDeVoteCombobox;
    }

    @Override
    public void initComboboxes() {
        super.initComboboxes();
        this.setBureauDeVoteCombobox(new BureauDeVoteCombobox(this));
    }

    @Override
    public void initAllItems() {
        boolean isInitiallyLocked = this.isLocked();
        super.initAllItems();
        this.getBureauDeVoteCombobox().initAllItems();
        if (!isInitiallyLocked) {
            this.lock();
        }
    }

    @Override
    public void unlistenComboboxes() {
        super.unlistenComboboxes();
        if (this.getBureauDeVoteCombobox() != null) {
            this.getBureauDeVoteCombobox().removeListener();
        }
    }

    @Override
    public void relistenComboboxes() {
        super.relistenComboboxes();
        if (this.getBureauDeVoteCombobox() != null) {
            this.getBureauDeVoteCombobox().setListener();
        }
    }

    @Override
    public void resetSelections() {
        super.resetSelections();
        this.selectedBureauDeVote = null;
        this.initAllItems();
    }

    @Override
    public void initUI() {
        super.initUI();

        // Bureau de vote panel
        JPanel bureauDeVotePanel = new JPanel();
        // districtPanel.setLayout(new BoxLayout(districtPanel, BoxLayout.X_AXIS));
        bureauDeVotePanel.add(new JLabel("Bureau de vote: "));
        bureauDeVotePanel.add(this.getBureauDeVoteCombobox());
        this.add(bureauDeVotePanel);

    }

    public DSelector(AppContext context) {
        super(context);
    }

}
