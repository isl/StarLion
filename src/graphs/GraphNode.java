package graphs;


import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;


import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

/**
 * GraphNode extends the custom graphic's library node element and implements the GraphElementInterface in order to provide portability 
 * @author leonidis
 * 
 */
public class GraphNode extends DefaultGraphCell implements GraphElementInterface{
	
	//Parent field required in order to update Node object's status during nail/unail function after selecting it in visual graph 
	private Node parent = null;
	private VisualGraph visGraph = null;
        
        private double nodeWidth;
	private double nodeHeigth;
        
	/**
	 * Creates a new graphical node
	 * @param name node label
	 * @param x the upper x position
	 * @param y the upper y position
	 * @param w width of node
	 * @param h height of node
	 * @param parent Node class parent
	 */
	public GraphNode(String name,double x,double y,double w,double h,Node parent, VisualGraph visGraph){
		super();
		this.parent = parent;
		this.visGraph = visGraph;
                this.nodeWidth = w;
                this.nodeHeigth = h;
		//Set name of this node
		GraphConstants.setValue(this.getAttributes(),name);
		//Set bounds of this node
		GraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(x,y,w,h));
		GraphConstants.setOpaque(this.getAttributes(), true);
		//Set visual attributes for this node
		GraphConstants.setGradientColor(this.getAttributes(), Color.BLUE);
		GraphConstants.setBorderColor(this.getAttributes(), Color.BLACK);
                //We don't want the name of the node to be editable
                GraphConstants.setEditable(this.getAttributes(),false);
		addPort();
	}//end GraphNode
	
        /**
         * Changes the gradient color of the node
         * @param GColor the gradient color of the Node
         */
        public void setNodeGColor(Color GColor){
            GraphConstants.setGradientColor(this.getAttributes(),GColor);
            
        }
        
        /**
         * Retrieves the gradient color of the node
         * @return the gradient color of the graph node
         */
        public Color getNodeGColor(){
            return GraphConstants.getGradientColor(this.getAttributes());
        }
        
        /**
         * Changes the bounds of the graphical node
         * 
         * @param w - the new width of the node
         * @param h - the new heigth of the node
         */
        public void setNodebounds(double w,double h){
            this.nodeWidth = w;
            this.nodeHeigth = h;
            
            GraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(this.getXposition(),this.getYposition(),w,h));
        }
        
	/**
	 * Return the upper x position of this graphical node
	 * @return x : the upper x position
	 */
	public int getXposition() {
		Rectangle r = GraphConstants.getBounds(this.getAttributes()).getBounds();
		return r.x;
	}//end getXposition

	/**
	 * Return the upper y position of this graphical node
	 * @return y : the upper y position
	 */
	public int getYposition() {
		Rectangle r = GraphConstants.getBounds(this.getAttributes()).getBounds();
		return r.y;
	}//end getYposition

	/**
	 *  Set the upper x position of this graphical node
	 * @param x : the upper x position
	 */
	public void setXposition(int x) {
            Rectangle2D nodeRect = (Rectangle2D) GraphConstants.getBounds(this.getAttributes());
            Double newX = (double) x;
            Double newY = nodeRect.getY();
            
            //If undo is enabled we must keep track of the moves that happen in a node
            if (visGraph.isUndoEnabled()) {
                Map map = new Hashtable();
                GraphConstants.setBounds(map, new Rectangle2D.Double(newX, newY, nodeRect.getWidth(), nodeRect.getHeight()));
                Hashtable nested = new Hashtable();
                nested.put(this, map);
                visGraph.getGraphLayoutCache().edit(nested);
            } else {
                GraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(newX, newY, nodeRect.getWidth(), nodeRect.getHeight()));
            }
	}//end setXposition
	
	/**
	 * Set the upper y position of this graphical node
	 * @param y : the upper y position
	 */
	public void setYposition(int y) {
            Rectangle2D nodeRect = (Rectangle2D) GraphConstants.getBounds(this.getAttributes());
            Double newX = nodeRect.getX();
            Double newY = (double)y;
            //If undo is enabled we must keep track of the moves that happen in a node
            if (visGraph.isUndoEnabled()) {
                Map map = new Hashtable();
                GraphConstants.setBounds(map, new Rectangle2D.Double(newX, newY, nodeRect.getWidth(), nodeRect.getHeight()));
                Hashtable nested = new Hashtable();
                nested.put(this, map);
                visGraph.getGraphLayoutCache().edit(nested);
            } else {
                GraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(newX, newY, nodeRect.getWidth(), nodeRect.getHeight()));
            }
	}//end setYposition

        /**
         * Set the x,y position of this graphical node
         * 
         * @param x - the upper x position
         * @param y - the upper y position
         */
        public void setXYposition(int x,int y){
            //Previous dimensions and position
            Rectangle2D nodeRect = (Rectangle2D) GraphConstants.getBounds(this.getAttributes());
            //New positions
            Double newX = (double) x;
            Double newY = (double) y;
            
            //If undo is enabled we must keep track of the moves that happen in a node
            if(visGraph.isUndoEnabled()){
                Map map = new Hashtable();
                GraphConstants.setBounds(map, new Rectangle2D.Double(newX, newY, nodeRect.getWidth(), nodeRect.getHeight()));
                Hashtable nested = new Hashtable();
                nested.put(this, map);
                visGraph.getGraphLayoutCache().edit(nested);
            }else{
                GraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(newX, newY, nodeRect.getWidth(), nodeRect.getHeight()));
            }
        }
        /**
         * @return the width of the graphical node
         */
        public double getNodeWidth(){
            return nodeWidth;
        }
        
        /**
         * 
         * @return the heigth of the graphical node
         */
        public double getNodeHeigth(){
            return nodeHeigth;
        }
	/**
	 * Set nailed status of this node
	 * @param nailedStatus
	 */
	public void setNailed(boolean nailedStatus) {
//		GraphConstants.setMoveable(this.getAttributes(),!nailedStatus);
	}
	
	/**
	 * Return Node object which is parent of this graph element
	 * @return Node
	 */
	public Node getParentNode(){
		return parent;
	}

	/**
	 * Set the visibility status of this graphical node
	 * @param visibility visibility
	 */
	public void setVisible(boolean visibility) {
		// TODO Change this. Probably let node now its parent graph
		//ProjectManager.getSingleton().getActiveProject().getActiveFrame().getGraph().getVisualGraph().getGraphLayoutCache().setVisible(this,visibility);
		visGraph.getGraphLayoutCache().setVisible(this,visibility);
	}//end setVisible
}//end class GraphNode