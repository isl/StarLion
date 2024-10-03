/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import gui.MainFrame;
import gui.Project;
import gui.ProjectFileFilter;
import gui.ProjectManager;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author zabetak
 */
public class OpenProjectAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        openProject();
    }

    public boolean execute() {
        return openProject();
    }

    
	/**
	 *	Open a saved project
	 *	Open a file chooser to browse to the desired project file
	 */
	private boolean openProject(){
		//Stores information regarding database connection needed to load this namespace
		boolean dbConnectionNeeded = false;
		////////////////////////////////////////////////////////////////////////////////////////
		//Open a file chooser with a custom filter (.prj) in order to facilitate selection
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        	fc.setFileFilter(new ProjectFileFilter());
        	int returnVal = fc.showOpenDialog(MainFrame.getSingleton());
    	
    	if(returnVal == JFileChooser.APPROVE_OPTION){
    		String prjFileName = fc.getSelectedFile().getAbsolutePath();
    		
    		try{
    			org.w3c.dom.Node projectNode = null, frameNode = null;
    			
    			//Open and parse selected file in order to retrieve all frames saved in it
    			File file = new File(prjFileName);

    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    			DocumentBuilder db = dbf.newDocumentBuilder();
    			Document doc = db.parse(file);
    			doc.getDocumentElement().normalize();
    			//Select root element
    			NodeList projectNodes = doc.getElementsByTagName("project:Project");
    			projectNode = projectNodes.item(0);
    			//Select all frame nodes stored in this file 
    			NodeList frameNodes = doc.getElementsByTagName("project:Frame");
    			if(projectNode.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE)
    				return false;
    			
				String projectName = ((Element)projectNode).getElementsByTagName("project:name").item(0).getChildNodes().item(0).getNodeValue();
				
                                if(((Element)projectNode).getElementsByTagName("project:dbConnectionNeeded").item(0).getChildNodes().item(0).getNodeValue().equals("true"))
					dbConnectionNeeded = true;
				
				//Create a table that will hold retrieved frame names and commends
				JTable table = null;
				String[] columnNames = {"Frame Name", "Frame Comment"};
				Object[][] data = new Object[frameNodes.getLength()][2];
				Project proj = new Project(file.getParent()+java.io.File.separator+projectName);
                                //MANY THINGS  TO CORRECT IN ORDER TO WORK PROPERLY
				//Add this new project to project manager iff it is not already opened 
				if(ProjectManager.getSingleton().addProject(proj, true)){
					//Add a table row for every frame retrieved from project file
					for(int i=0;i<frameNodes.getLength();i++){
						frameNode = frameNodes.item(i);
						if(frameNode.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE)
							continue;
						
						String frameName = ((Element)frameNode).getElementsByTagName("project:frameName").item(0).getChildNodes().item(0).getNodeValue();
						String frameComment = ((Element)frameNode).getElementsByTagName("project:frameComment").item(0).getChildNodes().item(0).getNodeValue();
						
                                                data[i][0] = frameName;
						data[i][1] = frameComment;
                                                
					}//end for i < frameNodes.getLength
					
					//Populate table with retrieved data and add to it a scroll pane 
					table = new JTable(data, columnNames);
					table.setFillsViewportHeight(true);
					JScrollPane scrollPane = new JScrollPane(table);
					
					//Create objects that will appear in OptionPane
					Object[] message = new Object[2];
			    	//Create Button names
					String[] options = {"Open","Cancel"};
					//Add to Optionpane all previously created panels
					message[0] = new String("Choose the frames you want to open:");
				    message[1] = scrollPane;
				     
				    int result = JOptionPane.showOptionDialog( 
						 		    null,                             	// the parent that the dialog blocks 
						 		    message,                            // the dialog message array 
						 		    "Open Frames", 						// the title of the dialog window 
						 		    JOptionPane.DEFAULT_OPTION,			// option type 
						 		    JOptionPane.INFORMATION_MESSAGE,	// message type 
						 		    null,                               // optional icon, use null to use the default icon 
						 		    options,                            // options string array, will be made into buttons 
						 		    options[0]                          // option that should be made into a default button 
						 			);
						
				    if(result==0){
				    	 int[]  selectedRows = table.getSelectedRows();
				    	 for(int i=0; i < selectedRows.length; i++){
				    		 ProjectManager.getSingleton().getActiveProject().restoreVisualizationFrame(
				    				 																	"frame" +ProjectManager.getSingleton().getActiveProject().getFrameCounter(),
				    				 																	file.getParentFile().getAbsolutePath()+java.io.File.separator+data[selectedRows[i]][0].toString(),
				    				 																	true
				    				 																	);
				    		 MainFrame.getSingleton().getDesktop().add(ProjectManager.getSingleton().getActiveProject().getActiveFrame());
				    	 }//end for
				    }//end if result == 0
	         		return true;
         		 }//end if project created successfully. No attempt to reopen a model
         		 else{
         		     JOptionPane.showMessageDialog(null, "Project : "+projectName+" is already open!", "Alert", JOptionPane.ERROR_MESSAGE);
         		     return false;
         		 }//end else
			}//end try
            catch (Exception e){
            	e.printStackTrace();
            	return false;
			}//end catch
    	}//end if retval == APPROVE_OPTION
    	return false;
	}//end openProject
}
