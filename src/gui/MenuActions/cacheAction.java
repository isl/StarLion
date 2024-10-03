/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import gui.Panels.cacheDialog;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 *
 * @author zabetak
 */
public class cacheAction extends AbstractAction implements MenuActionInterface{

    public void actionPerformed(ActionEvent arg0) {
        cache();
    }

    public boolean execute() {
        
        return cache();
    }

    private boolean cache(){
        JDialog ca = new cacheDialog(null,true);
        ca.pack();
        ca.setVisible(true);
//        JFrame fr = new NewProjectFrame();
//        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        fr.setLocationByPlatform(true);
//        fr.setAlwaysOnTop(true);
//
//        fr.setVisible(true);
//        fr.pack();
//        fr.setResizable(false);
//
        return true;
    }
}
