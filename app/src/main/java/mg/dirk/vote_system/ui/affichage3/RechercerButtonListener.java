package mg.dirk.vote_system.ui.affichage3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mg.dirk.vote_system.database.DirkDB;
import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.ui.Affichage3;
import mg.dirk.vote_system.ui.MessageBox;
import mg.dirk.vote_system.ui.selections.DSelector;

public class RechercerButtonListener implements ActionListener {
    private Affichage3 affichage3;

    public void setAffichage3(Affichage3 affichage3) {
        this.affichage3 = affichage3;
    }

    public Affichage3 getAffichage3() {
        return affichage3;
    }

    public RechercerButtonListener(Affichage3 affichage3) {
        super();
        this.setAffichage3(affichage3);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        DSelector dSelector = this.getAffichage3().getDSelector();
        DirkDB db = this.getAffichage3().getContext().getDb();
        try {
            if (dSelector.getSelectedBureauDeVote() != null) {
                this.getAffichage3().getDeputeTable().setDeputes(dSelector.getSelectedBureauDeVote().getDeputes(db));
            } else if (dSelector.getSelectedDistrict() != null) {
                this.getAffichage3().getDeputeTable().setDeputes(dSelector.getSelectedDistrict().getDeputes(db));
            } else if (dSelector.getSelectedFaritra() != null) {
                this.getAffichage3().getDeputeTable().setDeputes(dSelector.getSelectedFaritra().getDeputes(db));
            } else if (dSelector.getSelectedFaritany() != null) {
                this.getAffichage3().getDeputeTable().setDeputes(dSelector.getSelectedFaritany().getDeputes(db));
            } else {
                this.getAffichage3().getDeputeTable().setDeputes(db.select(Depute.class));
            }
        } catch (Exception e) {
            MessageBox.error(e);
        }
    }

}
