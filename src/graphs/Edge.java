package graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import model.RDFProperty;

/**
 * Edge connects two nodes
 * An edge may represents either a subclass hierarchy or a property (each look-and-feel is different in order to be distinguished)
 * Contains references to its source and targer nodes and its graphical representation 
 * @author leonidis
 *
 * @see #source
 * @see Edge#directed
 * @see RDFProperty#getDirectDescendants() Whatever
 */
public class Edge extends GraphElement{

	private Node source = null;
	private Node target = null;
	private GraphEdge edge = null;
    private boolean directed = true;
        private String    edgeName = "";
        private EdgeLabel eLabel = null;//A simple String is not enough because we want to control the visibility of the label
	private ArrayList<String> subProperties = null;
	/**
	 * Create a new edge between source and target node
	 * @param source source node
	 * @param target target node
	 * @param label edge's label
	 * @param rdfObjectType
	 * @param nedges number of edges between source and target nodes
     * @param directed true if the edge is directed and false otherwise
	 */
	public Edge(Graph parGraph,Node source, Node target, String label, SEMWEB_OBJECT_TYPE rdfObjectType, int nedges,boolean directed){
                this.parentGraph = parGraph;
		this.source = source;
		this.target = target;
		this.rdfObjectType = rdfObjectType;
                this.edgeName = label;
                this.subProperties = new ArrayList<String>();
                this.eLabel = new EdgeLabel(label);
                this.directed = directed;
		edge = new GraphEdge(source.getGraphNode(), target.getGraphNode(), eLabel, nedges,this.parentGraph.getVisualGraph());
		
		if(rdfObjectType == SEMWEB_OBJECT_TYPE.PROPERTY){
			edge.setArrowTypeSimple();
			edge.setLabelPosition(source.getEdgesNoToNode(target));
		}else if(rdfObjectType == SEMWEB_OBJECT_TYPE.SUBCLASSOF){
			edge.setArrowTypeTechnical();
                }else if(rdfObjectType == SEMWEB_OBJECT_TYPE.INSTANCEOF){
                        edge.setArrowTypeTechnicalDashed();
                }else if(rdfObjectType == SEMWEB_OBJECT_TYPE.PROPERTYINSTANCE){
                        edge.setArrowTypeSimple();
                }
        //If the edge is not directed remove the direction arrow
        if(!directed){
            edge.setArrowTypeNone();
        }
	}//end Edge
//        
//        public Edge(Edge newE){
//            this.parentGraph = newE.parentGraph;
//            this.source = newE.source;
//            this.target = newE.target;
//            this.rdfObjectType = newE.rdfObjectType;
//            this.edgeName = newE.edgeName;
//            this.subProperties = new ArrayList<String>();
//            this.eLabel = new EdgeLabel(newE.edgeName);
//            this.directed = newE.directed;
//            int nedges = 0;
//            edge = new GraphEdge(newE.source.getGraphNode(), newE.target.getGraphNode(), newE.eLabel, nedges,newE.parentGraph.getVisualGraph());
//            if(rdfObjectType == SEMWEB_OBJECT_TYPE.PROPERTY){
//			edge.setArrowTypeSimple();
//			edge.setLabelPosition(nedges);
//		}else if(rdfObjectType == SEMWEB_OBJECT_TYPE.SUBCLASSOF){
//			edge.setArrowTypeTechnical();
//                }else if(rdfObjectType == SEMWEB_OBJECT_TYPE.INSTANCEOF){
//                        edge.setArrowTypeTechnicalDashed();
//                }else if(rdfObjectType == SEMWEB_OBJECT_TYPE.PROPERTYINSTANCE){
//                        edge.setArrowTypeSimple();
//                }
//        //If the edge is not directed remove the direction arrow
//        if(!directed){
//            edge.setArrowTypeNone();
//        }
//        }
        
	/**
	 * Return the source node of this edge
	 * @return source node
	 */
	public Node getSourceNode(){
		return source;
	}//end getSourceNode
	
	/**
	 * Return the target node of this edge
	 * @return target node
	 */
	public Node getTargetNode(){
		return target;
	}//end getTargetNode
	
	/**
	 * Return the graphical representation of this edge
	 * @return graphical egde
	 */
	public GraphEdge getGraphEdge(){
		return edge;
	}//end getGraphEdge
	
	/**
	 * Set the graphical representation of this edge
	 * @param edge : graphical edge
	 */
	public void setGraphEdge(GraphEdge edge){
		this.edge = edge;
	}

        /**
         * Sets the visibility status of the label
         * 
         * @param visibility - true for visible label and false otherwise
         */
        public void setLabelVisible(boolean visibility){
            this.eLabel.setVisible(visibility);
        }
        
	@Override
	/**
	 * Not used in edges. GraphElement abstact method
	 */
	public void setX(int x) {
		// TODO Auto-generated method stub
	}//end setX

	@Override
	/**
	 * Not used in edges. GraphElement abstact method
	 */
	public void setY(int y) {
		// TODO Auto-generated method stub
	}//end setY

	@Override
	/**
	 * Not used in edges. GraphElement abstact method
	 */
	public void setNailed(boolean b) {
		// TODO Auto-generated method stub
	}//end setNailed

	@Override
	/**
	 * Set the visibility status of an edge
	 */
	public void setVisible(boolean b) {
            this.visible = b;
            edge.setVisible(b);
		// TODO Auto-generated method stub
	}//end setVisible
        
        
        public void setSubProperties(Collection<RDFProperty> propCollection){
            Iterator propIt = propCollection.iterator();
            while(propIt.hasNext()){
                this.subProperties.add(((RDFProperty)propIt.next()).toString());
            }
        }
        
        public ArrayList<String> getSubProperties(){
            return this.subProperties;
        }
        
        @Override
        /**
         * Returns the name of the edge
         */
        public String toString(){
            return edgeName;
        }
        
        /**
         * Two edges are considered equal if they have the same name,source and
         * targe node
         * @param edge - the other edge to compare
         * @see graphs.Node What a beatiful node
         * @return true if the two edges are the same
         */
    @Override
        public boolean equals(Object edge){
            if(edge == null || !(edge instanceof Edge)){
                return false;
            }
            Edge e = (Edge)edge;
            if(this.toString().equals(e.toString())&&
                    this.getSourceNode().getName().equals(e.getSourceNode().getName())&&
                    this.getTargetNode().getName().equals(e.getTargetNode().getName())){
//               this.getSourceNode().equals(e.getSourceNode())&&
//               this.getTargetNode().equals(e.getTargetNode())){
                return true;
            }else{
                return false;
            }
        }
    
    @Override
    public int hashCode(){
        int result = edge.toString().hashCode();
        result = 37 * result + getSourceNode().getName().hashCode();
        result = 37 * result + getTargetNode().getName().hashCode();
        return result;
   }
}//end class Edge