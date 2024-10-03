package model;

import gr.forth.ics.rdfsuite.swkm.model.impl.RDF_Property;
import gr.forth.ics.rdfsuite.swkm.model.impl.RDF_Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Wrapper class for SWKM RDF_Property class
 * Contains a private instance of original class in order to emulate same functionality
 * @author leonidis
 */
public class RDFProperty extends RDFResource implements RDFPropertyInterface{
	
	/**
	 * Public constructor for this class
	 * @param rdf_rs
	 */
	public RDFProperty(Object rdf_rs) {
		super(rdf_rs);
	}//end RDFProperty
	
	/**
	 * RDFPropertyInterface method
	 * For each RDF_Class domain provided by property, construct a new RDFClass object and add 
	 * it to collection
	 */
	public Collection getDomains() {
		Iterator domainsIter = ((RDF_Property)((RDF_Resource)this.rdf_rs)).getDomains().iterator();
		ArrayList<RDFClass> domainsCollection = new ArrayList<RDFClass>();
		RDFClass domain = null;
		
		while(domainsIter.hasNext()){
			domain = new RDFClass(domainsIter.next());
			domainsCollection.add(domain);
		}//end while domainsIter.hasNext
		
		return domainsCollection;
	}//end getDomains
	
	/**
	 * RDFPropertyInterface method
	 * For each RDF_Class range provided by property, construct a new RDFClass object and add 
	 * it to collection
	 */
	public Collection getRanges() {
		Iterator rangesIter = ((RDF_Property)((RDF_Resource)this.rdf_rs)).getRanges().iterator();
		ArrayList<RDFClass> rangesCollection = new ArrayList<RDFClass>();
		RDFClass range = null;
		
		while(rangesIter.hasNext()){
			range = new RDFClass(rangesIter.next());
			rangesCollection.add(range);
		}//end while domainsIter.hasNext
		return rangesCollection;
		
	}//end getRanges

	/**
	 * RDFPropertyInterface method
	 */
	public String getLocalName() {
		return ((RDF_Property)((RDF_Resource)this.rdf_rs)).getLocalName();
	}//end getLocalName
        
        public String toString(){
            return getLocalName();
        }
        
        public Collection<RDFProperty> getDirectDescendants() {
            Iterator descedantIt = ((RDF_Property)((RDF_Resource)this.rdf_rs)).getDirectDescendants().iterator();
            ArrayList<RDFProperty> descedantsCollection = new ArrayList<RDFProperty>();
            RDFProperty prop = null;
            
            while(descedantIt.hasNext()){
                prop = new RDFProperty(descedantIt.next());
                descedantsCollection.add(prop);
            }
            
            return descedantsCollection;
        }
}//end class RDFProperty