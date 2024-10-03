/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import graphs.Graph;
import gui.GraphInternalFrame;
import gui.InternalFrame;
import gui.ProjectManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author zabetak
 */
public class Method0Action extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        method0();
    }

    public boolean execute() {
               method0();
               return true;
    }
    
    
     private void method0(){
               rank("method0",false);
               set_K();
               getActiveGraph().makeNodesInvisible(); 
     }
	/**
	 * Execute user requested ranking method
	 * @param method : ranking method name
	 * @param requestParams : if this method requires any kind of user input
	 */
	private void rank(String method, boolean requestParams){
		String params = "";
		if(requestParams)
			params = getRankParams();
		if(!params.equals("") || !requestParams){
			if(!checkProjectsNo())
				return;
			
	    	if(!((GraphInternalFrame)ProjectManager.getSingleton().getActiveProject().getActiveFrame()).rankGraph(method,params))
	    		JOptionPane.showMessageDialog(null, "Q1,Q2,Q3 parameters must summarized to 1", "Alert", JOptionPane.ERROR_MESSAGE);	
    	}//end if params == "" || !requestedParams
	}//end rank
        
        private void set_K(){
            if(!checkProjectsNo() || !checkFramesNo())
        		return;
            /*
                JLabel topKnodesL = new JLabel("Top-K Nodes");
                JLabel topKGroupNodesL = new JLabel("Top-K Group of Nodes");
             */ 
             JLabel numberOfNodes = new JLabel("Number of K:");
                
                JTextField numberOfNodesF = new JTextField(5);
                
                ButtonGroup group = new ButtonGroup();
                JRadioButton b1 = new JRadioButton("Top-K Nodes");
                JRadioButton b2 = new JRadioButton("Top-K Group Of Nodes");
        	b1.setActionCommand("topKnodes");
        	b2.setActionCommand("topKgroups");
                b1.setSelected(true);
                group.add(b1);
                group.add(b2);
                JPanel p = new JPanel();
               // p.add(topKnodesL);
                p.add(b1);
                //p.add(topKGroupNodesL);
                p.add(b2);
                p.add(numberOfNodes);
                p.add(numberOfNodesF);
                
                Object[] message = new Object[2];
                message[0] = "Select:";
                message[1] = p;
                
                String [] options = {"OK","Cancel"};
                
                int result = JOptionPane.showOptionDialog( 
    			null,                             					// the parent that the dialog blocks 
	 		    message,                                    		// the dialog message array 
	 		    "Layout Parameters",								// the title of the dialog window 
	 		    JOptionPane.DEFAULT_OPTION,							// option type 
	 		    JOptionPane.INFORMATION_MESSAGE,					// message type 
	 		    null,                                       		// optional icon, use null to use the default icon 
	 		    options,                                   			// options string array, will be made into buttons 
	 		    options[0]                                  		// option that should be made into a default button 
                 );
                
                if(result == 0){
                    
        		if(!((GraphInternalFrame)ProjectManager.getSingleton().getActiveProject().getActiveFrame()).updateGraph(group.getSelection().getActionCommand(),numberOfNodesF.getText())){
        			JOptionPane.showMessageDialog(null, "Top-K layout parameter must be an integer!", "Error", JOptionPane.ERROR_MESSAGE);
        		}else{
                            //Ask the user if he/she wants to apply the forceDirectedAlgorithm and do the appropriate actions
                            ForceDirectedQuestionAction forceDirectedQuestionMenu = new ForceDirectedQuestionAction();
                            forceDirectedQuestionMenu.execute();
                        }
        		
                }
        }
        
        
	
	/**
	 * Create GUI in order to get Rank parametes from user
	 * @return a concatenated string with all parameters
	 */
	private String getRankParams(){
		String params ="";
		////////////////////////////////////////////////////////////////////////////////////////
		//Label Components
		JLabel formula = new JLabel("Formula: ");
		JLabel q1 = new JLabel("q1: ");
    	JLabel q2 = new JLabel("q2: ");
    	JLabel q3 = new JLabel("q3: ");
    	////////////////////////////////////////////////////////////////////////////////////////
		//JTextField Components
    	JTextField formulaField = new JTextField("q1*(attrs(c) / Attrs) + q2*(Sum(R(c') / subp(c'))) + (1-q1-q2)*(Sum(R(c') / conn(c'))");
    	formulaField.setEditable(false);
    	JTextField q1Field = new JTextField(Double.toString(getActiveGraph().getGraphRanker().getQ1()));
    	JTextField q2Field = new JTextField(Double.toString(getActiveGraph().getGraphRanker().getQ2()));
    	JTextField q3Field = new JTextField(Double.toString(getActiveGraph().getGraphRanker().getQ3()));
    	////////////////////////////////////////////////////////////////////////////////////////
    	//Set JTextFields max width
    	q1Field.setColumns(10);
    	q2Field.setColumns(10);
    	q3Field.setColumns(10);
    	////////////////////////////////////////////////////////////////////////////////////////
    	//Associate each label with the appropriate JTextField
    	formula.setLabelFor(formulaField);
    	q1.setLabelFor(q1Field);
    	q2.setLabelFor(q2Field);
    	q3.setLabelFor(q3Field);
    	////////////////////////////////////////////////////////////////////////////////////////
    	//Add to panel all previously created labels
    	JPanel labelPane = new JPanel(new GridLayout(0,1));
    	labelPane.add(formula);
        labelPane.add(q1);
        labelPane.add(q2);
        labelPane.add(q3);
        ////////////////////////////////////////////////////////////////////////////////////////
    	//Add to panel all previously created textfields
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(formulaField);
        fieldPane.add(q1Field);
        fieldPane.add(q2Field);
        fieldPane.add(q3Field);        
        ////////////////////////////////////////////////////////////////////////////////////////
    	//Create and stylize the main panel
        JPanel mainPane1 = new JPanel(new BorderLayout());
        Border blackline = BorderFactory.createLineBorder(Color.black);
        mainPane1.setBorder(BorderFactory.createTitledBorder(blackline, "Algorithm Parameters"));
        mainPane1.add(labelPane, BorderLayout.LINE_START);
        mainPane1.add(fieldPane, BorderLayout.LINE_END);
	  	//Create objects that will appear in OptionPane
    	Object[] message = new Object[1];
    	//Create Button names
    	String[] options = {"OK","Cancel"};
    	//Add to Optionpane all previously created panels
    	message[0] = mainPane1;
    	
		int result = JOptionPane.showOptionDialog( 
			null,                             					// the parent that the dialog blocks 
 		    message,                                    		// the dialog message array 
 		    "Layout Parameters",											// the title of the dialog window 
 		    JOptionPane.DEFAULT_OPTION,			// option type 
 		    JOptionPane.INFORMATION_MESSAGE,	// message type 
 		    null,                                       			// optional icon, use null to use the default icon 
 		    options,                                   			// options string array, will be made into buttons 
 		    options[0]                                  		// option that should be made into a default button 
		);
    	 
    	if(result == 0)
    		params = q1Field.getText() +","+ q2Field.getText() +","+ q3Field.getText();
    	else
    		params = "";
		return params;
	}//end getRankParams
	
        
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
        
        /**
	 * Check if there are open frames for the active project
	 * @return true if number of opened frames is greater than 0, false otherwise
	 */
	private boolean checkFramesNo(){
		if(ProjectManager.getSingleton().getActiveProject().getTotalFramesNo()<=0){
    		JOptionPane.showMessageDialog(null, "There isn't any open frame to associate with this action", "Alert", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
		return true;
	}//end checkFramesNo
        
        private Graph getActiveGraph(){
            InternalFrame IF = ProjectManager.getSingleton().getActiveProject().getActiveFrame();
            if(IF instanceof GraphInternalFrame){
                return ((GraphInternalFrame)IF).getGraph();
            }else{
                return null;
            }

        }


}
