package graphs;

/**
 * Any custom graphic's library node or edge element must implement this interface so as to provide the same functionality
 * and make use of system's portability 
 * @author leonidis
 */
public interface GraphElementInterface {
	
	/**
	 * Return element's upper x position
	 * @return x
	 */
	public int getXposition();
	
	/**
	 * Return element's upper y position
	 * @return y
	 */
	public int getYposition();
	
	/**
	 * Set element's upper x position
	 * @param x
	 */
	public void setXposition(int x);
	
	/**
	 * Set element's upper y position
	 * @param y
	 */
	public void setYposition(int y);
	
	/**
	 * Set element's visibility
	 * @param visibility
	 */
	public void setVisible(boolean visibility);
	public void setNailed(boolean nailedStatus);
	
}//end interface GraphElementInterface
