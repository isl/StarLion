package model;

import gr.forth.ics.rdfsuite.swkm.model.impl.RDF_Class;
import gr.forth.ics.rdfsuite.swkm.model.impl.RDF_Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Wrapper class for SWKM RDF_Class class
 * Contains a private instance of original class in order to emulate same functionality
 * @author leonidis
 */
public class RDFClass extends RDFResource implements RDFClassInterface{
	
	/**
	 * Public constructor for this class
	 * @param rdf_rs
	 */
	public RDFClass(Object rdf_rs) {
		super(rdf_rs);
	}//end RDFClass
	
	/**
	 * RDFClassInterface method
	 * For each RDF_Class descendant provided by class, construct a new RDFClass object and add 
	 * it to collection 
	 */
	public Collection getAllSubClasses() {
		Iterator allSubclassIter = ((RDF_Class)((RDF_Resource)this.rdf_rs)).getDescendants().iterator();
		ArrayList<RDFClass> allSubclassesCollection = new ArrayList<RDFClass>();
		RDFClass subclass = null;
		
		while(allSubclassIter.hasNext()){
			subclass = new RDFClass(allSubclassIter.next());
			allSubclassesCollection.add(subclass);
		}//end while classIter.hasNext
		
		return allSubclassesCollection;
		
	}//end getAllSubClasses
	
	/**
	 * RDFClassInterface method
	 * For each RDF_Class ancestor provided by class, construct a new RDFClass object and add 
	 * it to collection
	 */
	public Collection getAllSuperClasses() {
		Iterator allSuperclassIter = ((RDF_Class)((RDF_Resource)this.rdf_rs)).getAncestors().iterator();
		ArrayList<RDFClass> allSuperclassesCollection = new ArrayList<RDFClass>();
		RDFClass superclass = null;
		
		while(allSuperclassIter.hasNext()){
			superclass = new RDFClass(allSuperclassIter.next());
			allSuperclassesCollection.add(superclass);
		}//end allSuperclassIter.hasNext
		
		return allSuperclassesCollection;
		
	}//end getAllSuperClasses
	
	/**
	 * RDFClassInterface method
	 * For each RDF_Class direct ancestor provided by class, construct a new RDFClass object and add 
	 * it to collection
	 */
	public Collection getSuperClasses() {
		Iterator superclassIter = ((RDF_Class)((RDF_Resource)this.rdf_rs)).getDirectAncestors().iterator();
		ArrayList<RDFClass> superclassesCollection = new ArrayList<RDFClass>();
		RDFClass superclass = null;
		
		while(superclassIter.hasNext()){
			superclass = new RDFClass(superclassIter.next());
			superclassesCollection.add(superclass);
		}//end allSuperclassIter.hasNext
		
		return superclassesCollection;
		
	}//end getSuperClasses
	
	/**
	 * RDFClassInterface method
	 * For each RDF_Class direct descendant provided by class, construct a new RDFClass object and add 
	 * it to collection
	 */
	public Collection getSubClasses() {
		Iterator subclassIter = ((RDF_Class)((RDF_Resource)this.rdf_rs)).getDirectDescendants().iterator();
		ArrayList<RDFClass> subclassesCollection = new ArrayList<RDFClass>();
		RDFClass subclass = null;
		
		while(subclassIter.hasNext()){
			subclass = new RDFClass(subclassIter.next());
			subclassesCollection.add(subclass);
		}//end while classIter.hasNext
		
		return subclassesCollection;
		
	}//end getSubClasses
	
	/**
	 * RDFClassInterface method
	 * For each RDF_Class descentant provided by class, construct a new RDFProperty object and add 
	 * it to collection
	 */
	public Collection getProperties() {
		Iterator propertiesIter = ((RDF_Class)((RDF_Resource)this.rdf_rs)).getDomainOf().iterator();
		ArrayList<RDFProperty> propertiesCollection = new ArrayList<RDFProperty>();
		RDFProperty property = null;
		
		while(propertiesIter.hasNext()){
			property = new RDFProperty(propertiesIter.next());
			propertiesCollection.add(property);
		}//end while propertiesIter.hasNext
		
		propertiesIter = ((RDF_Class)((RDF_Resource)this.rdf_rs)).getRangeOf().iterator();
		property = null;
		
		while(propertiesIter.hasNext()){
			property = new RDFProperty(propertiesIter.next());
			propertiesCollection.add(property);
		}//end while propertiesIter.hasNext
		
		return propertiesCollection;
		
	}//end getProperties
}//end class RDFClass