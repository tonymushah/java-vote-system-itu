package mg.dirk.vote_system.ui.affichage1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mg.dirk.vote_system.database.tables.BureauDeVote;
import mg.dirk.vote_system.database.tables.Depute;
import mg.dirk.vote_system.database.tables.Vote;
import mg.dirk.vote_system.ui.Affichage1;
import mg.dirk.vote_system.ui.MessageBox;

public class ButtonInsertListener implements ActionListener {
    private Affichage1 affichage1;

    public void setAffichage1(Affichage1 affichage1) {
        this.affichage1 = affichage1;
    }

    public Affichage1 getAffichage1() {
        return affichage1;
    }

    public ButtonInsertListener(Affichage1 affichage1) {
        super();
        this.setAffichage1(affichage1);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Affichage1 affichage1 = this.getAffichage1();
        try {
            Depute depute = affichage1.getSelectedDepute();
            if (depute == null) {
                throw new Exception("Mila mifidy depute");
            }
            BureauDeVote bureauDeVote = affichage1.getSelectedBureauDeVote();
            if (bureauDeVote == null) {
                throw new Exception("Mila maka bureau de vote");
            }
            Vote new_vote = new Vote(Vote.nextId(affichage1.getAppContext().getDb()), depute.getId(),
                    bureauDeVote.getId(), Integer.parseInt(affichage1.getNombre().getText().trim()));
            affichage1.getAppContext().getDb().insert(new_vote);
            affichage1.triggerInsertEvent();
        } catch (Exception e) {
            MessageBox.error(e);
        }
    }

}
