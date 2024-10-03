package gui;

import graphs.Graph;

import gui.MenuActions.CloseProjectAction;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * InternalFrame is the visualization frame that displays the visual graph it contains
 * @author leonidis
 */
public class InternalFrame extends JInternalFrame implements InternalFrameListener, FocusListener{
	
	
	private Project		parent = null;
	private String 		name = "";
	static final int 	xOffset = 30, yOffset = 30;
        
    
    
    /**
     * Create a new visualization frame with specified name and parent project
     * @param name frame's name
     * @param parent frame's parent project
     */
    public InternalFrame(String name, Project parent) {
        super(name, 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
        
        this.parent = parent;
        this.name = name;
        //Then set the window size
        setSize(800,600);
        //Set the window's location.
        setLocation(xOffset, yOffset);

        //Add listener to this internal frame. On activation set it's parent project as
        //active
        addInternalFrameListener(this);
        this.addFocusListener(this);
        try {
			setSelected(true);
		}
        catch (PropertyVetoException e) {
			e.printStackTrace();
		}
        
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		
                
                

                
		//If i use pack, then the scrollpane shrinks to 0 
        //pack();
        setVisible(true);
    }//end InternalFrame
    
    /**
     * Get frame's name
     * @return name
     */
    public String getFrameName(){
    	return name;
    }//end getFrameName
    
    /**
     * Get frame's parent project
     * @return the frame's parent project
     */
    public Project getParentProj(){
        return parent;
    }
    
    /**
     * Not used in InternalFrame. InternalFrameListener interface method
     */
    public void internalFrameOpened(InternalFrameEvent arg0) {
	}
  
    /**
     * Not used in InternalFrame. InternalFrameListener interface method
     */
	public void internalFrameClosing(InternalFrameEvent arg0) {
	}

	/**
	 * When this frame closes remove it from project frame list
	 */
	public void internalFrameClosed(InternalFrameEvent arg0) {
		parent.removeFrame(this);
                
                if(parent.getTotalFramesNo() == 0){//If project does not have other frames close it
                    CloseProjectAction act = new CloseProjectAction();
                    act.execute();
                }else{
                    DesktopPane.getSingleton().unselectAllFrames();
                }
		
	}//end internalFrameClosed

	/**
	 * Not used in InternalFrame. InternalFrameListener interface method
	 */
	public void internalFrameIconified(InternalFrameEvent arg0) {
	}

	/**
	 * Not used in InternalFrame. InternalFrameListener interface method
	 */
	public void internalFrameDeiconified(InternalFrameEvent arg0) {
	}

	/**
	 * When this frame activates set parent's active frame this one
	 */
	public void internalFrameActivated(InternalFrameEvent arg0) {
		parent.setActiveFrame(this);
		MainFrame.getSingleton().getMenubar().updateMenus();
		ProjectTree.getSingleton().highlighActiveFrame(parent,this);
	}//end  internalFrameActivated

	/**
	 * Not used in InternalFrame. InternalFrameListener interface method
	 */
	public void internalFrameDeactivated(InternalFrameEvent arg0) {
	}
	
	@Override
	/**
	 * Return frame's name
	 */
	public String toString(){
		return name;
	}//end toString
	
	/**
	 * Activate project that contains this internal frame
	 */
	public void activateProject(){
		parent.setActiveFrame(this);
	}//end activateProject
	
	

	/**
	 * FocusListener Interface method. Activate project that contains newly focused InternalFrame
	 */
	public void focusGained(FocusEvent e) {
		activateProject();		
	}//end focusGained

	/**
	 * Not used in InternalFrame. FocusListener Interface method
	 */
	public void focusLost(FocusEvent e) {
	}//end focusLost
        
        /**
         * 
         * @return the center of the frame
         */
        public Point getCenterOfFrame(){
            return new Point(getWidth() / 2, getHeight() / 2);
        }
}//end class InternalFrame

