/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.MenuActions;

import graphs.Graph;
import gui.GraphInternalFrame;
import gui.InternalFrame;
import gui.ProjectManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author zabetak
 */
public class PropertiesAction extends AbstractAction implements MenuActionInterface {

    public void actionPerformed(ActionEvent arg0) {
        properties();
    }

    public boolean execute() {
        return properties();
        
    }

    private boolean properties() {
        Object[] message = new Object[2];
        JTabbedPane tabbedpane = new JTabbedPane();

        JPanel starViewTab = new JPanel();
        JLabel radiusFieldLabel = new JLabel("StarView Radius:");
        JTextField radiusField = new JTextField(1);
        JLabel indirectEdgesBoxLabel = new JLabel("Show Only Direct Edges:");
        JCheckBox indirectEdgesBox = new JCheckBox();

        radiusFieldLabel.setLabelFor(radiusField);
        radiusField.setText("1");

        indirectEdgesBoxLabel.setLabelFor(indirectEdgesBox);
        indirectEdgesBox.setSelected(true);

        starViewTab.add(radiusFieldLabel);
        starViewTab.add(radiusField);
        starViewTab.add(indirectEdgesBoxLabel);
        starViewTab.add(indirectEdgesBox);

        //Create the size chooser panel
        JPanel sizeChooseTab = new JPanel();
        JLabel widthFieldLabel = new JLabel("Node's width:");
        JLabel heigthFieldLabel = new JLabel("Node's heigth:");

        JTextField widthField = new JTextField(5);
        widthFieldLabel.setLabelFor(widthField);
        widthField.setText((new Integer(getActiveGraph().getGraphNodesWidth())).toString());


        JTextField heigthField = new JTextField(5);
        heigthFieldLabel.setLabelFor(heigthField);
        heigthField.setText((new Integer(getActiveGraph().getGraphNodesHeigth())).toString());

        sizeChooseTab.add(widthFieldLabel);
        sizeChooseTab.add(widthField);
        sizeChooseTab.add(heigthFieldLabel);
        sizeChooseTab.add(heigthField);

        //Create the color chooser panel
        JColorChooser coch = new JColorChooser();

        JComboBox nameSpaceSelector = new JComboBox(getActiveGraph().getGraphNamespaces().toArray());
        nameSpaceSelector.setSelectedIndex(0);

        JPanel colorChPanel = new JPanel();

        colorChPanel.add(coch);
        colorChPanel.add(nameSpaceSelector);

        //Add panels in the main tabbed panel
        tabbedpane.insertTab("Color Of Nodes", null, colorChPanel, "Choose the color for the nodes", 0);
        tabbedpane.insertTab("Size Of Nodes", null, sizeChooseTab, "Select the size for the nodes", 1);
        tabbedpane.insertTab("StarView", null, starViewTab, "StarView Mode Properties", 2);


        String[] options = {"Apply", "Cancel"};
        message[0] = "Choose color for nodes";
        message[1] = tabbedpane;

        int result = JOptionPane.showOptionDialog(
                null, // the parent that the dialog blocks 
                message, // the dialog message array 
                "Properties", // the title of the dialog window 
                JOptionPane.DEFAULT_OPTION, // option type 
                JOptionPane.INFORMATION_MESSAGE, // message type 
                null, // optional icon, use null to use the default icon 
                options, // options string array, will be made into buttons 
                options[0] // option that should be made into a default button 
                );



        if (result == 0) {
            int tabIndex = tabbedpane.getSelectedIndex();
            switch (tabIndex) {
                case 0:
                    getActiveGraph().setGraphGradientColor(coch.getSelectionModel().getSelectedColor(), nameSpaceSelector.getSelectedItem().toString());
                    break;
                case 1:
                    int width = 0;
                    int heigth = 0;

                    width = Integer.parseInt(widthField.getText());
                    heigth = Integer.parseInt(heigthField.getText());
                    System.out.println("Width = " + width);
                    System.out.println("Heigth=" + heigth);
                    getActiveGraph().setNodesDefaultSize(width, heigth);

                    break;
                case 2:
                    int radius = 1;
                    radius = Integer.parseInt(radiusField.getText());
                    System.out.println("StarView Radius = " + radius);
                    getActiveGraph().setStarViewRadius(radius);
                    getActiveGraph().setStarViewIndirectEdgesAppearing(!indirectEdgesBox.isSelected());
            }


            return true;
        } else {
            return false;
        }
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
