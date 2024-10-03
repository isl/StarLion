package gui;

import gr.forth.ics.rdfsuite.swkm.model.impl.RDF_Model;
import gr.forth.ics.swkmclient.Client;
import java.util.ArrayList;

import javax.swing.JDesktopPane;

/**
 * ProjectManager is responsible for holding all opened projects.
 * A ProjectManager is a singleton instance and it is accessed in a static way.
 * Contains an projectList with all opened projects
 * @author leonidis
 */
public class ProjectManager {

	private static ProjectManager manager = null;
	private ArrayList<Project> projectList = new ArrayList<Project>();
	private Project activeProject = null;
	private int totalProjects = 0;
	
	/**
	 * Private Empty constructor which overrides the default constructor in order to make singleton possible 
	 */
	private ProjectManager(){
	}//end ProjectManager

	/**
	 * Returns the static instance of the project manager.
	 * If this is the first call of this method then static instance is null and should be created
	 * @return ProjectManager
	 */
	public static ProjectManager getSingleton(){
		if (manager == null)
	        manager = new ProjectManager();		
	    return manager;
	}//end getSingleton

	/**
	 * Avoid hacking singleton architecture using clone method
	 */
	public Object clone() throws CloneNotSupportedException{
	    throw new CloneNotSupportedException(); 
	}//end clone
	
	/**
	 * Return the active project of all opened projects
	 * @return active Project
	 */
	public Project getActiveProject(){
		return projectList.get(projectList.indexOf(activeProject));
	}//end getActiveProject
	
	/**
	 * Set the given project as the active project 
	 * @param p : new active frame
	 */
	public void setActiveProject(Project p){
		activeProject = p;
		MainFrame.getSingleton().getMenubar().updateMenus();
	}
	
	
	/**
	 * Adds a new project
	 * @param p : the project to be added
	 * @param isFromLoad : true if the file that we want to add in the project is from load and false if we have a new project
         * @return true or false : if project is created successfully
         * 
	 */
	public boolean addProject(Project p,boolean isFromLoad){
		
		//Before Project creation check if this model is already open
		for(int i=0;i<projectList.size();i++){
			if(projectList.get(i).getName().equals(p.getName())){
				System.out.println("This project already exists...");
				return false;
			}//end if
		}//end for
			
		projectList.add(totalProjects, p);
		setActiveProject(p);
		totalProjects++;
		ProjectTree.getSingleton().createProjectNode(p);
//                boolean popResult = false;
//                popResult = true; 
////                        p.populateModel();
//		if(popResult){
//                    return true;
//                }else{
//                    return false;
//                }
                return true;
	}//end addProject
	
        
	/**
	 * Remove given project from project list
	 * @param p : the project to be removed
	 * @param desktop : the desktop that contains the frames for this project
	 */
	public boolean removeProject(Project p, JDesktopPane desktop){
		projectList.remove(p);
		totalProjects--;
		
		p.closeAllFrames(desktop);
		desktop.updateUI();
		
		ProjectTree.getSingleton().removeProjectNode(p);
		
		if(!projectList.isEmpty())
			setActiveProject(projectList.get(0));
		else
			setActiveProject(null);
		
		return true;
	}//end removeProject
	
	/**
	 * Returns the total number of projects
	 * @return totalProjects : the total number of opened projects
	 */
	public int getTotalProjectsNo(){
		return totalProjects;
	}//end getTotalProjectsNo

}//end class MainManager