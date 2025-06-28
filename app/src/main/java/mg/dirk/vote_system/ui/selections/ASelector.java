package mg.dirk.vote_system.ui.selections;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.database.tables.BureauDeVote;
import mg.dirk.vote_system.database.tables.District;
import mg.dirk.vote_system.database.tables.Faritany;
import mg.dirk.vote_system.database.tables.Faritra;
import mg.dirk.vote_system.ui.selections.a_selector.FaritanyCombobox;
import mg.dirk.vote_system.ui.selections.a_selector.FaritraCombobox;

public class ASelector extends JPanel {
    private Faritany selectedFaritany;
    private Faritra selectedFaritra;
    private District selectedDistrict;
    private BureauDeVote selectedBureauDeVote;
    private AppContext appContext;

    private FaritanyCombobox faritanyCombobox;
    private FaritraCombobox faritraCombobox;

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

        // Faritany panel
        JPanel faritanyPanel = new JPanel();
        faritanyPanel.setLayout(new BoxLayout(faritanyPanel, BoxLayout.X_AXIS));
        faritanyPanel.add(new JLabel("Faritany: "));
        faritanyPanel.add(this.getFaritanyCombobox());
        this.add(faritanyPanel);

        // Faritra panel
        JPanel faritraPanel = new JPanel();
        faritraPanel.setLayout(new BoxLayout(faritraPanel, BoxLayout.X_AXIS));
        faritanyPanel.add(new JLabel("Faritra: "));
        faritraPanel.add(this.getFaritraCombobox());
        this.add(faritraPanel);

        // District panel
        JPanel districtPanel = new JPanel();
        districtPanel.setLayout(new BoxLayout(districtPanel, BoxLayout.X_AXIS));
        faritanyPanel.add(new JLabel("District: "));
        this.add(districtPanel);

        // Bureau de vote panel
        JPanel bureauDeVotePanel = new JPanel();
        bureauDeVotePanel.setLayout(new BoxLayout(bureauDeVotePanel, BoxLayout.X_AXIS));
        faritanyPanel.add(new JLabel("Bureau de vote: "));
        this.add(bureauDeVotePanel);
    }

    public ASelector(AppContext context) {
        this.setAppContext(context);
        this.setFaritanyCombobox(new FaritanyCombobox(this));
        this.setFaritraCombobox(new FaritraCombobox(this));
        this.initUI();
    }

}
