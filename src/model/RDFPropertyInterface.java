package model;

import java.util.Collection;

/**
 * Public interface in order to emulate RDF_Property methods through an independent API 
 * @author leonidis
 */
public interface RDFPropertyInterface {
	
	/**
	 * Returns a collection with all classes indicated as ranges of this property
	 * @return a collection with all classes
	 */
	public Collection getRanges();
	
	/**
	 * Returns a collection with all classes indicated as domains of this property
	 * @return a collection with all classes
	 */
	public Collection getDomains();
	
	/**
	 * Returns the local name of this property as string 
	 * @return local name of this property
	 */
	public String getLocalName();
}//end interface RDFPropertyInterface