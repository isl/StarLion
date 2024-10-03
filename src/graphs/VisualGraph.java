package graphs;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.GraphUndoManager;

import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.event.UndoableEditEvent;

/**
 * VisualGraph extends the custom graphic's library graph object and implements the VisualGraphInterface in order to provide portability
 * @author leonidis
 * 
 */
public class VisualGraph extends JGraph implements VisualGraphInterface{

	GraphUndoManager undoManager;
	GraphModel model = new DefaultGraphModel();
	Vector<DefaultGraphCell> cells =null;
        Vector<DefaultGraphCell> insertCells = null;
        Vector<DefaultGraphCell> removeCells = null;
	GraphLayoutCache view = new GraphLayoutCache(model,new DefaultCellViewFactory(),true);
	private boolean populated = false;
	private boolean undoEnabled = true;        
	/**
	 *	Create a new visual graph
	 */
	public VisualGraph(){
		setModel(model);
		setGraphLayoutCache(view);
		setCloneable(true);
		// Enable edit without final RETURN keystroke
		setInvokesStopCellEditing(true);
		// When over a cell, jump to its default port (we only have one, anyway)
		setJumpToDefaultPort(true);
		cells = new Vector <DefaultGraphCell>();
                
                insertCells = new Vector<DefaultGraphCell>();
                removeCells = new Vector<DefaultGraphCell>();
		
		undoManager = new GraphUndoManager() {
			// Override Superclass
			public void undoableEditHappened(UndoableEditEvent e) {
				// First Invoke Superclass
				super.undoableEditHappened(e);
			}
		};
		
		this.getModel().addUndoableEditListener(undoManager);
	}//end VisualGraph

	/**
	 * Add a new graphical node in visual graph
	 * @param node Node object that will provide the graphical node
	 */
	public void addNode(Node node) {
		cells.add(node.getGraphNode());
                insertCells.add(node.getGraphNode());
	}//end addNode

    public void removeNode(Node node){
        
        cells.remove(node.getGraphNode());
        removeCells.add(node.getGraphNode());
    }
	/**
	 * Add a new graphical edge in visual graph
	 * @param edge Edge object that will provide the graphical edge
	 */
	public void addEdge(Edge edge) {
		cells.add(edge.getGraphEdge());
                insertCells.add(edge.getGraphEdge());
	}//end addEdge

    public void removeEdge(Edge edge){
        cells.remove(edge.getGraphEdge());
        removeCells.add(edge.getGraphEdge());
    }
	/**
	 * Not used in current library, Alternative edge construction
	 */
	public void addEdge(Node source, Node target, Edge edge) {
		//Not necessary for the current library. Edges are alredy connected with nodes.
	}//end addEdge
	
	/**
	 * Visualize Graph. During insertion of nodes or edges in order to make this function dynamic, a vector was used
	 * but specific library needs graphical elements should be in a DefaultGraphCell array to be displayed
	 * This function populates this array from vector
	 */
	public void visualizeGraph(){
//		DefaultGraphCell[] allCells = new DefaultGraphCell[cells.size()];
//		for(int i=0;i<cells.size();i++)
//			allCells[i] = cells.elementAt(i);
//		System.out.println("Cell Size = "+cells.size());
                eliminateInconsistencies(insertCells,removeCells);
                if(insertCells.size() != 0){
                    getGraphLayoutCache().insert(insertCells.toArray());
                    insertCells.clear();
                }
                if(removeCells.size() != 0){
                    getGraphLayoutCache().remove(removeCells.toArray());
                    removeCells.clear();
                }
//		getGraphLayoutCache().insert(allCells);
//		populated = true;
		undoManager.discardAllEdits();
	}//end visualizeGraph

    private void eliminateInconsistencies(Vector<DefaultGraphCell> ins,Vector<DefaultGraphCell> del){
        if(ins.size() == 0 || del.size() == 0)
            return;
        for(DefaultGraphCell dgc:ins){            
            if(del.remove(dgc)){
                System.out.println("Inconsistent Element="+dgc.toString());
            }
        }
    }

	/**
	 * Clear all cells of this graph
	 */
	public void clearGraph(){
		cells.clear();
	}

    public Collection<DefaultGraphCell> getGraphCells(){
        return cells;
    }
	/**
	 * Return the populated status of visual graph
	 * A visual graph is populated only if visualizeGraph was called once at least and so the  DefaultGraphCell array is populated with graphical elements
	 * @return populated : populated status of visual graph
	 */
	public boolean isPopulated(){
		return populated;
	}//end isPopulated
	
	/**
	 * Update graphical layout cache
	 */
	public void updateGraph(){
                long time = System.nanoTime();
		getGraphLayoutCache().refresh(getGraphLayoutCache().getAllViews(),false);
		updateUI();
//                drawImage(getGraphImage().getGraphics());
                time = System.nanoTime()-time;
                System.out.println("UpdateGraph TIME="+time);
	}//end updateGraph
	
	/**
	 * Return a BufferdImage of visual graph
	 * @return BufferedImage if successful completion, null otherwise 
	 */
	public BufferedImage getGraphImage(){
		try{
			BufferedImage buffImage = this.getImage(this.getBackground(),10);
			return (buffImage);
		}catch (OutOfMemoryError e){
			return null;
		}
	}//end getGraphImage
	
        /**
         * Check if the undo operations must be performed or not
         * @return true if the operations must be performed and false otherwise
         */
        public boolean isUndoEnabled(){
            return this.undoEnabled;
        }
        
	/**
	 * Return if visual graph can undo
	 * @return true if it can, false otherwise
	 */
	public boolean canUndo(){
		return undoManager.canUndo(this.getGraphLayoutCache());
	}
	
	/**
	 * Return if visual graph can redo
	 * @return true if it can, false otherwise
	 */
	public boolean canRedo(){
		return undoManager.canRedo(this.getGraphLayoutCache());
	}
	
	/**
	 * Perform undo if possible
	 */
	public void undo() {
		try {
			undoManager.undo(this.getGraphLayoutCache());
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}//undo
	
	/**
	 * Perform redo if possible
	 */
	public void redo() {
		try {
			undoManager.redo(this.getGraphLayoutCache());
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}//redo
        
        public int getSelectedNodesCount(){
            int count = 0;
            Object[] graphElem = this.getSelectionCells();
            for(int i=0;i < graphElem.length ;i++){
                if(graphElem[i] instanceof GraphNode){
                        count++;
                }
            }
            return count;
        }
        
}//end class VisualGraph