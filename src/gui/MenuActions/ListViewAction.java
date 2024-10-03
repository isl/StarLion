/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.MenuActions;

import graphs.Graph;
import gui.GraphInternalFrame;
import gui.InternalFrame;
import gui.ListViewMenu;
import gui.MainFrame;
import gui.ProjectManager;
import gui.ProjectTree;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author zabetak
 */
public class ListViewAction extends AbstractAction implements MenuActionInterface {

    public void actionPerformed(ActionEvent arg0) {
        listView();
    }

    public boolean execute() {
        listView();
        return true;
    }

    private void listView() {
        ListViewMenu m = new ListViewMenu(getActiveGraph());
        ProjectManager.getSingleton().getActiveProject().addInfoVisFrame("List View" + ProjectManager.getSingleton().getActiveProject().getTotalInfoFramesNo(), m);
        MainFrame.getSingleton().getDesktop().add(ProjectManager.getSingleton().getActiveProject().getActiveFrame());
        ProjectTree.getSingleton().highlighActiveFrame(ProjectManager.getSingleton().getActiveProject(), ProjectManager.getSingleton().getActiveProject().getActiveFrame());
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
