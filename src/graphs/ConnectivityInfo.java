package graphs;

/**
 * Auxilliary class that holds information node's connectivity information, e.g number of direct descendants, number of all descendants,
 * number of properties in which it is the range or the domain class etc.
 * @author leonidis
 *
 */
public class ConnectivityInfo{
	//Class name
	private String name;
	private double rank = 0;
	//Additional data needed for method 2
	private double prevRank = 1;
	
	private int attrs = 0;
	private int subCl = 0;
	private int allSubCl = 0;
	private int supCl = 0;
	private int allSupCl = 0;
	private int rangesCl = 0;
	private int domainsCl = 0;
	
	public ConnectivityInfo(String name, double prevRank){
		this.name = name;
		this.prevRank = prevRank;
	}//end ClassObj
	
	/**
	 * Return the node's name for which this object holds information
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Return rank of this node
	 * @return rank
	 */
	public double getRank(){
		return rank;
	}
	
	/**
	 * Set input rank as node's rank
	 * @param rank
	 */
	public void setRank(double rank){
		this.rank = rank;
	}
	
	/**
	 * Return previous rank of this node
	 * @return rank
	 */
	public double getPreviousRank(){
		return prevRank;
	}
	
	/**
	 * Set input rank as node's previos rank
	 * @param prevRank
	 */
	public void setPreviousRank(double prevRank){
		this.prevRank = prevRank;
	}
	
	/**
	 * Return attributes of this node (properties where domain is this class and range is RDF:Literal)
	 * @return attrs
	 */
	public int getAttrs(){
		return attrs;
	}
	
	/**
	 * Set attributes for this node
	 * @param attrs
	 */
	public void setAttrs(int attrs){
		this.attrs = attrs;
	}
	
	/**
	 * Return number of direct descendants
	 * @return subclasses number
	 */
	public int getSubclassesNo(){
		return subCl;
	}
	
	/**
	 * Set number of direct descendants
	 * @param subCl
	 */
	public void setSubclassesNo(int subCl){
		this.subCl = subCl;
	}

	/**
	 * Return number of all descendants
	 * @return all subclasses number
	 */
	public int getAllSubclassesNo(){
		return allSubCl;
	}
	
	/**
	 * Set number of all descendants
	 * @param allSubCl
	 */
	public void setAllSubclassesNo(int allSubCl){
		this.allSubCl = allSubCl;
	}
	
	/**
	 * Return number of direct ancestors
	 * @return superclasses number
	 */
	public int getSuperclassesNo(){
		return supCl;
	}
	
	/**
	 * Set number of direct ancestors
	 * @param supCl
	 */
	public void setSuperclassesNo(int supCl){
		this.supCl = supCl;
	}
	
	/**
	 * Return number of all ancestors
	 * @return all superclasses number
	 */
	public int getAllSuperclassesNo(){
		return allSupCl;
	}
	
	/**
	 * Set number of direct ancestors
	 * @param allSupCl
	 */
	public void setAllSuperclassesNo(int allSupCl){
		this.allSupCl = allSupCl;
	}
	
	/**
	 * Return number of properties where this class is range
	 * @return ranges
	 */
	public int getRangeclassesNo(){
		return rangesCl;
	}
	
	/**
	 * Set number of properties where this class is range
	 * @param rangesCl
	 */
	public void setRangeclassesNo(int rangesCl){
		this.rangesCl = rangesCl;
	}
	
	/**
	 * Return number of properties where this class is domain
	 * @return domains
	 */
	public int getDomainclassesNo(){
		return domainsCl;
	}
	
	/**
	 * Set number of properties where this class is domain
	 * @param domainsCl
	 */
	public void setDomainclassesNo(int domainsCl){
		this.domainsCl = domainsCl;
	}
}//end class ClassObj
