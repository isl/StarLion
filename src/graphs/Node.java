package graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import model.RDFResource;

/**
 * Node represents a class object of the RDF Model.
 * Contains the RDF resource (super class of RDF_Model) that represents
 * @author leonidis
 * 
 */
public class Node extends GraphElement{
	//StartPosX,StartPosY variables can be used when we want to keep a 
        //node's position without moving the node it self.Usually used in Force 
        //Directed Placement Algorithm
        protected int             startPosX;
        protected int             startPosY;
        protected int             endPosX;
        protected int             endPosY;
        private GraphNode 	node = null;
	private String 		name = "";
        //The namespace that the node belongs to
        private String          nodeNamespace;
        
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	//Added for ranking speed. They may be useful later for force
	//directed algorithms
	//Used for 2-way navigation
	private LinkedList<Edge> edgesFrom;
	private LinkedList<Edge> edgesTo;
	private LinkedList<Edge> speedEdgesFrom;
	private LinkedList<Edge> speedEdgesTo;
	/**
	 * Create a new node with the specified name and attribute
	 * @param res RDF_Resource
	 * @param name RDF_Resource localname
	 * @param x the upper x position of this node
	 * @param y the upper y position of this node
	 * @param z the upper z position of this node (if 3-D)
	 * @param w the width of this node (graphical attributes)
	 * @param h the height of this node (graphical attributes)
	 */
	public Node(Graph parentGraph, RDFResource res, String name, int x, int y, int z, int w, int h, String nameSpace,SEMWEB_OBJECT_TYPE nodeType) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.h = h;
        this.name = name;
        this.parentGraph = parentGraph;
        this.rdfResource = res;
        this.rdfObjectType = nodeType;
        this.nodeNamespace = nameSpace;
        ///////////////////////////////////////////////////////////////////////////////////////////////
        //Create new object

        //Sets the width to match the length of the name
        w = calculateBestWidth(name);
        this.w = w;
        node = new GraphNode(name, x, y, w, h, this, this.parentGraph.getVisualGraph());
        ///////////////////////////////////////////////////////////////////////////////////////////////
        //Create new list that will hold connection info
        edgesFrom = new LinkedList<Edge>();
        edgesTo = new LinkedList<Edge>();
        speedEdgesFrom = new LinkedList<Edge>();
        speedEdgesTo = new LinkedList<Edge>();
    }//end Node
        
