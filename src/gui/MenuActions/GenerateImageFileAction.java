/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import graphs.Graph;
import gui.GraphInternalFrame;
import gui.InternalFrame;
import gui.MainFrame;
import gui.PNGImageFileFilter;
import gui.ProjectManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author zabetak
 */
public class GenerateImageFileAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        generateImageFile();
    }

    public boolean execute() {
        generateImageFile();
        return true;
    }
    
        /**
	 * Execute image generation for the active frame
	 */
	private void generateImageFile(){
		//Open file chooser in order to select the location of saved file
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new PNGImageFileFilter());
    	int returnVal = fc.showOpenDialog(MainFrame.getSingleton());
    	
    	if(returnVal == JFileChooser.APPROVE_OPTION){
    		 //Append .png extension to file name if it does not contains it
    		String fileName = fc.getSelectedFile().getAbsolutePath();
    		if(!fileName.contains(".png") && !fileName.contains(".PNG"))
     			fileName += ".png";
    		//Save active's frame graph as image
    		if(!getActiveGraph().saveGraphAsImage(fileName))
    			JOptionPane.showMessageDialog(null, "An error occuring while trying to save image. Try scaling down the graph.", "Error", JOptionPane.ERROR_MESSAGE);
    	}//end if returnVal == APPROVE_OPTION
	}//end generateImageFile
	
        private Graph getActiveGraph(){
            InternalFrame IF = ProjectManager.getSingleton().getActiveProject().getActiveFrame();
            if(IF instanceof GraphInternalFrame){
                return ((GraphInternalFrame)IF).getGraph();
            }else{
                return null;
            }

        }
}
