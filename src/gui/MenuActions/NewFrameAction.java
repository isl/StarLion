/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import graphs.Graph;
import gui.GraphInternalFrame;
import gui.InternalFrame;
import gui.MainFrame;
import gui.ProjectManager;
import gui.ProjectTree;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author zabetak
 */
public class NewFrameAction extends AbstractAction implements MenuActionInterface {

    public void actionPerformed(ActionEvent arg0) {
        newVisualizationFrame(null, true);
    }

    public boolean execute() {
        newVisualizationFrame(null, true);
        return true;
    }

    
	/**
	 * Create a new Visualization Frame for the active project
         * 
         * @param containingNameSpaces - (Optional Parameter) if we want to specify directly the namespaces contained in the frame
         * @param menuActive - If we want the menu about choosing layout algorithm to appear or not
	 */

	public void newVisualizationFrame(String[] containingNameSpaces,boolean menuActive){
    	//Create objects that will appear in OptionPane
    	Object[] message = new Object[2];
    	//Create ComboBox for file names
    	//Create Button names
    	String[] options = {"Open","Cancel"};
    	////////////////////////////////////////////////////////////////////////////////////////
		//Declare and create interactive UI components
    	ButtonGroup group = new ButtonGroup();
        JLabel position = new JLabel("Starting positions");
    	JRadioButton b1 = new JRadioButton("Random");
    	JRadioButton b2 = new JRadioButton("At Origin Coordinates");
    	b1.setActionCommand("random");
    	b1.setSelected(true);
    	b2.setActionCommand("origin");
    	group.add(b1);
    	group.add(b2);
    	////////////////////////////////////////////////////////////////////////////////////////
    	//Add UI components to a common panel
    	JPanel p = new JPanel();
    	p.add(b1);
    	p.add(b2);
    	
    	//Add to Optionpane all previously created panels
    	message[0] = new String("Choose layout Algorithm:");
    	message[1] = p;
    
        int result = 0;
        if(menuActive){
            result = JOptionPane.showOptionDialog( 
    			null,                             	// the parent that the dialog blocks 
	 		    message,                            // the dialog message array 
	 		    "Layout",							// the title of the dialog window 
	 		    JOptionPane.DEFAULT_OPTION,			// option type 
	 		    JOptionPane.INFORMATION_MESSAGE,	// message type 
	 		    null,                               // optional icon, use null to use the default icon 
	 		    options,                            // options string array, will be made into buttons 
	 		    options[0]                          // option that should be made into a default button 
    			);
        }
    	if(result == 0){
    			if(!checkProjectsNo())
    				return;
    			
	    		String layout = group.getSelection().getActionCommand();
	    		String params = "";
	    		ProjectManager.getSingleton().getActiveProject().addVisualizationFrame("frame" +ProjectManager.getSingleton().getActiveProject().getFrameCounter());
                        if(containingNameSpaces != null){
                            getActiveGraph().setGraphNameSpaces(containingNameSpaces);
//                            getActiveGraph().setGraphExternalLinks(true);
                            ProjectManager.getSingleton().getActiveProject().addGraphInFrame(layout, params);
                            MainFrame.getSingleton().getDesktop().add(ProjectManager.getSingleton().getActiveProject().getActiveFrame());
                            ProjectTree.getSingleton().highlighActiveFrame(ProjectManager.getSingleton().getActiveProject(), ProjectManager.getSingleton().getActiveProject().getActiveFrame());
                                
                        }else{
                            NameSpacesPopupAction popupNamespaces = new NameSpacesPopupAction();
                            if(popupNamespaces.execute()){
                                ProjectManager.getSingleton().getActiveProject().addGraphInFrame(layout, params);
                                MainFrame.getSingleton().getDesktop().add(ProjectManager.getSingleton().getActiveProject().getActiveFrame());
                                ProjectTree.getSingleton().highlighActiveFrame(ProjectManager.getSingleton().getActiveProject(), ProjectManager.getSingleton().getActiveProject().getActiveFrame());
                                //Ask the user if he/she wants to apply the forceDirectedAlgorithm and do the appropriate actions
                                ForceDirectedQuestionAction forceDirectedQuestionMenu = new ForceDirectedQuestionAction();
                                forceDirectedQuestionMenu.execute();

                            }else{
                                ProjectManager.getSingleton().getActiveProject().removeFrame(ProjectManager.getSingleton().getActiveProject().getActiveFrame());
                            }
                        }
                        
    	}//end if result == 0
	}//end newVisualizationFrame
        
        private Graph getActiveGraph(){
            InternalFrame IF = ProjectManager.getSingleton().getActiveProject().getActiveFrame();
            if(IF instanceof GraphInternalFrame){
                return ((GraphInternalFrame)IF).getGraph();
            }else{
                return null;
            }

        }
        
        /**
	 * Check if there are open projects 
	 * @return true if number of opened projects is greater than 0, false otherwise
	 */
	private boolean checkProjectsNo(){
		if(ProjectManager.getSingleton().getTotalProjectsNo()<=0){
    		JOptionPane.showMessageDialog(null, "There isn't any open project to associate with this action", "Alert", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
		return true;
	}//end checkProjectsNo
	
}
