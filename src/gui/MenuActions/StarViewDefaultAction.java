/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.MenuActions;

import graphs.Graph;
import gui.GraphInternalFrame;
import gui.InternalFrame;
import gui.ProjectManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author zabetak
 */
public class StarViewDefaultAction extends AbstractAction implements MenuActionInterface {

    public void actionPerformed(ActionEvent arg0) {
        starView();
    }

    public boolean execute() {
        return starView();
        
    }

    private boolean starView() {
        getActiveGraph().setStarViewRadius(1);
        getActiveGraph().setStarViewIndirectEdgesAppearing(false);

        Point cOfWindow = null;
        int width = ProjectManager.getSingleton().getActiveProject().getActiveFrame().getWidth();
        int heigth = ProjectManager.getSingleton().getActiveProject().getActiveFrame().getHeight();
        cOfWindow = new Point(width / 2, heigth / 2);

        getActiveGraph().activateStarGraph();
        getActiveGraph().executeStarView();
        getActiveGraph().forceDirectedPlacement(null,true);
        return true;
    }

    private Graph getActiveGraph() {
        InternalFrame IF = ProjectManager.getSingleton().getActiveProject().getActiveFrame();
        if (IF instanceof GraphInternalFrame) {
            return ((GraphInternalFrame) IF).getGraph();
        } else {
            return null;
        }

    }
}
