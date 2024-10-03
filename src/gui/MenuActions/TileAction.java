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
public class TileAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        tile();
    }

    public boolean execute() {
        tile();
        return true;
    }
    

    
	/**
	 * Tile DesktopPane frames
	 * @param desktopPane
	 */
	private void tile(){
            JDesktopPane desktopPane = MainFrame.getSingleton().getDesktop();
	    JInternalFrame[] frames = desktopPane.getAllFrames();
	    if (frames.length == 0)
	    	return;
	    
	    tile(frames, desktopPane.getBounds());
	}//end tile
        
        
	/**
	 * Tile all frames to fit in desktop
	 * @param frames
	 * @param dBounds
	 */
	private void tile(JInternalFrame[] frames, Rectangle dBounds){
	    int cols = (int)Math.sqrt(frames.length);
	    int rows = (int)(Math.ceil( ((double)frames.length) / cols));
	    int lastRow = frames.length - cols*(rows-1);
	    int width, height;
	 
	    if (lastRow == 0){
	        rows--;
	        height = dBounds.height / rows;
	    }
	    else{
	        height = dBounds.height / rows;
	        if (lastRow < cols){
	            rows--;
	            width = dBounds.width / lastRow;
	            for (int i = 0; i < lastRow; i++){
	                frames[cols*rows+i].setBounds(i*width, rows*height, width, height);
	            }//end for
	        }//end if
	    }//end else
	            
	    width = dBounds.width/cols;
	    for (int j = 0; j < rows; j++){
	        for (int i = 0; i < cols; i++){
	            frames[i+j*cols].setBounds(i*width, j*height, width, height);
	        }//end for i<cols
	    }//end for j<rows
	}//end tile
	
}
