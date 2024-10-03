/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import gui.MainFrame;
import gui.ProjectManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author zabetak
 */
public class CloseProjectAction extends AbstractAction implements MenuActionInterface {

    public void actionPerformed(ActionEvent arg0) {
        closeProject();
    }

    public boolean execute() {
        closeProject();
        return true;
    }
    
    private void closeProject(){
        if(!checkProjectsNo())
        		return;
        	
        	ProjectManager.getSingleton().removeProject(ProjectManager.getSingleton().getActiveProject(),MainFrame.getSingleton().getDesktop());
                if(ProjectManager.getSingleton().getTotalProjectsNo()!= 0){
//                    fileName = ProjectManager.getSingleton().getActiveProject().getName();
                    ProjectManager.getSingleton().setActiveProject(ProjectManager.getSingleton().getActiveProject());
                }
    }
    
    /**
	 * Check if there are open projects 
	 * @return true if number of opened projects is greater than 0, false otherwise
	 */
	private boolean checkProjectsNo(){
		if(ProjectManager.getSingleton().getTotalProjectsNo()<=0){
    		JOptionPane.showMessageDialog(null, "There isn't any open project to associate with this action", "Alert", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
		return true;
	}//end checkProjectsNo
	
    
}
