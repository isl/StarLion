package graphs;

import model.RDFResource;

enum SEMWEB_OBJECT_TYPE{CLASS,CLASSINSTANCE, PROPERTY,PROPERTYINSTANCE, SUBCLASSOF ,INSTANCEOF};

/**
 * The main superclass of all graph elements (Node, Edge)
 * Contains a reference to the RDF Resource that represents
 * Also contains attribute variables used in layout algorithms such as nailed status, ranking score etc.   
 * @author leonidis
 */
public abstract class GraphElement {
	boolean 	 		nailed = false;
	boolean				visible = true;
	boolean 	 		selected = false;
	double 		 		rankingScore;
	//Add for calculation of w3
	double 		 		prevRankingScore=0; //TODO initially 1 -> used in method2
	double 		 		forceX;
	double		 		forceY;
	int			 		x;
	int			 		y;
	int			 		z;
        int                                     w;
        int                                     h;
	SEMWEB_OBJECT_TYPE 	rdfObjectType;
	RDFResource			rdfResource = null;
	Graph				parentGraph = null;
	
	/**
	 * Set X position of this element
	 * @param x
	 */
	public abstract void setX(int x);
	
	/**
	 * Set Y position of this element
	 * @param y
	 */
	public abstract void setY(int y);
	
	/**
	 * Set nailed status of this element
	 * @param b
	 */
	public abstract void setNailed(boolean b);
	
	/**
	 * Set visibility status of this element
	 * @param b
	 */
	public abstract void setVisible(boolean b);
}//end class GraphElement