package model;

import gr.forth.ics.rdfsuite.swkm.model.IRDF_PropertyInstance;


/**
 *
 * @author zabetak
 */
public class RDFPropertyInstance {
    IRDF_PropertyInstance prInstance;
    public RDFPropertyInstance(Object obj){
        prInstance = (IRDF_PropertyInstance)obj;
    }
    
    public RDFResource getSubject(){
        return new RDFResource(prInstance.getSubject());
    }
    
    public Object getObject(){
        return prInstance.getObject();
    }
    
    public RDFProperty getPredicate(){
        return new RDFProperty(prInstance.getPredicate());
    }
    
    public String toString(){
        return prInstance.toString();
    }
}
