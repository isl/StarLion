/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.MenuActions;

import gui.InternalFrame;
import gui.MainFrame;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;

/**
 *
 * @author zabetak
 */
public class ViewTextualAction extends AbstractAction implements MenuActionInterface {

    public void actionPerformed(ActionEvent arg0) {
        viewRDFasText();
    }

    public boolean execute() {
        viewRDFasText();
        return true;
    }
    

	/**
	 * View active project as plain RDF text
	 */
	private void viewRDFasText(){
		try{			
			JInternalFrame jif = MainFrame.getSingleton().getDesktop().getSelectedFrame();
			if(jif==null){
				System.out.println("\n\nSelected frame is null\n\n");
				return;
			}
			String fileName = jif.getName();
			InternalFrame mif = new InternalFrame(fileName,null);
			mif.setSelected(true);
//			MainMemoryModel model = new MainMemoryModel(fileName);
//			String triples = model.getTriples();
//			mif.text.setText(triples);
			MainFrame.getSingleton().getDesktop().add(mif);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
	}//end viewRDFasText()
}
