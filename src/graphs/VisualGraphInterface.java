package graphs;

import java.awt.image.BufferedImage;

/**
 * Any custom graphic's library graph element must implement this interface so as to provide the same functionality
 * and make use of system's portability 
 * @author leonidis
 * 
 */
public interface VisualGraphInterface {

	/**
	 * Add a new graphical node in visual graph
	 * @param node Node object that will provide the graphical node
	 */
	public void addNode(Node node);
	
	/**
	 * Add a new graphical edge in visual graph
	 * @param edge Edge object that will provide the graphical edge
	 */
	public void addEdge(Edge edge);
	
	/**
	 * Alternative way. Add graphical edge(graphEdge) between source and targer graphical nodes in visual graph
	 * @param source
	 * @param target
	 * @param edge
	 */
	public void addEdge(Node source, Node target, Edge edge);
	
	/**
	 * Visualize Graph in Internal frame.
	 */
	public void visualizeGraph();
	
	/**
	 * Update graphical layout cache
	 */
	public void updateGraph();

	/**
	 * Return graph as a BufferedImage object
	 * @return BufferedImage
	 */
	public BufferedImage getGraphImage();
}//end interface VisualGraphInterface