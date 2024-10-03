/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import gui.Project;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author zabetak
 */
public class HelpAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        helpMenu();
    }

    public boolean execute() {
        helpMenu();
        return true;
    }
    
    
        /*
         * The function responsible for the handling of the help menu
         */
        private void helpMenu(){
            InputStream in;
            BufferedReader br;
            String content = "";
            
            try{
                in = Project.class.getResourceAsStream("/helpFiles/help.txt");
                br = new BufferedReader(new InputStreamReader(in));
                    
               while(br.ready()){
                   content += br.readLine() + "\n";
                }
                
            }catch(Exception e){
                e.printStackTrace();
                return;
            }
            //The main help panel that holds all the other components
            JPanel helpPanel = new JPanel();
            //The text area that prints the help information
            JTextArea tArea = new JTextArea(content,30,40);
            //A scroll panel for the case that the help file is bigger than expected
            JScrollPane scrollPane = new JScrollPane(tArea);
            tArea.setEditable(false);
            helpPanel.add(scrollPane);
            //Create objects that will appear in OptionPane
            Object[] message = new Object[1];
	    //Create Button names
	    String[] options = {"Close"};
	    //Add to Optionpane all previously created panels
	    message[0] = helpPanel;
	    int result = JOptionPane.showOptionDialog( 
						 null,                               // the parent that the dialog blocks 
						 message,                            // the dialog message array 
						 "Help Contents", 		     // the title of the dialog window 
						 JOptionPane.DEFAULT_OPTION,	     // option type 
						 JOptionPane.PLAIN_MESSAGE,    // message type 
						 null,                               // optional icon, use null to use the default icon 
						 options,                            // options string array, will be made into buttons 
						 options[0]                          // option that should be made into a default button 
					);
        }

}