//        
//	public Node(Node newN) {
//        this.x = newN.x;
//        this.y = newN.y;
//        this.z = newN.z;
//        this.name = newN.name;
//        this.parentGraph = newN.parentGraph;
//        this.rdfResource = newN.rdfResource;
//        this.rdfObjectType = newN.rdfObjectType;
//        this.nodeNamespace = newN.nodeNamespace;
//        ///////////////////////////////////////////////////////////////////////////////////////////////
//        //Create new object
//
//        
//        
//        node = new GraphNode(newN.name, newN.x, newN.y, newN.w, newN.h, newN, newN.parentGraph.getVisualGraph());
//        ///////////////////////////////////////////////////////////////////////////////////////////////
//        //Create new list that will hold connection info
//        edgesFrom = new LinkedList<Edge>();
//        edgesTo = new LinkedList<Edge>();
//        speedEdgesFrom = new LinkedList<Edge>();
//        speedEdgesTo = new LinkedList<Edge>();
//    }//end Node
	/**
	 * Return the graphical object(node) that represents this node
	 * @return graphical node
	 */
	public GraphNode getGraphNode(){
		return node;
	}//end getGraphNode
	
	/**
	 * Set the graphical object(node) of this node 
	 * @param node
	 */
	public void setGraphNode(GraphNode node){
		this.node = node;
	}//end setGraphNode
	
	/**
	 * Return name of this node
	 * @return node's name
	 */
	public String getName(){
		return name;
	}//end getName
        
        /**
         * Return the total number of node's edges incoming and outgoing
         * @return total number of edges
         */
        public int getTotalEdges(){
            return edgesFrom.size() + edgesTo.size();
        }
        
        /**
         * Returns the collection of superclasses(direct or all) of the current node
         * @param directOnly - if we want to get all or only direct superclasses
         * @return a collection with all superclasses
         */
        public Collection<Node> getSuperClasses(boolean directOnly){
            ArrayList<Node> superClasses = new ArrayList<Node>();
            if(this.edgesFrom.size() == 0){
                //if there are no incoming edges then there are no superclasses
                return superClasses;
            }else{
                Iterator edgeIt = edgesFrom.descendingIterator();
                while(edgeIt.hasNext()){
                    Edge edge = (Edge) edgeIt.next();
                    Node parentNode = edge.getTargetNode();
                    if(edge.rdfObjectType == SEMWEB_OBJECT_TYPE.SUBCLASSOF){
                        superClasses.add(parentNode);
                        if(!directOnly){
                            superClasses.addAll(parentNode.getSuperClasses(false));
                        }
                    }
                    
                }
                return superClasses;
            }
        }
        
        /**
         * Returns the collection of subclasses(direct or all) of the current node
         * @param directOnly - if we want to get all or only direct subclasses
         * @return a collection with all subclasses
         */
        public Collection<Node> getSubClasses(boolean directOnly){
            ArrayList<Node> subClasses = new ArrayList<Node>();
            if(this.edgesTo.size() == 0){
                //if there are no incoming edges then there are no subclasses
                return subClasses;
            }else{
                Iterator edgeIt = edgesTo.descendingIterator();
                while(edgeIt.hasNext()){
                    Edge edge = (Edge) edgeIt.next();
                    Node parentNode = edge.getSourceNode();
                    if(edge.rdfObjectType == SEMWEB_OBJECT_TYPE.SUBCLASSOF){
                        subClasses.add(parentNode);
                        if(!directOnly){
                            subClasses.addAll(parentNode.getSubClasses(false));
                        }
                    }
                    
                }
                return subClasses;
            }
        }
        
        
        /**
     * Returns the collection of instances(direct or all)of the current node
     * @param directOnly - true if we want to get only direct instances of this class
     * @return a collection with all nodes
     */
    public Collection<Node> getInstances(boolean directOnly) {
        ArrayList<Node> instances = new ArrayList<Node>();
        if (this.edgesTo.size() == 0) {
            //if there are no incoming edges then there are no subclasses
            return instances;
        } else {
            Iterator edgeIt = edgesTo.descendingIterator();
            while (edgeIt.hasNext()) {
                Edge edge = (Edge) edgeIt.next();
                Node parentNode = edge.getSourceNode();
                //For not direct instances we need to traverse the hierarchie
                if (edge.rdfObjectType == SEMWEB_OBJECT_TYPE.SUBCLASSOF||edge.rdfObjectType == SEMWEB_OBJECT_TYPE.INSTANCEOF) {
                    if(edge.rdfObjectType == SEMWEB_OBJECT_TYPE.INSTANCEOF){
                        instances.add(parentNode);
                    }
                    if (!directOnly) {
                        instances.addAll(parentNode.getInstances(false));
                    }
                }

            }
            return instances;
        }
    }
        /**
         * Returns the Uri of the current node
         * @return the uri of the current node
         */
        public String getNodeUri(){
            return this.nodeNamespace+name;
        }
        
        public void setStartingCoordinates(int startX,int startY){
            this.startPosX = startX;
            this.startPosY = startY;
        }
        
        public void setEndingCoordinates(int endX,int endY){
            this.endPosX = endX;
            this.endPosY = endY;
        }
        
	/**
         * Updates the x position of the node WITHOUT updating the visual library(Graph Node)
         * 
         * @param x - the new x position of the node
         */
        public void setCoreX(int x){
                this.x = x;
        }
        
        /**
         * Updates the y position of the node WITHOUT updating the visual library(Graph Node)
         * 
         * @param y - the new y position of the node
         */
        public void setCoreY(int y){
                this.y = y;
        }
        
        /**
         * Patches the x,y position of the Graph Node with the x,y position of the node.Some times this x,y can be different
         * because there is some times that we update only core node for performance reasons
         */
        public void patchVirtualPosition(){
            node.setXYposition(x, y);
        }
        
        @Override
	/**
	 * Set the upper x position of this node
	 * @param x new x position
	 */
	public void setX(int x){
		this.x = x;
		node.setXposition(x);
	}//end setX

        
	@Override
	/**
	 * Set the upper y position of this node
	 * @param y new y position
	 */
	public void setY(int y){
		this.y = y;
		node.setYposition(y);
	}
	
        @Override
	/**
	 * Override default toString method to return node's name
	 * @return name 
	 */
	public String toString(){
		return name;
	}//end toString

	@Override
	/**
	 * Set the nailed status of this node
	 * @param b nailed
	 */
	public void setNailed(boolean b){
		this.nailed = b;
	}//end setNailed

	@Override
	/**
	 * Set the visibility status of this node
	 * @param b visibility
	 */
	public void setVisible(boolean b){
		this.visible = b;
		node.setVisible(b);
	}//end setVisible
	
	/**
	 * Get a list with all edges starting from this node
	 * @return outgoing edges
	 */
	public LinkedList<Edge> getEdgesFrom(){
		return this.edgesFrom;
	}//end getEdgesFrom
	
	/**
	 * Get a list with all edges ending to this node
	 * @return incoming edges
	 */
	public LinkedList<Edge> getEdgesTo(){
		return this.edgesTo;
	}//end getEdgesTo
	
        /**
         * Get a list with all edges connected to the node
         * @return incoming + outogoing edges
         */
        public LinkedList<Edge> getAllEdges(){
                LinkedList<Edge> allEdges = new LinkedList<Edge>();
                allEdges.addAll(edgesFrom);
                allEdges.addAll(edgesTo);
                return allEdges;
        }
        
	/**
	 * Get number of edges that connect current node with the input one
	 * @param nodeTo input node
	 * @return num
	 */
	public int getEdgesNoToNode(Node nodeTo){
		int num = 0;
		
		Iterator edgesFromIt = this.edgesFrom.iterator();
		Edge edge = null;

		while(edgesFromIt.hasNext()){
			edge = (Edge)edgesFromIt.next();
			if(edge.getTargetNode().equals(nodeTo))
				num++;
		}//end while edgesFromIt.hasNext()
		return num;
	}//end getEdgesNoToNode
	
	public int getEdgesNoFromNode(Node nodeFrom){
		return 0;
	}
	
	/**
	 * Add a new edge to edgesFrom list
	 * @param edgeFrom
	 */
	public void addEdgeFrom(Edge edgeFrom){
		this.edgesFrom.add(edgeFrom);
	}//end addEdgeFrom

    /**
     * Remove an edge from the edgesFrom list
     * @param edgeFrom
     */
    public void removeEdgeFrom(Edge edgeFrom){
        this.edgesFrom.remove(edgeFrom);

    }
	/**
	 * Add a new edge to edgesTo list
	 * @param edgeTo
	 */
	public void addEdgeTo(Edge edgeTo){
		this.edgesTo.add(edgeTo);
	}//end addEdgeTo

    /**
     * Remove an edge from the edgesTo list
     * @param edgeFrom
     */
    public void removeEdgeTo(Edge edgeTo){
        this.edgesTo.remove(edgeTo);
    }
	/**
	 * Get a list with TopK edges starting from this node
	 * @return outgoing edges
	 */
	public LinkedList<Edge> getSpeedEdgesFrom(){
		return this.speedEdgesFrom;
	}//end getEdgesFrom
	
	/**
	 * Get a list with TopK edges ending to this node
	 * @return incoming edges
	 */
	public LinkedList<Edge> getSpeedEdgesTo(){
		return this.speedEdgesTo;
	}//end getEdgesTo
	
	/**
	 * Add a new edge to TopKedgesFrom list
	 * @param edgeFrom
	 */
	public void addSpeedEdgeFrom(Edge edgeFrom){
		this.speedEdgesFrom.add(edgeFrom);
	}//end addEdgeFrom
	
	/**
	 * Add a new edge to TopKedgesTo list
	 * @param edgeTo
	 */
	public void addSpeedEdgeTo(Edge edgeTo){
		this.speedEdgesTo.add(edgeTo);
	}//end addEdgeTo
        
        /**
         * 
         * @return the namespace that the node belongs
         */
        public String getNodeNamespace(){
            return nodeNamespace;
        }
        
        /**
         * Returns information about the visibility of the node
         * @return true if the node is visible and false otherwise
         */
        public boolean isVisible(){
            return this.visible;
        }
        
        /**
         * Returns information about the nail status of the node
         * @return true if the node is nailed and false otherwise
         */
        public boolean isNailed(){
            return this.nailed;
        }
        /**
     * Returns true if the node is a class instance
     * @return true if the node is a class instance and false otherwise
     */
    public boolean isInstance() {
        if (this.rdfObjectType == SEMWEB_OBJECT_TYPE.CLASSINSTANCE) {
            return true;
        } else {
            return false;
        }
    }

        
        /**
         * Based on the length of a node's name calculates and returns the best width for the node with a maximum of 250
         * 
         * @param nodeName - the name of the node
         * @return the suggested width for the node
         */
        private int calculateBestWidth(String nodeName){
            int nodeNameLen = (int) (nodeName.length() * 9.5);
            if(nodeNameLen > 250){
                nodeNameLen = 250;
            }
            
            return nodeNameLen;
        }
}//end class Node