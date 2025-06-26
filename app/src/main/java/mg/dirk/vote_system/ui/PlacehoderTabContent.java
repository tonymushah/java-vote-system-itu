package mg.dirk.vote_system.ui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlacehoderTabContent extends JPanel {
    public PlacehoderTabContent(String placeholderText) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel(placeholderText));
    }

    public PlacehoderTabContent() {
        this("Placeholder");
    }
}
