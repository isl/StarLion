package gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 * DesktopPane extends JDesktopPane and implements MouseListener in order to enable
 * mouse clicking events
 * @author leonidis
 *
 */
public class DesktopPane extends JDesktopPane implements MouseListener{
	
	private static DesktopPane desktopPane;
	
	/**
	 * Private constructor in order to facilitate Singleton implementation
	 */
	private DesktopPane(){
		super();
		addMouseListener(this);
	}//end DesktopPane
	
	/**
	 * Unselect all contained internal frames in this desktop
	 */
	public void unselectAllFrames(){
		JInternalFrame[] allFrames = getAllFrames();
		
		for(int i=0;i<allFrames.length;i++){
			try {
				allFrames[i].setSelected(false);
			}
			catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}//end for i<allFrames
	}//end unselectAllFrames

	/**
	 * Not used in DesktopPane. MouseListener interface method
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used in DesktopPane. MouseListener interface method
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used in DesktopPane. MouseListener interface method
	 */
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used in DesktopPane. MouseListener interface method
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * When user selects empty space in Desktop  all frames should be unselected
	 */
	public void mouseReleased(MouseEvent e){
		unselectAllFrames();
	}//get mouseReleased
	
	/**
	 * Returns the static instance of the DesktopPane
	 * If this is the first call of this method then static instance is null and should be created
	 * @return desktopPane
	 */
	public static DesktopPane getSingleton(){
		if (desktopPane == null)
			desktopPane = new DesktopPane();		
	    return desktopPane;
	}//end getSingleton
	
	@Override
	/**
	 * Avoid hacking singleton architecture using clone method 
	 */
	public Object clone() throws CloneNotSupportedException{
	    throw new CloneNotSupportedException(); 
	}
}//end DesktopPane