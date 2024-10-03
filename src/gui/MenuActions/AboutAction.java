/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author zabetak
 */
public class AboutAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        aboutMenu();
    }

    public boolean execute() {
        aboutMenu();
        return true;
    }
    

        
        /* The function responsible for handling the about menu */
        private void aboutMenu(){
            ImageIcon baseIcon = createImageIcon("/helpFiles/lion.jpg", "A lion pic");
            JLabel iconLabel = new JLabel(baseIcon);
            JLabel textLabel = new JLabel("StarLion v1.0",JLabel.CENTER);
            JLabel textLabel2 = new JLabel("The library that was used for graph drawing can be found at http://www.jgraph.com/",JLabel.CENTER);
            
            JPanel aboutPanel = new JPanel(new GridLayout(0,1));
            aboutPanel.add(iconLabel);
            aboutPanel.add(textLabel);
            aboutPanel.add(textLabel2);
            //Create objects that will appear in OptionPane
            Object[] message = new Object[1];
	    //Create Button names
	    String[] options = {"Close"};
	    //Add to Optionpane all previously created panels
	    message[0] = aboutPanel;
	    int result = JOptionPane.showOptionDialog( 
						 null,                               // the parent that the dialog blocks 
						 message,                            // the dialog message array 
						 "About", 		     // the title of the dialog window 
						 JOptionPane.DEFAULT_OPTION,	     // option type 
						 JOptionPane.PLAIN_MESSAGE,    // message type 
						 null,                               // optional icon, use null to use the default icon 
						 options,                            // options string array, will be made into buttons 
						 options[0]                          // option that should be made into a default button 
					);
            
        }
        
        
        //Ready made function taken from the internet that creates an Image Icon
        private ImageIcon createImageIcon(String path,
                                           String description) {
                java.net.URL imgURL = getClass().getResource(path);
                if (imgURL != null) {
                      return new ImageIcon(imgURL, description);
                } else {
                       System.err.println("Couldn't find file: " + path);
                      return null;
                }
        }
    
    
}
