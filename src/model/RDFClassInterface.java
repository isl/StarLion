package model;

import java.util.Collection;

/**
 * Public interface in order to emulate RDF_Class methods through an independent API 
 * @author leonidis
 */
public interface RDFClassInterface {
	
	/**
	 * Returns all subclasses of this class object
	 * @return collection with all subclasses
	 */
	public Collection getAllSubClasses();
	
	/**
	 * Returns all superclasses of this class object
	 * @return collection with all superclasses
	 */
	public Collection getAllSuperClasses();
	
	/**
	 * Returns direct superclasses of this class object
	 * @return collection with direct superclasses
	 */
	public Collection getSuperClasses();
	
	/**
	 * Returns direct subclasses of this class object
	 * @return collection with direct subclasses
	 */
	public Collection getSubClasses();
	
	/**
	 * Returns all properties of this class object
	 * @return collection with all properties
	 */
	public Collection getProperties();
	
	/**
	 * Returns the local name of this class as string 
	 * @return local name of this class
	 */
	public String getLocalName();
}//end interface RFDClassInterface