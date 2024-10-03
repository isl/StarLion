package model;

/**
 * Public interface in order to emulate RDF_Resource methods through an independent API 
 * @author leonidis
 */
public interface RDFResourceInterface {
	
	/**
	 * Returns the local name of this resource as string 
	 * @return local name of this resource
	 */
	public String getLocalName();
	
	/**
	 * Returns the URI of this resource as string 
	 * @return URI of this resource
	 */
	public String getURI();
}//end interface RDFResourceInterface