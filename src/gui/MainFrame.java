package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * MainFrame is the main window of StarLion
 * @author leonidis
 *
 */
public class MainFrame extends JFrame{

	boolean 					runStandAlone;
	JSplitPane					splitPane;
	private DesktopPane 		desktop;
	private static MainFrame 	mainFrame = null;
	private MenuBar 			menuBar;
	
	/**
	 * Return desktop instance of this frame
	 * @return JDesktopPane instance
	 */
	public JDesktopPane getDesktop(){
		return desktop;
	}//end getDesktop
	
	/**
	 * Private MainFrame constructor in order to facilitate Singleton implementation
	 */
	private MainFrame(){
        
		//Call super constructor to give a title to the window.
		super("StarLion");
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		desktop = DesktopPane.getSingleton();
		desktop.putClientProperty("JDesktopPane.dragMode", "outline");
        menuBar = new MenuBar();
		setJMenuBar(menuBar.getMenuBar());
                Toolkit tk = Toolkit.getDefaultToolkit();  
                int xSize = ((int) tk.getScreenSize().getWidth());  
                int ySize = ((int) tk.getScreenSize().getHeight());  
                setSize(xSize,ySize); 
//		setBounds(20,20,800,600);
		
		//Set splitPane left, right components and divider
		splitPane.setLeftComponent(ProjectTree.getSingleton());
		splitPane.setRightComponent(desktop);
		splitPane.setDividerLocation(100);
		//Set more split panel extra options
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.0);
		splitPane.setPreferredSize(new Dimension(800, 600));
		setContentPane(splitPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		// Closing is taken care of by the following WindowListener,
		// so don't allow any default close operation.
		addWindowListener(new WindowAdapter() {
			// This window listener responds when the user clicks the window's close box by giving the
			// user a chance to change his mind
			public void windowClosing(WindowEvent evt) {
				int response = JOptionPane.showConfirmDialog(null,
						"Do you really want to quit?");
				if (response == JOptionPane.YES_OPTION) {
					dispose();
					if (runStandAlone)
						System.exit(0);
				}//end if response = YES_OPTION
			}//end windowClosing
		});
		setVisible(true);
	}//end MainFrame
	
	/**
	 * Returns the static instance of the MainFrame
	 * If this is the first call of this method then static instance is null and should be created
	 * @return MainFrame singleton instance
	 */
	public static MainFrame getSingleton(){
		if (mainFrame == null)
	        mainFrame = new MainFrame();		
	    return mainFrame;
	}//end getSingleton

	@Override
	/**
	 * Avoid hacking singleton architecture using clone method 
	 */
	public Object clone() throws CloneNotSupportedException{
	    throw new CloneNotSupportedException(); 
	}//end clone
	
	/**
	 * Get Menubar of this frame
	 * @return menubar
	 */
	public MenuBar getMenubar(){
		return menuBar;
	}//end getMenubar
	
	public static void main(String[] args){
		MainFrame.getSingleton();
	}//end main
}//end class MainFrame