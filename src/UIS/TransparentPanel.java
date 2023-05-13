package UIS;

import java.awt.LayoutManager;
import javax.swing.JPanel;

public class TransparentPanel extends JPanel {

    public TransparentPanel() {
        this.setOpaque(false);
    }
    
    public TransparentPanel(LayoutManager layout) {
        this.setLayout(layout);
    }

}
