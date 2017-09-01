package MindcrackDefence;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainFrame extends JFrame {
    JFrame frame = this;
    public mainFrame() {
                Container container = frame.getContentPane();
                Dimension startd = new Dimension(800,450);
                container.setPreferredSize(startd);
                frame.setLocation(200, 200);
                frame.setResizable(true);
        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.add(new mainPanel(frame));              
                frame.setTitle("Mindcrack Defence");
                frame.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/sprites/icon.png"));
                frame.pack();
                Robot robot = null;
                try {
                    robot = new Robot();
                } catch (AWTException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
    	}
	public static void main(String[] args) {
                new mainFrame();
        } 
}
        
