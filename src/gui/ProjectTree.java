package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyVetoException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * ProjectTree is the tree view that represents the opened projects and frames in a graphical tree-like view
 * A ProjectTree is a singleton instance and is accessed in a static way
 * @author leonidis
 */
public class ProjectTree extends JPanel implements TreeSelectionListener{

	private static ProjectTree projectTree = null;
	private JTree tree = null;
	private DefaultMutableTreeNode top = null;
	
	/**
	 * Create the static instance, set selection and scrolling parameters and finally add a treeSelectionListener to respond at user's actions
	 */
	public ProjectTree(){
		//Create Root node
		top =  new DefaultMutableTreeNode("Projects");
		//Create tree from root node
		tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        //Enable tooltips
        ToolTipManager.sharedInstance().registerComponent(tree);
        tree.setCellRenderer(new MyRenderer());

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        
        JScrollPane treeView = new JScrollPane(tree);
        treeView.setWheelScrollingEnabled(true);
        setMinimumSize(new Dimension(100, 0));
        
        setLayout(new BorderLayout());
        
        setBackground(Color.WHITE);
        setOpaque(true);
        add(treeView,BorderLayout.CENTER);
	}//end ProjectTree
	
	/**
	 * Returns the static instance of the project tree
	 * If this is the first call of this method then static instance is null and must be constructed
	 * @return ProjectTree
	 */
	public static ProjectTree getSingleton(){
		if (projectTree == null)
			projectTree = new ProjectTree();		
	    return projectTree;
	}//end getSingleton
	
	/**
	 * If a tree node gets selected the corresponding frame should be set as the selected one
	 * If node selected is a leaf node then 
	 * 	Checked if this a internalframe or a project and set selected the appopriate objects
	 * Else
	 * 	Selected node is a project and must be set as the selected one
	 */
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		DesktopPane.getSingleton().unselectAllFrames();
		if (node == null)
			return;

