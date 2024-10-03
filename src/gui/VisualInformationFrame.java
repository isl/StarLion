package gui;

import java.awt.event.FocusListener;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author zabetak
 */
public class VisualInformationFrame extends InternalFrame {
    

    /**
     * Create a new visualization frame with specified name and parent project
     * @param name frame's name
     * @param parent frame's parent project
     */
    public VisualInformationFrame(String name, Project parent,JPanel panel) {
        super(name,parent);
        this.add(panel);
        
    }//end InternalFrame

}
