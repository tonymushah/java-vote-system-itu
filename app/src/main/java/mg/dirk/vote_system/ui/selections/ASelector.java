package mg.dirk.vote_system.ui.selections;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

// import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;
import mg.dirk.vote_system.database.tables.District;
import mg.dirk.vote_system.database.tables.Faritany;
import mg.dirk.vote_system.database.tables.Faritra;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.helpers.tous.TousDistrict;
import mg.dirk.vote_system.ui.helpers.tous.TousFaritany;
import mg.dirk.vote_system.ui.helpers.tous.TousFaritra;
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

    private boolean includeTous;

    public boolean getIncludeTous() {
        return includeTous;
    }

    public void setIncludeTous(boolean includeTous) {
        this.includeTous = includeTous;
    }

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
        boolean isInitiallyLocked = this.isLocked();
        if (!isInitiallyLocked) {
            this.lock();
        }
        if (selectedFaritany == null) {
            this.selectedFaritany = null;
            if (this.getIncludeTous() && this.getFaritanyCombobox() != null) {
                this.getFaritanyCombobox().setSelectedItem(new TousFaritany());
            }
        } else if (this.selectedFaritany != selectedFaritany && selectedFaritany != null
                && this.getFaritanyCombobox() != null) {
            System.out.println(selectedFaritany);
            boolean isTousFaritany = selectedFaritany instanceof TousFaritany;
            if (isTousFaritany) {
                this.selectedFaritany = null;
            } else {
                this.selectedFaritany = selectedFaritany;
            }

            try {

                List<Faritra> faritras;
                if (isTousFaritany) {
                    faritras = this.getAppContext().getDb().select(Faritra.class);
                } else {
                    faritras = this.getAppContext().getDb().get_relationships(this.getSelectedFaritany(),
                            Faritra.class, "getFaritany");
                }

                // if (!faritras.contains(this.getFaritraCombobox().getSelectedItem())) {
                this.getFaritraCombobox().removeAllItems();
                if (this.includeTous) {
                    this.getFaritraCombobox().addItem(new TousFaritra());
                }
                this.getFaritraCombobox().addItems(faritras);
                // }

                List<District> districts;
                if (isTousFaritany) {
                    districts = this.getAppContext().getDb().select(District.class);
                } else {

                    districts = this.getAppContext().getDb().get_relationships(
                            faritras.toArray(new Faritra[faritras.size()]),
                            District.class, "getFaritra");
                }
                // if (!districts.contains(this.getDistrictCombobox().getSelectedItem())) {
                this.getDistrictCombobox().removeAllItems();
                if (this.includeTous) {
                    this.getDistrictCombobox().addItem(new TousDistrict());
                }
                this.getDistrictCombobox().addItems(districts);
                // }

            } catch (InvalidClassException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | UndefinedPrimaryKeyException | UndefinedTableAnnotationException | InvalidForeignKeyTarget
                    | NoRowsException e) {
                this.resetSelections();
                e.printStackTrace();
                MessageBox.error(e);
            }

        }
        if (!isInitiallyLocked) {
            this.unlock();
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
        boolean isInitiallyLocked = this.isLocked();
        if (!isInitiallyLocked) {
            this.lock();
        }
        System.out.println(selectedFaritra);
        if (selectedFaritra == null) {
            this.selectedFaritra = null;
            if (this.getIncludeTous() && this.getFaritraCombobox() != null) {
                this.getFaritraCombobox().setSelectedItem(new TousFaritra());
            }
        } else if (this.selectedFaritra != selectedFaritra && selectedFaritra != null
                && this.getFaritraCombobox() != null) {
            System.out.println(selectedFaritra);
            boolean isTousFaritra = selectedFaritra instanceof TousFaritra;
            if (isTousFaritra) {
                this.selectedFaritra = null;
            } else {
                this.selectedFaritra = selectedFaritra;
            }

            try {
                if (!isTousFaritra) {
                    Faritany faritany = this.getAppContext().getDb().get_reference(selectedFaritra, Faritany.class,
                            "getFaritany");

                    this.setSelectedFaritany(faritany);
                    if (this.getFaritanyCombobox() != null) {
                        this.getFaritanyCombobox().setSelectedItem(faritany);
                    }
                }

                List<District> districts;
                if (isTousFaritra) {
                    districts = this.getAppContext().getDb().select(District.class);
                } else {
                    districts = this.getAppContext().getDb().get_relationships(
                            selectedFaritra,
                            District.class, "getFaritra");
                }

                // if (!districts.contains(this.getDistrictCombobox().getSelectedItem())) {
                this.getDistrictCombobox().removeAllItems();
                if (this.getIncludeTous()) {
                    this.getDistrictCombobox().addItem(new TousDistrict());
                }
                this.getDistrictCombobox().addItems(districts);
                // }

                this.getFaritraCombobox().setSelectedItem(selectedFaritra);
            } catch (InvalidClassException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | UndefinedPrimaryKeyException | UndefinedTableAnnotationException | InvalidForeignKeyTarget
                    | ReferredValueNotFoundException | NoRowsException e) {
                this.resetSelections();
                e.printStackTrace();
                MessageBox.error(e);
            }

        }
        if (!isInitiallyLocked) {
            this.unlock();
        }
    }

    public District getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(District selectedDistrict) {
        boolean isInitiallyLocked = this.isLocked();
        if (!isInitiallyLocked) {
            this.lock();
        }
        if (selectedDistrict == null) {
            this.selectedDistrict = null;
            if (this.getIncludeTous() && this.getDistrictCombobox() != null) {
                this.getDistrictCombobox().setSelectedItem(new TousDistrict());
            }
        } else if (this.selectedDistrict != selectedDistrict && selectedDistrict != null
                && this.getDistrictCombobox() != null) {
            System.out.println(selectedDistrict);
            this.selectedDistrict = selectedDistrict;

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

        }
        if (!isInitiallyLocked) {
            this.unlock();
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