		if (node.isLeaf()) {
			Object userObj = node.getUserObject();
			//Check if leaf node is an instance of internalframe
			if(userObj instanceof InternalFrame){
				InternalFrame nodeInfo = (InternalFrame)userObj;
				try {
					nodeInfo.moveToFront();
					nodeInfo.activateProject();
					if(nodeInfo.isIcon()){
						nodeInfo.setMaximum(true);
					}
					nodeInfo.setSelected(true);
				}
				catch (PropertyVetoException e) {
					e.printStackTrace();
				}
			}//end if userObj instanceof InternalFrame
			else if(userObj instanceof Project){
				ProjectManager.getSingleton().setActiveProject((Project)userObj);
			}
		}//end if node.isLeaf
		else{
			Object userObj = node.getUserObject();
			if(userObj instanceof Project){
				ProjectManager.getSingleton().setActiveProject((Project)userObj);
				highlighActiveFrame(ProjectManager.getSingleton().getActiveProject(), ProjectManager.getSingleton().getActiveProject().getActiveFrame());
			}
		}
	}//end valueChanged
	
	/**
	 * Highlight the provided frame of the selected project 
	 * @param parent previously selected project
	 * @param activeFrame the one that should be set as selected for provided project
	 */
	public void highlighActiveFrame(Project parent, InternalFrame activeFrame){
		DefaultMutableTreeNode project = null;
		DefaultMutableTreeNode frame = null;
		
		for(int i=0;i<top.getChildCount();i++){
			project = (DefaultMutableTreeNode)top.getChildAt(i);
			if(((Project)project.getUserObject()).equals(parent)){
				break;
			}//end if
		}//end for
		
		for(int i=0; i<project.getChildCount();i++){
			frame = (DefaultMutableTreeNode)project.getChildAt(i);
			if(((InternalFrame)frame.getUserObject()).equals(activeFrame)){
				tree.setSelectionPath(new TreePath(frame.getPath()));
				break;
			}//end if
		}//end for
	}//end highlightActiveFrame
	
	/**
	 * Create a new node in tree for each new project
	 * @param p : new project
	 */
	public void createProjectNode(Project p) {
        DefaultMutableTreeNode project = null;
        project = new DefaultMutableTreeNode(p);
        top.add(project);
        tree.updateUI();
	}//end createProjectNode
	
	/**
	 * Remove the given project and all its children from the tree
	 * @param p : project that will be removed
	 */
	public void removeProjectNode(Project p) {
		DefaultMutableTreeNode project;
		for(int i=0;i<top.getChildCount();i++){
			project = (DefaultMutableTreeNode)top.getChildAt(i);
			//Check if project is the one that requested the removal of its children
			if(((Project)project.getUserObject()).equals(p)){
				project.removeAllChildren();
				top.remove(project);
				break;
			}//end if
		}//end for
        tree.updateUI();
	}//end removeProject
	
	/**
	 * Create a new leaf for this frame under given project subtree
	 * @param p : parent project
	 * @param f : new InternalFrame
	 */
	public void createFrameNode(Project p, InternalFrame f) {
		DefaultMutableTreeNode project;
		DefaultMutableTreeNode frame = null;
		
		for(int i=0;i<top.getChildCount();i++){
			project = (DefaultMutableTreeNode)top.getChildAt(i);
			//Check if project is the project that requested the addition of one frame in its leafs
			if(((Project)project.getUserObject()).equals(p)){
				frame = new DefaultMutableTreeNode(f);
				project.add(frame);
				//Make node visible
				tree.scrollPathToVisible(new TreePath(frame.getPath()));
				break;
			}//end if
		}//end for
		tree.updateUI();
	}//end createFrameNode
	
	/**
	 * Remove leaf that represents the given frame from its parent project subtree
	 * @param p : parent project
	 * @param f : InternalFrame that will be removed
	 */
	public void removeFrameNode(Project p, InternalFrame f) {
		DefaultMutableTreeNode project;
		
		for(int i=0;i<top.getChildCount();i++){
			project = (DefaultMutableTreeNode)top.getChildAt(i);
			//Check if project is the one that requested the removal of one frame in its leafs
			if(((Project)project.getUserObject()).equals(p)){
				for(int j=0;j<project.getChildCount();j++){
					if(((InternalFrame)((DefaultMutableTreeNode)project.getChildAt(j)).getUserObject()).equals(f)){
						project.remove((DefaultMutableTreeNode)project.getChildAt(j));
						tree.setSelectionRow(0);
						tree.updateUI();
						break;
					}//end if frame is the active one of current project
				}//end for all frames of current project
			}//end if project is the requested one
		}//end for
	}//end removeFrameNode
	
        /**
	 * Remove leaf that represents the given frame from its parent project subtree
	 * @param p : parent project
	 * @param f : InternalFrame that will be removed
	 */
        /*
	public void removeVisualInfoFrameNode(Project p, VisualInformationFrame f) {
		DefaultMutableTreeNode project;
		
		for(int i=0;i<top.getChildCount();i++){
			project = (DefaultMutableTreeNode)top.getChildAt(i);
			//Check if project is the one that requested the removal of one frame in its leafs
			if(((Project)project.getUserObject()).equals(p)){
				for(int j=0;j<project.getChildCount();j++){
					if(((VisualInformationFrame)((DefaultMutableTreeNode)project.getChildAt(j)).getUserObject()).equals(f)){
						project.remove((DefaultMutableTreeNode)project.getChildAt(j));
						tree.setSelectionRow(0);
						tree.updateUI();
						break;
					}//end if frame is the active one of current project
				}//end for all frames of current project
			}//end if project is the requested one
		}//end for
	}//end removeFrameNode
        */
	//////////////////////////////////////////////////////////////////////////////////////
	/**
	 * MyRenderer is used for displaying custom tooltips with frame's name when user points
	 * at a leaf in tree that represents an InternalFrame
	 */
	private class MyRenderer extends DefaultTreeCellRenderer{

        public MyRenderer() {
        }

        /**
         * Add a tooltip with frames name over its leaf representation
         */
        public Component getTreeCellRendererComponent( JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus){

            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            if(leaf && isInternalFrame(value)){
            	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
            	String name = ((InternalFrame)node.getUserObject()).getFrameName();
            	setToolTipText(name);
            }//end if
            return this;
        }//end getTreeCellRendererComponent

        /**
         * Checks it provided object is an InternalFrame instance
         * @param value
         * @return
         */
        protected boolean isInternalFrame(Object value) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
            if(node.getUserObject() instanceof InternalFrame)
            	return true;
            else
            	return false;
        }//isInternalFrame
    }//end class MyRenderer

}//end class ProjectTree