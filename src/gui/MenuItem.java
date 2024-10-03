package gui;

import javax.swing.JMenuItem;

/**
 * MenuItem class that extends JMenuItem and provides extra functionality regarding
 * items status (enable/disable) according to specific dependencies. Do not allow
 * selection of an item if some precondition are not ment, e.g. Do not allow closing
 * a project if there is not any open. 
 * @author leonidis
 *
 */
public class MenuItem extends JMenuItem{
	
	private boolean shouldEnable = false;
	private String dependencies = "";
	
	/**
	 * Create a new MenuItem with specified name
	 * @param name
	 */
	public MenuItem(String name) {
		super(name);
		this.setName(name);
	}//end MenuItem
	
	/**
	 * Set enable status of this MenuItem
	 * @param b
	 */
	public void setShouldEnable(boolean b){
		this.shouldEnable = b;
	}//end setShouldEnable
	
	/**
	 * Return enable status of this MenuItem
	 * @return status
	 */
	public boolean getShouldEnable(){
		return shouldEnable;
	}//end getShouldEnable
	
	/**
	 * Set dependencies of this MenuItem
	 * @param dependencies
	 */
	public void setDependencies(String dependencies){
		this.dependencies = dependencies;
	}//end setDependencies
	
	/**
	 * Get dependencies of this MenuItem
	 * @return dependencies
	 */
	public String getDependencies(){
		return dependencies;
	}//end getDependencies
}//end MenuItem