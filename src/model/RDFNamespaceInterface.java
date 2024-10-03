package model;

import java.util.Collection;

/**
 * Public interface in order to emulate RDF_Namespace methods through an independent API 
 * @author leonidis
 */
public interface RDFNamespaceInterface {
	
	/**
	 * Return a collection with all classes of this namespace object
	 * @return collection with all classes
	 */
	public Collection getClasses();
	
	/**
	 * Return a collection with all classes of this namespace object
	 * @return collection with all classes
	 */
	public Collection getProperties();
	
	/**
	 * Checks if this namespace contains class indicated by provided uri
	 * @param class_uri requested class
	 * @return true if provided, false otherwise
	 */
	public boolean containsClass(String class_uri);
	
	/**
	 * Returns the URI of this namespace as string 
	 * @return URI of this namespace
	 */
	public String getURI();
}//end interface RDFNamespaceInterface