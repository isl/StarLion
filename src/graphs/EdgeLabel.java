/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphs;

/**
 * A more sophisticated way for edge-labels that permit changes in the
 * visibility of the label
 * 
 * @author zabetak
 */
public class EdgeLabel {
    private boolean visible = true;
    private String  edgeName;
    
    public EdgeLabel(String name){
        this.edgeName = name;
    }
    
    /**
     * Sets the visibility of the label
     * @param visibility
     */
    public void setVisible(boolean visibility){
        this.visible = visibility;
    }
    
    /**
     * Returns the label as a string depending on the visibility value
     * 
     * @return - a string representation of the label
     */
    public String toString(){
        String retString;
        if(visible){
            retString = edgeName;
        }else{
            retString = null;
        }
        
        return retString;
    }
}
