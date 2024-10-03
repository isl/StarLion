package gui;

import javax.swing.JPanel;

/**
 * Any frame that contains visual informations about the graph must implement this interface
 * used for portability
 * 
 * @author zabetak
 */
public interface VisualInfoInterface {

    /**
     * Returns all the information needed in a JPanel
     * @return a JPanel with the neccessary components that we want to visualize
     */
    public JPanel getPanel();

}
