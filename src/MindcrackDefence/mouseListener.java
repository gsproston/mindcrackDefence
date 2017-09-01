package MindcrackDefence;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.util.ArrayList;

public class mouseListener implements MouseListener {
    mainPanel panelClass;
    public mouseListener(mainPanel newPanel) {
        panelClass = newPanel;
        panelClass.panel.addMouseListener(this);
    }
    public void mousePressed(MouseEvent e) {
        panelClass.click();
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    }
}
