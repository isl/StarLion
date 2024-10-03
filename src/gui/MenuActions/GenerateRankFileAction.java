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
import gui.ScoreFileFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
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
public class GenerateRankFileAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        generateScoresFile();
    }

    public boolean execute() {
        generateScoresFile();
        return true;
    }
    
    
	/**
	 * Generate score file with rankings produces by user selected methods
	 */
	private void generateScoresFile(){
		String params = "";
		//Open file chooser in order to select the location of saved file
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new ScoreFileFilter());
    	int returnVal = fc.showOpenDialog(MainFrame.getSingleton());
    	
    	if(returnVal == JFileChooser.APPROVE_OPTION){
    		String fileName = fc.getSelectedFile().getAbsolutePath();
    		//Append .scores extension to file name if it does not contains it
     		if(!fileName.contains(".scores") && !fileName.contains(".SCORES"))
     			fileName += ".scores";
     		//Request user to choose which ranking methods want to use
			String methods = getScoreFileRequestedMethods();
			if(methods.contains("method2")){
				params = getRankParams();
				if(params.equals(""))
					return;
			}//end if method.contains("method2")
			
			//Generate score file according to requested methods (and their appropriate input, if needed)
			if(!((GraphInternalFrame)ProjectManager.getSingleton().getActiveProject().getActiveFrame()).generateScoreFile(fileName,params,methods))
				JOptionPane.showMessageDialog(null, "Q1,Q2,Q3 parameters must summarized to 1", "Alert", JOptionPane.ERROR_MESSAGE);
    	}//end if returnVal == APPROVE_OPTION
	}//end generateScoresFile

        
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
	 * Create GUI in order to let user select ranking methods
	 */
	private String getScoreFileRequestedMethods(){
		String methods = "";
		//Create objects that will appear in OptionPane
    	Object[] message = new Object[2];
    	//Create Button names
    	String[] options = {"Open","Cancel"};
    	//Create Radio Buttons in order to let user select the methods he want to use
    	JRadioButton b1 = new JRadioButton("Graph Degree");
    	JRadioButton b2 = new JRadioButton("Random Surfer Explicit");
    	JRadioButton b3 = new JRadioButton("Random Surfer Inferred");
    	b1.setActionCommand("method0");
    	b1.setSelected(true);
    	b2.setActionCommand("method2");
    	b3.setActionCommand("method5");
    	//Create the main panel
    	JPanel p = new JPanel();
    	p.add(b1);
    	p.add(b2);
    	p.add(b3);
    	//Add to Optionpane all previously created panels
    	message[0] = new String("Choose Ranking Functions:");
    	message[1] = p;
    
    	int result = JOptionPane.showOptionDialog( 
    			null,                             					// the parent that the dialog blocks 
	 		    message,                                    		// the dialog message array 
	 		    "Layout",											// the title of the dialog window 
	 		    JOptionPane.DEFAULT_OPTION,			// option type 
	 		    JOptionPane.INFORMATION_MESSAGE,	// message type 
	 		    null,                                       			// optional icon, use null to use the default icon 
	 		    options,                                   			// options string array, will be made into buttons 
	 		    options[0]                                  		// option that should be made into a default button 
    	);
    	 
    	if(result == 0){
    		if(b1.isSelected())
    			methods += b1.getActionCommand() + ", ";
    		if(b2.isSelected())
    			methods += b2.getActionCommand() + ", ";
    		if(b3.isSelected())
    			methods += b3.getActionCommand();
    	}
		return methods;
	}//end getScoreFileRequestedMethods
        
        private Graph getActiveGraph(){
            InternalFrame IF = ProjectManager.getSingleton().getActiveProject().getActiveFrame();
            if(IF instanceof GraphInternalFrame){
                return ((GraphInternalFrame)IF).getGraph();
            }else{
                return null;
            }

        }
}
