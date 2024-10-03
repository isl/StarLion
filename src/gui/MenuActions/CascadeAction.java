/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import gui.MainFrame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author zabetak
 */
public class CascadeAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        cascade();
    }

    public boolean execute() {
        cascade();
        return true;
    }
    
    
	/**
	 * Cascade DesktopPane frames
	 * @param desktopPane
	 */
	private static void cascade(){
            JDesktopPane desktopPane = MainFrame.getSingleton().getDesktop();
	    JInternalFrame[] frames = desktopPane.getAllFrames();
	    if (frames.length == 0)
	    	return;
	 
	    cascade(frames, desktopPane.getBounds(), 16);
	}//end cascade
	
	/**
	 * Cascade all frames in desktop
	 * @param frames
	 * @param dBounds
	 * @param separation
	 */
	private static void cascade(JInternalFrame[] frames, Rectangle dBounds, int separation){
	    int margin = frames.length*separation + separation;
	    int width = dBounds.width - margin;
	    int height = dBounds.height - margin;
	    for ( int i = 0; i < frames.length; i++){
	    	frames[i].setBounds(i*separation, i*separation, width, height);
	    }//end for i<frames.length
	}//end cascade

}
