package model;

import gr.forth.ics.rdfsuite.swkm.model.impl.RDF_Class;
import gr.forth.ics.rdfsuite.swkm.model.impl.RDF_Resource;

/**
 * Wrapper class for SWKM RDF_Resource class
 * Contains a private instance of original class in order to emulate same functionality
 * @author leonidis
 */
public class RDFResource implements RDFResourceInterface{

	RDF_Resource rdf_rs = null;
	
	/**
	 * Public constructor for this object. Must distict rdf_rs to appropriate class (RDFResource of RDFClass)
	 * @param rdf_rs provided resource
	 */
	public RDFResource(Object rdf_rs){
		if(rdf_rs instanceof RDF_Resource)
			this.rdf_rs = (RDF_Resource)rdf_rs;
		else if(rdf_rs instanceof RDF_Class)
			this.rdf_rs = (RDF_Class)rdf_rs;
	}//end RDFResource
	
	/**
	 * RDFResourceInterface method
	 */
	public String getLocalName() {
		return rdf_rs.getLocalName();
	}//end getLocalName
        
	/**
	 * RDFResourceInterface method
	 */
	public String getURI(){
		return rdf_rs.getURI();
	}//end getURI
        
        @Override
        public String toString(){
            return rdf_rs.toString();
        }
}//end class RDFResource