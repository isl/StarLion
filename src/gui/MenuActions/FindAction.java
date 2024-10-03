/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import graphs.Graph;
import gui.GraphInternalFrame;
import gui.InternalFrame;
import gui.ProjectManager;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author zabetak
 */
public class FindAction extends AbstractAction implements MenuActionInterface{

    
        /* The function responsible for handling the find menu */
        private void  findMenu(){
            //Create Button names
            JTextField findField = new JTextField(15);
            JLabel findFieldLabel = new JLabel("Find:");
            JPanel findPanel = new JPanel();
            
            
            
            ButtonGroup group = new ButtonGroup();
            JRadioButton b1 = new JRadioButton("Class");
            JRadioButton b2 = new JRadioButton("Property");
            
            b1.setActionCommand("classFind");
            b2.setActionCommand("propertyFind");
            group.add(b1);
            group.add(b2);
            
            b1.setSelected(true);
            
            findFieldLabel.setLabelFor(findField);
            
            findPanel.add(findFieldLabel);
            findPanel.add(findField);
            findPanel.add(b1);
            findPanel.add(b2);
            
            //Create objects that will appear in OptionPane
            Object[] message = new Object[1];
	    String[] options = {"Find","Close"};
            //Add to Optionpane all previously created panels
	    message[0] = findPanel;
            int result = JOptionPane.showOptionDialog( 
						 null,                               // the parent that the dialog blocks 
						 message,                            // the dialog message array 
						 "Find",         		     // the title of the dialog window 
						 JOptionPane.DEFAULT_OPTION,	     // option type 
						 JOptionPane.PLAIN_MESSAGE,    // message type 
						 null,                               // optional icon, use null to use the default icon 
						 options,                            // options string array, will be made into buttons 
						 options[0]                          // option that should be made into a default button 
					);
            if(result == 0){
                Hashtable<String,Point> findResults = null;
                
                findResults = getActiveGraph().find(findField.getText(),group.getSelection().getActionCommand());
                
                if(findResults.size() == 0){
                     JOptionPane.showMessageDialog(null,"Couldn't find "+findField.getText(), "Alert", JOptionPane.ERROR_MESSAGE);
                }else if(findResults.size() == 1){
                    Iterator it = findResults.values().iterator();
                    findOperations((Point)it.next());
                }else{
                    JPanel resultPanel = new JPanel();
                    
                    JList resultList = new JList(findResults.keySet().toArray());
                    
                    resultList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    resultList.setLayoutOrientation(JList.VERTICAL);
                    resultList.setVisibleRowCount(-1);
                    resultList.setSelectedIndex(0);
                    //Add list to a scrollpane and this to another panel
                    JScrollPane listScroller = new JScrollPane(resultList);
                    listScroller.setPreferredSize(new Dimension(350, 150));
                    
                    resultPanel.add(listScroller);
                    //Create objects that will appear in OptionPane
                     Object[] message1 = new Object[1];
                     String[] options1 = {"Select","Cancel"};
                     //Add to Optionpane all previously created panels
                     message1[0] = resultPanel;
                     int result1 = JOptionPane.showOptionDialog( 
						 null,                               // the parent that the dialog blocks 
						 message1,                            // the dialog message array 
						 "Results",         		     // the title of the dialog window 
						 JOptionPane.DEFAULT_OPTION,	     // option type 
						 JOptionPane.PLAIN_MESSAGE,    // message type 
						 null,                               // optional icon, use null to use the default icon 
						 options1,                            // options string array, will be made into buttons 
						 options1[0]                          // option that should be made into a default button 
					);
                     if(result1 == 0){
                         Point p = findResults.get(resultList.getSelectedValue());
                         findOperations(p);
                     }
                    
                }
            }
            
        }
        
        /* Executes the operations that have to be done when find is executed 
         * a)Move viewport(Scrollbars)
         * b)Move mouse 
         */
        
        private void findOperations(Point p){
                    Robot robot = null;
                    JScrollPane scrP = null;
                    Component cmp[] =  ProjectManager.getSingleton().getActiveProject().getActiveFrame().getContentPane().getComponents(); 
                    
                    int i = 0;
                    //Find which component is the JScrollPane
                    while(i < cmp.length){
                        if(cmp[i] instanceof JScrollPane){
                           scrP = (JScrollPane) cmp[i];
                           
                        }
                        i++;
                    }
                     
                     
                   
                    Point internalFrameLocation = ProjectManager.getSingleton().getActiveProject().getActiveFrame().getContentPane().getLocationOnScreen(); 
                    
                    int bI = scrP.getHorizontalScrollBar().getBlockIncrement();
                    
                    try {
                        robot = new Robot();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    
                    
                    Rectangle vpRec = null;
                    //Move the scrollbars in relationship with the given point p
                    vpRec = scrP.getViewport().getViewRect();
                    if(p.x < vpRec.x ){
                        while(vpRec.x > p.x){
                            scrP.getHorizontalScrollBar().setValue(scrP.getHorizontalScrollBar().getValue()-bI*20);
                            vpRec = scrP.getViewport().getViewRect();
                        }
                    }else{
                        while(vpRec.x+vpRec.width < p.x){
                            scrP.getHorizontalScrollBar().setValue(scrP.getHorizontalScrollBar().getValue()+bI*20);
                            vpRec = scrP.getViewport().getViewRect();
                       }
                    }
                    if(p.y < vpRec.y ){
                        while(vpRec.y > p.y){
                            scrP.getVerticalScrollBar().setValue(scrP.getVerticalScrollBar().getValue()-bI*20);
                            vpRec = scrP.getViewport().getViewRect();
                       }
                    }else{
                        while(vpRec.y+vpRec.height < p.y){
                            scrP.getVerticalScrollBar().setValue(scrP.getVerticalScrollBar().getValue()+bI*20);
                            vpRec = scrP.getViewport().getViewRect();
                        }
                    }
                    robot.mouseMove(internalFrameLocation.x + p.x -vpRec.x,
                                    internalFrameLocation.y + p.y -vpRec.y);
                    
                   
        }
        
        private Graph getActiveGraph(){
            InternalFrame IF = ProjectManager.getSingleton().getActiveProject().getActiveFrame();
            if(IF instanceof GraphInternalFrame){
                return ((GraphInternalFrame)IF).getGraph();
            }else{
                return null;
            }

        }

    public void actionPerformed(ActionEvent arg0) {
        findMenu();
    }

    public boolean execute() {
        findMenu();
        return true;
    }
        
}
