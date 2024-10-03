package model;

import gr.forth.ics.rdfsuite.swkm.model.IRDF_Namespace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Wrapper class for SWKM RDF_Namespace class
 * Contains a private instance of original class in order to emulate same functionality
 * @author leonidis
 */
public class RDFNamespace implements RDFNamespaceInterface{
	
	private IRDF_Namespace namespace = null;

	/**
	 * Public constructor for this class
	 * @param namespace
	 */
	public RDFNamespace(IRDF_Namespace namespace){
		this.namespace = namespace;
	}//end RDFNamespace
	
	/**
	 * RDFNamespaceInterface method
	 * For each RDF_Class provided by namespace, construct a new RDFClass object and add 
	 * it to collection
	 */
	public Collection getClasses() {
		Iterator classIt = namespace.getClasses().values().iterator();
		ArrayList<RDFClass> classesCollection = new ArrayList<RDFClass>();
		RDFClass rdfClass = null;
		
		while(classIt.hasNext()){
			rdfClass = new RDFClass(classIt.next());
			classesCollection.add(rdfClass);
		}//end while classIt.hasNext
		
		return classesCollection;
		
	}//end getClasses

	/**
	 * RDFNamespaceInterface method
	 * For each RDF_Class provided by namespace, construct a new RDFClass object and add 
	 * it to collection
	 */
	public Collection getProperties() {
		Iterator propertiesIt = namespace.getProperties().values().iterator();
		ArrayList<RDFProperty> propertiesCollection = new ArrayList<RDFProperty>();
		RDFProperty rdfProperty = null;
		
		while(propertiesIt.hasNext()){
			rdfProperty = new RDFProperty(propertiesIt.next());
			propertiesCollection.add(rdfProperty);
		}//end propertiesIt.hasNext
		
		return propertiesCollection;
		
	}//end getProperties
	
	/**
	 * RDFNamespaceInterface method
	 */
	public String getURI(){
		return namespace.getURI();
	}//end getURI

	/**
	 * RDFNamespaceInterface method
	 */
	public boolean containsClass(String class_uri) {
		Object obj = namespace.getClass(class_uri);
		
		if(obj == null)
			return false;
		
		return true;
	}//end containsClass
}
