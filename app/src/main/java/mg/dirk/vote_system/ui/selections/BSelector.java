package mg.dirk.vote_system.ui.selections;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;
import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.database.tables.District;
import mg.dirk.vote_system.database.tables.Faritany;
import mg.dirk.vote_system.database.tables.Faritra;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.selections.b_selector.DeputeCombobox;

public class BSelector extends ASelector {
    private Depute selectedDepute;

    private DeputeCombobox deputeCombobox;

    @Override
    public void setSelectedFaritany(Faritany selectedFaritany) {
        boolean isInitiallyLocked = this.isLocked();
        if (!isInitiallyLocked) {
            this.lock();
        }
        super.setSelectedFaritany(selectedFaritany);
        try {
            List<Depute> deputes = this.getAppContext().getDb().get_relationships(
                    this.getAppContext().getDb()
                            .get_relationships(this.getAppContext().getDb().get_relationships(selectedFaritany,
                                    Faritra.class, "getFaritany"), District.class, "getFaritra"),
                    Depute.class,
                    "getDistrict");

            this.getDeputeCombobox().removeAllItems();
            this.getDeputeCombobox().addItems(deputes);
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
        try {
            List<Depute> deputes = this.getAppContext().getDb().get_relationships(
                    this.getAppContext().getDb().get_relationships(selectedFaritra, District.class, "getFaritra"),
                    Depute.class,
                    "getDistrict");

            this.getDeputeCombobox().removeAllItems();
            this.getDeputeCombobox().addItems(deputes);
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
        try {
            List<Depute> deputes = this.getAppContext().getDb().get_relationships(selectedDistrict, Depute.class,
                    "getDistrict");

            this.getDeputeCombobox().removeAllItems();
            this.getDeputeCombobox().addItems(deputes);
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

    public void setSelectedDepute(Depute selectedDepute) {
        if (selectedDepute == null) {
            this.selectedDepute = null;
        } else if (this.selectedDepute != selectedDepute && selectedDepute != null
                && this.getDeputeCombobox() != null) {
            System.out.println(selectedDepute);
            this.selectedDepute = selectedDepute;
            boolean isInitiallyLocked = this.isLocked();
            if (!isInitiallyLocked) {
                this.lock();
            }
            try {
                District district = this.getAppContext().getDb().get_reference(selectedDepute, District.class,
                        "getDistrict");
                this.setSelectedDistrict(district);

                this.getDeputeCombobox().setSelectedItem(selectedDepute);
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

    public Depute getSelectedDepute() {
        return selectedDepute;
    }

    public DeputeCombobox getDeputeCombobox() {
        return deputeCombobox;
    }

    public void setDeputeCombobox(DeputeCombobox deputeCombobox) {

        this.deputeCombobox = deputeCombobox;
    }

    @Override
    public void initComboboxes() {
        super.initComboboxes();
        this.setDeputeCombobox(new DeputeCombobox(this));
    }

    @Override
    public void initAllItems() {
        boolean isInitiallyLocked = this.isLocked();
        super.initAllItems();
        this.getDeputeCombobox().initAllItems();
        if (!isInitiallyLocked) {
            this.lock();
        }
    }

    @Override
    public void unlistenComboboxes() {
        super.unlistenComboboxes();
        if (this.getDeputeCombobox() != null) {
            this.getDeputeCombobox().removeListener();
        }
    }

    @Override
    public void relistenComboboxes() {
        super.relistenComboboxes();
        if (this.getDeputeCombobox() != null) {
            this.getDeputeCombobox().setListener();
        }
    }

    @Override
    public void resetSelections() {
        // TODO Auto-generated method stub
        super.resetSelections();
        this.selectedDepute = null;
        this.initAllItems();
    }

    @Override
    public void initUI() {
        super.initUI();

        // Depute panel
        JPanel deputePanel = new JPanel();
        // districtPanel.setLayout(new BoxLayout(districtPanel, BoxLayout.X_AXIS));
        deputePanel.add(new JLabel("Depute: "));
        deputePanel.add(this.getDeputeCombobox());
        this.add(deputePanel);
    }

    public BSelector(AppContext context) {
        super(context);
    }

}
