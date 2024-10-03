/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import gui.GraphInternalFrame;
import gui.MainFrame;
import gui.ProjectFileFilter;
import gui.ProjectManager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author zabetak
 */
public class SaveFramesAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        if(!checkProjectsNo() || !checkFramesNo())
        		return;
        saveFrames();
    }

    public boolean execute() {
        if(!checkProjectsNo() || !checkFramesNo())
        		return false;
        return saveFrames();
    }
    

    
	/**
	 * Save multiple frames for the active project
	 * Request user choice(s) with a JList
	 */
	private boolean saveFrames(){
		//Open file chooser in order to select the location of saved file
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ProjectFileFilter());
		int returnVal = fc.showSaveDialog(MainFrame.getSingleton());
		
		if(!(returnVal == JFileChooser.APPROVE_OPTION)) 
			return false;
		
		//Populate an array with all opened frame names that contains a graph
		int totalProjFrames = ProjectManager.getSingleton().getActiveProject().getTotalFramesNo();
                int totalInfoFrames = ProjectManager.getSingleton().getActiveProject().getTotalInfoFramesNo();
                int totalGraphFrames = totalProjFrames - totalInfoFrames;
                
		String[] frameNames = new String[totalGraphFrames];
                int k = 0;
		for(int i=0;i<totalProjFrames;i++){
			if(ProjectManager.getSingleton().getActiveProject().getFrameAt(i) instanceof GraphInternalFrame){
                            frameNames[k] = ProjectManager.getSingleton().getActiveProject().getFrameAt(i).getFrameName();
                            k++;
                        }
                }
		//Populate a list with these names in order to facilitate user's selection
		JList list = new JList(frameNames); 
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
                list.setSelectedIndex(0);
		//Add list to a scrollpane and this to another panel
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(350, 150));
     	JPanel p = new JPanel();
     	p.add(listScroller);
     	 
     	//Create objects that will appear in OptionPane
     	Object[] message = new Object[2];
     	//Create Button names
     	String[] options = {"Open","Cancel"};
     	//Add to Optionpane all previously created panels
     	 message[0] = new String("Choose the frames you want to save:");
     	 message[1] = p;
     
     	 int result = JOptionPane.showOptionDialog( 
		 		    null,                             	// the parent that the dialog blocks 
		 		    message,                            // the dialog message array 
		 		    "Save Frames", 						// the title of the dialog window 
		 		    JOptionPane.DEFAULT_OPTION,			// option type 
		 		    JOptionPane.INFORMATION_MESSAGE,	// message type 
		 		    null,                               // optional icon, use null to use the default icon 
		 		    options,                            // options string array, will be made into buttons 
		 		    options[0]                          // option that should be made into a default button 
		 		);
		
     	 if(result == 0){
     		 //Append .prj extension to file name if it does not contains it
     		 String prjFileName = fc.getSelectedFile().getAbsolutePath();
                 
     		 String tmpPrjFileName = prjFileName;
     		 if(!tmpPrjFileName.contains(".prj") && !tmpPrjFileName.contains(".prj"))
     			tmpPrjFileName += ".prj";
                 String tmpPrjFileName2 = prjFileName;
                 if(tmpPrjFileName2.contains(".prj")){
                     tmpPrjFileName2 = tmpPrjFileName2.substring(0,tmpPrjFileName2.lastIndexOf("."));
                 }
                 String plainNameWithExt;
                 int start = prjFileName.lastIndexOf(java.io.File.separator);
                 if(start < 0){
                     start = 0;
                 }
                 plainNameWithExt = prjFileName.substring(start);
                 if(!plainNameWithExt.contains(".prj")){
                     plainNameWithExt += ".prj";
                 }
     		 String savedFrame = "";
     		 String comment = "";
     		 try {
                         //Create a new directory to store the files
                         
                         boolean dirCreated = (new File(tmpPrjFileName2)).mkdir();
                         if(!dirCreated)
                             System.out.println("LE POOL");
     			 //Create a new file in order to write in it
     			 BufferedWriter out = new BufferedWriter(new FileWriter(tmpPrjFileName2+java.io.File.separator+plainNameWithExt));
                         
     			 out.write("<?xml version=\"1.0\"?>\n");
     			 out.write("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
     					 +"xmlns:project=\"www.csd.uoc.gr/~leonidis/project\">\n");
     			 out.write("\t<project:Project>\n");
     			 out.write("\t\t<project:name>output_file0</project:name>\n");
     			 
                         //Save the model in a file
                         ProjectManager.getSingleton().getActiveProject().getModel().write(tmpPrjFileName2+java.io.File.separator,ProjectManager.getSingleton().getActiveProject().getName());
     			 
                         //For each frame, add a new node in project file containing each name and user comments (if any)
     			 for(int i=0; i<list.getSelectedIndices().length; i++){
     				 String frameName = list.getModel().getElementAt(i).toString();
    
     				 //String newName = ProjectManager.getSingleton().getActiveProject().getName().replace("\\", "_");
                                 String tmpNewName = ProjectManager.getSingleton().getActiveProject().getName().replace('/', '_');
                                 String newName = tmpNewName.replace('\\', '_');
                                 
     				newName = newName.replace(".rdf", "");
     				newName = newName.replace(":", "");
     				 System.out.println("NEWNAME ="+newName);
     				 savedFrame = tmpPrjFileName2+java.io.File.separator+newName+ "_" +frameName+ ".rdf";
                                 
     				 //For current frame save its graph related info in a uniquely named file
     				 ((GraphInternalFrame)ProjectManager.getSingleton().getActiveProject().getFrameWithName(frameName)).getGraph().saveGraph(savedFrame);
            		
     				 comment = JOptionPane.showInputDialog(null, "Comment: ", "Frame : "+frameName, JOptionPane.INFORMATION_MESSAGE);
     				 if(comment == null || comment.equals(""))
     					 comment = " ";
     				 out.write("\t\t<project:Frame>\n");
     				 out.write("\t\t\t<project:frameName>" +newName+ "_" +frameName+ ".rdf"+ "</project:frameName>\n");
     				 out.write("\t\t\t<project:frameComment>" +comment+ "</project:frameComment>\n");
     				 out.write("\t\t</project:Frame>\n");
     			 }//end for i<list.getSelectedIndices
     			 out.write("\t</project:Project>\n");
     			 out.write("</rdf:RDF>");
     			 out.close();
     			 return true;
     		 }//end try
     		 catch (IOException e) {
     			 e.printStackTrace();
     			 return false;
     		 }//end catch
     	 }//end if result == 0
     	 return false;
	}//end saveFrames
        
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
}
