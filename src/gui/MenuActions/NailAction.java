/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import graphs.Graph;
import graphs.GraphNode;
import graphs.Node;
import gui.GraphInternalFrame;
import gui.InternalFrame;
import gui.ProjectManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author zabetak
 */
public class NailAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        nail();
    }

    public boolean execute() {
        nail();
        return true;
    }
    
    private void nail(){
        Object[] nodes = getActiveGraph().getVisualGraph().getSelectionCells();
        	for(int i=0;i<nodes.length;i++)
        		((Node)(((GraphNode)nodes[i]).getParentNode())).setNailed(true);
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
