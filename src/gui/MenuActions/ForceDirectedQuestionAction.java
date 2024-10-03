/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import gui.MainFrame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author zabetak
 */
public class ForceDirectedQuestionAction extends AbstractAction implements MenuActionInterface {

    public void actionPerformed(ActionEvent arg0) {
        forceDirectedQuestionMenu();
    }
    
    public boolean execute(){
        forceDirectedQuestionMenu();
        return true;
    }
    
    /**
         * A pop-up window asking for the application of force Directed Algorithm
         */
        private void forceDirectedQuestionMenu(){
            
            Object[] message = new Object[2];
            message[0] = "Do you want to apply Force Directed Algorithm?";
            String[] options = {"Yes", "No"};

            int result = JOptionPane.showOptionDialog(
                    null, // the parent that the dialog blocks 
                    message, // the dialog message array 
                    "Apply Force Directed Placement Algorithm", // the title of the dialog window 
                    JOptionPane.DEFAULT_OPTION, // option type 
                    JOptionPane.INFORMATION_MESSAGE, // message type 
                    null, // optional icon, use null to use the default icon 
                    options, // options string array, will be made into buttons 
                    options[0] // option that should be made into a default button 
                    );

            if (result == 0) {
                ForceDirectedAction forceDirected = new ForceDirectedAction();
                forceDirected.execute();
            }
            
//            else{
//                RandomAction random = new RandomAction();
//                random.execute();
//            }
                             
        }

}
