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

/**
 *
 * @author zabetak
 */
public class UnHideAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        unHide();
    }

    public boolean execute() {
        unHide();
        return true;
    }
    
    private void unHide(){
        getActiveGraph().makeNodesVisible();
        
    }
    
    private Graph getActiveGraph(){
            InternalFrame IF = ProjectManager.getSingleton().getActiveProject().getActiveFrame();
            if(IF instanceof GraphInternalFrame){
                return ((GraphInternalFrame)IF).getGraph();
            }else{
                return null;
            }

        }
}
