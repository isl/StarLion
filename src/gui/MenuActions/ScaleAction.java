/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import graphs.Graph;
import gui.GraphInternalFrame;
import gui.InternalFrame;
import gui.ProjectManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

/**
 *
 * @author zabetak
 */
public class ScaleAction extends AbstractAction implements MenuActionInterface{
    
    public void actionPerformed(ActionEvent arg0) {
        scale();
    }

    public boolean execute() {
        scale();
        return true;
    }
    
	/**
	 * Execute scaling
	 */
	private void scale(){
		//Create Slider GUI component in order to let user scale graph
    	JSlider zoom = new JSlider(20,200,100);
    	zoom.setMajorTickSpacing(30);
    	zoom.setMinorTickSpacing(10);
    	zoom.setPaintTicks(true);
    	zoom.setPaintLabels(true);
    	zoom.setSnapToTicks(true);
    	zoom.setValue(getActiveGraph().getScaleFactor());
    	
	  	//Create objects that will appear in OptionPane
    	Object[] message = new Object[1];
    	//Create Button names
    	String[] options = {"OK","Cancel"};
    	//Add to Optionpane all previously created panels
    	message[0] = zoom;
    	
    	int result = JOptionPane.showOptionDialog( 
    			null,                             					// the parent that the dialog blocks 
	 		    message,                                    		// the dialog message array 
	 		    "Layout Parameters",											// the title of the dialog window 
	 		    JOptionPane.DEFAULT_OPTION,			// option type 
	 		    JOptionPane.INFORMATION_MESSAGE,	// message type 
	 		    null,                                       			// optional icon, use null to use the default icon 
	 		    options,                                   			// options string array, will be made into buttons 
	 		    options[0]                                  		// option that should be made into a default button 
    	);
    	
    	if(result == 0){
    		if(!checkProjectsNo())
    			return;
    		
    		int scale = zoom.getValue();
    		getActiveGraph().scale(scale);
    	}//end if result == 0
	}//end scale

    
        /**
	 * Check if there are open projects 
	 * @return true if number of opened projects is greater than 0, false otherwise
	 */
	public boolean checkProjectsNo(){
		if(ProjectManager.getSingleton().getTotalProjectsNo()<=0){
    		JOptionPane.showMessageDialog(null, "There isn't any open project to associate with this action", "Alert", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
		return true;
	}//end checkProjectsNo
        
        
        
    private Graph getActiveGraph(){
            InternalFrame IF = ProjectManager.getSingleton().getActiveProject().getActiveFrame();
            if(IF instanceof GraphInternalFrame){
                return ((GraphInternalFrame)IF).getGraph();
            }else{
                return null;
            }

        }
}
