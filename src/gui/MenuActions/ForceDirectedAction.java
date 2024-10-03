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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author zabetak
 */
public class ForceDirectedAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        forceDirected();
    }

    public boolean execute() {
        forceDirected();
        return true;
    }

    /**
     * Execute force directed placement algorithm after requesting user input
     */
    private void forceDirected() {

        if (!checkProjectsNo()) {
            return;
        }
        String params = getForceDirectedParams();
        System.out.println("PARAMS:" + params);
        if (!params.equals("")) {
            if (!((GraphInternalFrame) ProjectManager.getSingleton().getActiveProject().getActiveFrame()).updateGraph("forceDirected", params)) {
                JOptionPane.showMessageDialog(null, "Parameters for Force-Directed must be numbers!", "Alert", JOptionPane.ERROR_MESSAGE);
            }
        }//end if result == 0	

    }//end forceDirecte

    /**
     * Create GUI in order to get Force Directed Algorithm parameters from user
     * @return a concatenated string with all needed params
     */
    private String getForceDirectedParams() {
        String params = "";
        ////////////////////////////////////////////////////////////////////////////////////////
        //Label Components
        JLabel formula = new JLabel("Formula");
        JLabel Ks = new JLabel("Stiffness of spring (Ks): ");
        JLabel Ls = new JLabel("Natural Length of spring (Ls): ");
        JLabel Ke = new JLabel("Repulsion strenght (Ke): ");
        JLabel Km = new JLabel("Magnetic field strength (Km): ");
        JLabel Iter = new JLabel("Number of iterations: ");
        JLabel Check = new JLabel("Continue request every: ");
        JLabel MaxX = new JLabel("Maximum X move: ");
        JLabel MaxY = new JLabel("Maximum Y move: ");
        JLabel MaxF = new JLabel("Minimum Force: ");

        //JLabel FsmallDiagrams = new JLabel("For small Diagrams: ");
        ////////////////////////////////////////////////////////////////////////////////////////
        //JTextField components
        JTextField formulaField = new JTextField("F(ei) = Sum(f(ej,ei)) + Sum(g(ej,ei)) + Sum(h(ej,ei))");
        formulaField.setEditable(false);
        JTextField KsField = new JTextField(Double.toString(getActiveGraph().getKs()));
        JTextField LsField = new JTextField(Double.toString(getActiveGraph().getLs()));
        JTextField KeField = new JTextField(Double.toString(getActiveGraph().getKe()));
        JTextField KmField = new JTextField(Double.toString(getActiveGraph().getKm()));
        JTextField IterField = new JTextField(Integer.toString(getActiveGraph().getMaxIterations()));
        JTextField CheckField = new JTextField(Integer.toString(getActiveGraph().getCheckStop()));
        JTextField MaxXField = new JTextField(Integer.toString(getActiveGraph().getmaxXmove()));
        JTextField MaxYField = new JTextField(Integer.toString(getActiveGraph().getmaxYmove()));
        JTextField MaxFField = new JTextField(Integer.toString(getActiveGraph().getFthres()));

        //JButton FsmallDiagramsB = new JButton();

        ////////////////////////////////////////////////////////////////////////////////////////
        //Set Actions for buttons


        /*
        Action actionForSmallDia = new AbstractAction("ForSmallDia"){
        public void actionPerformed(ActionEvent evt) {
        if("ForSmallDia".equals(evt.getActionCommand())){
        
        System.out.println("ActionHappen");
        getActiveGraph().changeFDPAparam("Ke",50000000);
        getActiveGraph().changeFDPAparam("Ls",200);
        getActiveGraph().changeFDPAparam("MaxIterations",1000);
        }
        }
        
        };
         */

        //FsmallDiagramsB.setAction(actionForSmallDia);


        //Set JTextFields max width
        KsField.setColumns(10);
        LsField.setColumns(10);
        KeField.setColumns(10);
        KmField.setColumns(10);
        IterField.setColumns(10);
        CheckField.setColumns(10);
        MaxXField.setColumns(10);
        MaxYField.setColumns(10);
        MaxFField.setColumns(10);
        ////////////////////////////////////////////////////////////////////////////////////////
        //Associate each label with the appropriate JTextField
        formula.setLabelFor(formulaField);
        Ks.setLabelFor(KsField);
        Ls.setLabelFor(LsField);
        Ke.setLabelFor(KeField);
        Km.setLabelFor(KmField);
        Iter.setLabelFor(IterField);
        Check.setLabelFor(CheckField);
        MaxX.setLabelFor(MaxXField);
        MaxY.setLabelFor(MaxYField);
        MaxF.setLabelFor(MaxFField);
        ////////////////////////////////////////////////////////////////////////////////////////
        //Associate each label with the appropriate JButton
        //FsmallDiagrams.setLabelFor(FsmallDiagramsB);

        //Add to panel all previously created labels
        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.add(formula);
        labelPane.add(Ks);
        labelPane.add(Ls);
        labelPane.add(Ke);
        labelPane.add(Km);
        labelPane.add(Iter);
        labelPane.add(Check);
        ////////////////////////////////////////////////////////////////////////////////////////
        //Add to panel all previously created textfields
        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        fieldPane.add(formulaField);
        fieldPane.add(KsField);
        fieldPane.add(LsField);
        fieldPane.add(KeField);
        fieldPane.add(KmField);
        fieldPane.add(IterField);
        fieldPane.add(CheckField);
        ////////////////////////////////////////////////////////////////////////////////////////
        //Create a new panel for extra algorithm related information
        JPanel labelPane2 = new JPanel(new GridLayout(0, 1));
        JPanel fieldPane2 = new JPanel(new GridLayout(0, 1));
        ////////////////////////////////////////////////////////////////////////////////////////
        //Add to panel all previously created components
        labelPane2.add(MaxX);
        labelPane2.add(MaxY);
        labelPane2.add(MaxF);
        fieldPane2.add(MaxXField);
        fieldPane2.add(MaxYField);
        fieldPane2.add(MaxFField);

        //Create a new panel for predefined algorithms parameters
        JPanel labelPane3 = new JPanel(new GridLayout(0, 1));
        JPanel fieldPane3 = new JPanel(new GridLayout(0, 1));
        ////////////////////////////////////////////////////////////////////////////////////////
        //Add to panel all previously created components
        //labelPane3.add(FsmallDiagrams);
        //fieldPane3.add(FsmallDiagramsB);

        //Create and stylize main panels
        JPanel mainPane1 = new JPanel(new BorderLayout());
        JPanel mainPane2 = new JPanel(new BorderLayout());
        JPanel mainPane3 = new JPanel(new BorderLayout());
        Border blackline = BorderFactory.createLineBorder(Color.black);
        //mainPane1
        mainPane1.setBorder(BorderFactory.createTitledBorder(blackline, "Algorithm Parameters"));
        mainPane1.add(labelPane, BorderLayout.LINE_START);
        mainPane1.add(fieldPane, BorderLayout.LINE_END);
        //mainPane2
        mainPane2.setBorder(BorderFactory.createTitledBorder(blackline, "Placement Parameters"));
        mainPane2.add(labelPane2, BorderLayout.LINE_START);
        mainPane2.add(fieldPane2, BorderLayout.LINE_END);
        //mainPane3
        mainPane3.setBorder(BorderFactory.createTitledBorder(blackline, "Predefined Parameters"));
        mainPane3.add(labelPane3, BorderLayout.LINE_START);
        mainPane3.add(fieldPane3, BorderLayout.LINE_END);

        //Create objects that will appear in OptionPane
        Object[] message = new Object[3];
        //Create Button names
        String[] options = {"OK", "Cancel"};
        //Add to Optionpane all previously created panels
        message[0] = mainPane1;
        message[1] = mainPane2;
        message[2] = mainPane3;

        int result = JOptionPane.showOptionDialog(
                null, // the parent that the dialog blocks 
                message, // the dialog message array 
                "Layout Parameters", // the title of the dialog window 
                JOptionPane.DEFAULT_OPTION, // option type 
                JOptionPane.INFORMATION_MESSAGE, // message type 
                null, // optional icon, use null to use the default icon 
                options, // options string array, will be made into buttons 
                options[0] // option that should be made into a default button 
                );

        if (result == 0) {
            params = KsField.getText() + "," + LsField.getText() + "," + KeField.getText() + "," + KmField.getText() + "," + IterField.getText() + "," + CheckField.getText() + "," + MaxXField.getText() + "," + MaxYField.getText() + "," + MaxFField.getText();
        } else {
            params = "";
        }
        return params;
    }//end getForceDirectedParams

    /**
     * Check if there are open projects 
     * @return true if number of opened projects is greater than 0, false otherwise
     */
    private boolean checkProjectsNo() {
        if (ProjectManager.getSingleton().getTotalProjectsNo() <= 0) {
            JOptionPane.showMessageDialog(null, "There isn't any open project to associate with this action", "Alert", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }//end checkProjectsNo

    private Graph getActiveGraph() {
        InternalFrame IF = ProjectManager.getSingleton().getActiveProject().getActiveFrame();
        if (IF instanceof GraphInternalFrame) {
            return ((GraphInternalFrame) IF).getGraph();
        } else {
            return null;
        }

    }
}
