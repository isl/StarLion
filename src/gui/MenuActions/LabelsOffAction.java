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
public class LabelsOffAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        labelsOffMenu();
    }

    public boolean execute() {
        labelsOffMenu();
        return true;
    }
    /**
         * The Menu that is responsible for turning off the labels of the edges
         */
        private void labelsOffMenu(){
            getActiveGraph().setEdgeLabelsVisibility(false);
            getActiveGraph().getVisualGraph().updateGraph();
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
