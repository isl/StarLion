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
public class GraphViewAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        graphView();
    }

    public boolean execute() {
        graphView();
        return true;
    }
    
    private void graphView(){
        getActiveGraph().graphView();
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


