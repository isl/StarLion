/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.MenuActions;

import graphs.Graph;
import gui.GraphInternalFrame;
import gui.InternalFrame;
import gui.Panels.MstatisticsPanel;
import gui.ProjectManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author zabetak
 */
public class MstatisticsAction extends AbstractAction implements MenuActionInterface {

    public void actionPerformed(ActionEvent arg0) {
        mstatistics();
    }

    public boolean execute() {
        mstatistics();
        return true;
    }

    /**
         * A pop-up window asking for the application of force Directed Algorithm
         */
        private void mstatistics(){

            Object[] message = new Object[1];
            message[0] = new MstatisticsPanel(getActiveGraph());
            String[] options = {"Close"};

            int result = JOptionPane.showOptionDialog(
                    null, // the parent that the dialog blocks
                    message, // the dialog message array
                    "Metric Statistics", // the title of the dialog window
                    JOptionPane.DEFAULT_OPTION, // option type
                    JOptionPane.INFORMATION_MESSAGE, // message type
                    null, // optional icon, use null to use the default icon
                    options, // options string array, will be made into buttons
                    options[0] // option that should be made into a default button
                    );



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
