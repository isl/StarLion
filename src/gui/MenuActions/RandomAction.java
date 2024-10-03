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
public class RandomAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        random();
    }

    public boolean execute() {
        random();
        return true;
    }
    
    private void random(){
        ((GraphInternalFrame)ProjectManager.getSingleton().getActiveProject().getActiveFrame()).updateGraph("random","");
    }

}
