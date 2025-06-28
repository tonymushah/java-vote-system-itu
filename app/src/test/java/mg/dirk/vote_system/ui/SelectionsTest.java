package mg.dirk.vote_system.ui;

import org.junit.Test;

import mg.dirk.vote_system.AppContext;
import mg.dirk.vote_system.ui.selections.ASelectorTest;

public class SelectionsTest {
    private AppContext context;

    public AppContext getContext() {
        return context;
    }

    public void setContext(AppContext context) {
        this.context = context;
    }

    public SelectionsTest() {
        this.setContext(new AppContext());
    }

    @Test
    public void aSelector() {
        new ASelectorTest(this.getContext());
    }
}
