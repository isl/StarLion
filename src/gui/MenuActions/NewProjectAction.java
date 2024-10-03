/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.MenuActions;

import gui.Panels.NewProjectDialog;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 * The actions exectuded when we create a new project
 * @author zabetak
 */
public class NewProjectAction extends AbstractAction {


    public void actionPerformed(ActionEvent arg0) {
        newProject();
    }

    /**
     * Create a new Project
     * Request user to specify input RDF file(predefined, local, http or db) and visualization algorithm
     */
    private boolean newProject() {
        
        JDialog di = new NewProjectDialog(null,true);
        di.pack();
        di.setVisible(true);
//        JFrame fr = new NewProjectFrame();
//        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        fr.setLocationByPlatform(true);
//        fr.setAlwaysOnTop(true);
//
//        fr.setVisible(true);
//        fr.pack();
//        fr.setResizable(false);
//
        return true;
//        Object[] message = new Object[2];
//        String[] options = new String[0];
//NewProjectPanel main2 = new NewProjectPanel();
//        //Add to Optionpane all previously created panels
//        message[0] = new String("Choose the file you want to open:");
//        message[1] = main2;
//
//        int result = JOptionPane.showOptionDialog(
//                null, // the parent that the dialog blocks
//                message, // the dialog message array
//                "Open File", // the title of the dialog window
//                JOptionPane.DEFAULT_OPTION, // option type
//                JOptionPane.INFORMATION_MESSAGE, // message type
//                null, // optional icon, use null to use the default icon
//                options, // options string array, will be made into buttons
//                null // option that should be made into a default button
//                );

        //If option is yes open the file with this filename
//        if (result == 0) {
//            if(!main2.ok()){
//                newProject();
//            }
//
//        }//end if result == 0
//        return false;
    }//end openNewProject

    
}
