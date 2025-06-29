package mg.dirk.vote_system.ui.selections;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

// import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;
import mg.dirk.vote_system.database.tables.District;
import mg.dirk.vote_system.database.tables.Faritany;
import mg.dirk.vote_system.database.tables.Faritra;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.selections.a_selector.DistrictCombobox;
import mg.dirk.vote_system.ui.selections.a_selector.FaritanyCombobox;
import mg.dirk.vote_system.ui.selections.a_selector.FaritraCombobox;

public class ASelector extends JPanel {
    private Faritany selectedFaritany;
    private Faritra selectedFaritra;
    private District selectedDistrict;
    private AppContext appContext;

    private FaritanyCombobox faritanyCombobox;
    private FaritraCombobox faritraCombobox;
    private DistrictCombobox districtCombobox;

    public DistrictCombobox getDistrictCombobox() {
        return districtCombobox;
    }

    public void setDistrictCombobox(DistrictCombobox districtCombobox) {
        this.districtCombobox = districtCombobox;
    }

    public FaritraCombobox getFaritraCombobox() {
        return faritraCombobox;
    }

    public void setFaritraCombobox(FaritraCombobox faritraCombobox) {
        this.faritraCombobox = faritraCombobox;
    }

    public void setFaritanyCombobox(FaritanyCombobox faritanyCombobox) {
        this.faritanyCombobox = faritanyCombobox;
    }

    public FaritanyCombobox getFaritanyCombobox() {
        return faritanyCombobox;
    }

    public Faritany getSelectedFaritany() {
        return selectedFaritany;
    }

    public void setSelectedFaritany(Faritany selectedFaritany) {
        if (selectedFaritany == null) {
            this.selectedFaritany = null;
        } else if (this.selectedFaritany != selectedFaritany && selectedFaritany != null
                && this.getFaritanyCombobox() != null) {
            System.out.println(selectedFaritany);
            this.selectedFaritany = selectedFaritany;
            boolean isInitiallyLocked = this.isLocked();
            if (!isInitiallyLocked) {
                this.lock();
            }
            try {
                List<Faritra> faritras = this.getAppContext().getDb().get_relationships(this.getSelectedFaritany(),
                        Faritra.class, "getFaritany");

                // if (!faritras.contains(this.getFaritraCombobox().getSelectedItem())) {
                this.getFaritraCombobox().removeAllItems();
                this.getFaritraCombobox().addItems(faritras);
                // }

                List<District> districts = this.getAppContext().getDb().get_relationships(
                        faritras.toArray(new Faritra[faritras.size()]),
                        District.class, "getFaritra");

                // if (!districts.contains(this.getDistrictCombobox().getSelectedItem())) {
                this.getDistrictCombobox().removeAllItems();
                this.getDistrictCombobox().addItems(districts);
                // }

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
    }

    public Faritra getSelectedFaritra() {
        return selectedFaritra;
    }

    public void unlistenComboboxes() {
        if (this.getFaritanyCombobox() != null)
            this.getFaritanyCombobox().removeListener();
        if (this.getFaritraCombobox() != null)
            this.getFaritraCombobox().removeListener();
        if (this.getDistrictCombobox() != null)
            this.getDistrictCombobox().removeListener();
    }

    public void relistenComboboxes() {
        if (this.getFaritanyCombobox() != null)
            this.getFaritanyCombobox().setListener();
        if (this.getFaritraCombobox() != null)
            this.getFaritraCombobox().setListener();
        if (this.getDistrictCombobox() != null)
            this.getDistrictCombobox().setListener();
    }

    protected boolean isLocked;

    public boolean isLocked() {
        return this.isLocked;
    }

    public void lock() {
        this.isLocked = true;
        this.unlistenComboboxes();
    }

    public void unlock() {
        this.isLocked = false;
        this.relistenComboboxes();
    }

    public void setSelectedFaritra(Faritra selectedFaritra) {
        System.out.println(selectedFaritra);
        if (selectedFaritra == null) {
            this.selectedFaritra = null;
        } else if (this.selectedFaritra != selectedFaritra && selectedFaritra != null
                && this.getFaritraCombobox() != null) {
            System.out.println(selectedFaritra);
            this.selectedFaritra = selectedFaritra;
            boolean isInitiallyLocked = this.isLocked();
            if (!isInitiallyLocked) {
                this.lock();
            }

            try {
                Faritany faritany = this.getAppContext().getDb().get_reference(selectedFaritra, Faritany.class,
                        "getFaritany");

                this.setSelectedFaritany(faritany);
                if (this.getFaritanyCombobox() != null) {
                    this.getFaritanyCombobox().setSelectedItem(faritany);
                }

                List<District> districts = this.getAppContext().getDb().get_relationships(
                        selectedFaritra,
                        District.class, "getFaritra");

                // if (!districts.contains(this.getDistrictCombobox().getSelectedItem())) {
                this.getDistrictCombobox().removeAllItems();
                this.getDistrictCombobox().addItems(districts);
                // }

                this.getFaritraCombobox().setSelectedItem(selectedFaritra);
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

    public District getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(District selectedDistrict) {
        if (selectedDistrict == null) {
            this.selectedDistrict = null;
        } else if (this.selectedDistrict != selectedDistrict && selectedDistrict != null
                && this.getDistrictCombobox() != null) {
            System.out.println(selectedDistrict);
            this.selectedDistrict = selectedDistrict;
            boolean isInitiallyLocked = this.isLocked();
            if (!isInitiallyLocked) {
                this.lock();
            }
            try {
                Faritra faritra = this.getAppContext().getDb().get_reference(selectedDistrict, Faritra.class,
                        "getFaritra");

                this.setSelectedFaritra(faritra);

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

    public AppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    public void initAllItems() {
        boolean isInitiallyLocked = this.isLocked();
        if (!isInitiallyLocked) {
            this.lock();
        }
        this.getFaritanyCombobox().initAllItems();
        this.getFaritraCombobox().initAllItems();
        this.getDistrictCombobox().initAllItems();
        if (!isInitiallyLocked) {
            this.unlock();
        }
    }

    public void resetSelections() {
        this.selectedDistrict = null;
        this.selectedFaritra = null;
        this.selectedFaritany = null;
        this.initAllItems();
    }

    public void initUI() {
        // this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Faritany panel
        JPanel faritanyPanel = new JPanel();
        // faritanyPanel.setLayout(new BoxLayout(faritanyPanel, BoxLayout.X_AXIS));
        faritanyPanel.add(new JLabel("Faritany: "));
        faritanyPanel.add(this.getFaritanyCombobox());
        this.add(faritanyPanel);

        // Faritra panel
        JPanel faritraPanel = new JPanel();
        // faritraPanel.setLayout(new BoxLayout(faritraPanel, BoxLayout.X_AXIS));
        faritraPanel.add(new JLabel("Faritra: "));
        faritraPanel.add(this.getFaritraCombobox());
        this.add(faritraPanel);

        // District panel
        JPanel districtPanel = new JPanel();
        // districtPanel.setLayout(new BoxLayout(districtPanel, BoxLayout.X_AXIS));
        districtPanel.add(new JLabel("District: "));
        districtPanel.add(this.getDistrictCombobox());
        this.add(districtPanel);

    }

    public void initComboboxes() {
        this.setFaritanyCombobox(new FaritanyCombobox(this));
        this.setFaritraCombobox(new FaritraCombobox(this));
        this.setDistrictCombobox(new DistrictCombobox(this));
    }

    public ASelector(AppContext context) {
        super();
        this.setAppContext(context);
        this.initComboboxes();
        this.initUI();
    }

}
