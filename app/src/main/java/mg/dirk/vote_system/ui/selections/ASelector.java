package mg.dirk.vote_system.ui.selections;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.database.tables.BureauDeVote;
import mg.dirk.vote_system.database.tables.District;
import mg.dirk.vote_system.database.tables.Faritany;
import mg.dirk.vote_system.database.tables.Faritra;

public class ASelector extends JPanel {
    private Faritany selectedFaritany;
    private Faritra selectedFaritra;
    private District selectedDistrict;
    private BureauDeVote selectedBureauDeVote;
    private AppContext appContext;

    private FaritanyCombobox faritanyCombobox;

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
        this.selectedFaritany = selectedFaritany;
    }

    public Faritra getSelectedFaritra() {
        return selectedFaritra;
    }

    public void setSelectedFaritra(Faritra selectedFaritra) {
        this.selectedFaritra = selectedFaritra;
    }

    public District getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(District selectedDistrict) {
        this.selectedDistrict = selectedDistrict;
    }

    public BureauDeVote getSelectedBureauDeVote() {
        return selectedBureauDeVote;
    }

    public void setSelectedBureauDeVote(BureauDeVote selectedBureauDeVote) {
        this.selectedBureauDeVote = selectedBureauDeVote;
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    public void initUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.getFaritanyCombobox());
    }

    public ASelector(AppContext context) {
        this.setAppContext(context);
        this.setFaritanyCombobox(new FaritanyCombobox(this));
    }

}
