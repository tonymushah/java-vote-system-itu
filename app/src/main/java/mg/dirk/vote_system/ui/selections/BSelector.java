package mg.dirk.vote_system.ui.selections;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.ui.selections.b_selector.DeputeCombobox;

public class BSelector extends ASelector {
    private Depute selectedDepute;

    private DeputeCombobox deputeCombobox;

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

            this.getDeputeCombobox().setSelectedItem(selectedDepute);

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
