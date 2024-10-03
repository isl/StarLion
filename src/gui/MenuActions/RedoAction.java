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
public class RedoAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        redo();
    }

    public boolean execute() {
        redo();
        return true;
    }
    
    private void redo(){
        getActiveGraph().redo();
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

