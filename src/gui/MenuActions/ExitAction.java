/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import gui.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author zabetak
 */
public class ExitAction extends AbstractAction implements MenuActionInterface {

    public void actionPerformed(ActionEvent arg0) {
        exit();
    }

    public boolean execute() {
        exit();
        return true;
    }
    
    private void exit(){
        MainFrame.getSingleton().dispatchEvent(new WindowEvent(MainFrame.getSingleton(),WindowEvent.WINDOW_CLOSING)); 
    }

}
