/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import gui.GraphInternalFrame;
import gui.ProjectManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author zabetak
 */
public class HideAction extends AbstractAction implements MenuActionInterface{

    
    
    public void actionPerformed(ActionEvent arg0) {
        hide();
       
    }

    public boolean execute() {
        hide();
        return true;
    }
    
    private void hide(){
        ((GraphInternalFrame)ProjectManager.getSingleton().getActiveProject().getActiveFrame()).getGraph().hideSelectedNodes(); 
    }
    

}
