/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

/**
 * The methods that an Action of the menu must support
 * @author zabetak
 */
public interface MenuActionInterface {
    /**
     * Directly executes the action wihtout waiting for the action event
     * 
     * @return true if the action is executed correctly and false otherwise
     */
    public boolean execute();
}
