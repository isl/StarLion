package model;

import gr.forth.ics.rdfsuite.swkm.model.IRDF_Class;
import gr.forth.ics.rdfsuite.swkm.model.IRDF_Namespace;
import gr.forth.ics.rdfsuite.swkm.model.IRDF_Resource;
import gr.forth.ics.rdfsuite.swkm.model.impl.RDF_Model;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Wrapper class for SWKM RDF_Model class
 * Contains a private instance of original class in order to emulate same functionality
 * @author leonidis
 */
public class RDFModel implements RDFModelInterface{
	
	RDF_Model model = null;
	
	/**
	 * Public constructor for this class
	 */
	public RDFModel(){
		this.model = new RDF_Model();
	}//end RDFModel
	
	/**
	 * RDFModelInterface method
	 * Check all model namespaces in order to find requested one.
	 * Take into account that model maybe contain a base_uri not exactly similar to its name
	 * eg real filename vs base_uri, so we must search all contained namespaces for the requested
	 * one
	 * 
	 * Tokenize file path to get its real file name and search it as base_uri
	 */
	public RDFNamespace getNamespace(String namespace_uri) {

		String base_uri = "";
		HashMap namespaces = model.getNamespaces();
		IRDF_Namespace inamespace = null;

                boolean base_uri_isSet = false;
                System.out.println("NSURI:"+namespace_uri);
		int start = namespace_uri.lastIndexOf(java.io.File.separatorChar);
                int end = namespace_uri.lastIndexOf("#");
                if(end == -1)
                      end = namespace_uri.length();
		/*
                int end = namespace_uri.lastIndexOf('.');
                if(end == -1 || end < start){
                    end = namespace_uri.lastIndexOf("#");
                }
                 
                System.out.println("START = "+start);
                System.out.println("Namespace_uri = "+namespace_uri);
                System.out.println("END = "+end);
                 */
		base_uri = namespace_uri.substring(start+1, end);
		//System.out.println("BaseUri = "+base_uri);
		Iterator it = namespaces.values().iterator();
		while(it.hasNext()){
			inamespace = (IRDF_Namespace)it.next();
                        if(inamespace.getURI() == null)continue;
			if(inamespace.getURI().contains(base_uri)){
				base_uri = inamespace.getURI();
                                base_uri_isSet = true;
				break;
			}
		}
		//System.out.println("FINAL URI  ="+base_uri);
                RDFNamespace namespace = null;
                if(base_uri_isSet){
                    namespace = new RDFNamespace(model.getNamespace(base_uri));
                }
		
                //System.out.println("NAMESPACE = "+namespace.getURI());
		return namespace;
	}//end getNamespace
	/*
        public Collection getSubPropertyOf(RDFProperty arg0){
            
             IRDF_SubProperty sprop;
             IRDF_SubClass cl;
             RDF_Property prop;

             model.getSubPropertyOf((RDF_Property)arg0.rdf_rs);

             return null;
        }
         */
        
        public Collection<RDFResource> getAllInstances(){
            HashSet<RDFResource> instances = new HashSet<RDFResource>();
            for(IRDF_Class c:model.getClasses(false, false)){
                for(IRDF_Resource r:c.getInstances()){
                    RDFResource re = new RDFResource(r);
                    instances.add(re);
                }
            }
            return instances;
        }
        
        public Collection<RDFResource> getInstances(String className){
            HashSet<RDFResource> instances = new HashSet<RDFResource>();
            for(IRDF_Class c:model.getClasses(false, false)){
                if(!c.getLocalName().equals(className)){
                    continue;
                }
                for(IRDF_Resource r:c.getInstances()){
                    RDFResource re = new RDFResource(r);
                    instances.add(re);
                }
            }
            return instances;
        }
        
        public Collection<RDFPropertyInstance> getAllPropertyInstances(){
            HashSet<RDFPropertyInstance> instances = new HashSet<RDFPropertyInstance>();
            for(Object obj:model.getPropertyInstances().values()){
                RDFPropertyInstance rdf_prop_in = new RDFPropertyInstance(obj);
                instances.add(rdf_prop_in);
            }
            return instances;
        }
        
        /**
         * Returns all namespaces of the model in an array
         * @return the namespaces of the model
         */
        public String[] getNamespaces(){
                
		HashMap namespaces = model.getNamespaces();
		IRDF_Namespace inamespace = null;
                Iterator it = namespaces.values().iterator();
     
                String[] nameSpaces = new String[namespaces.size()];
                int i = 0;
                
                while(it.hasNext()){
                    
                    inamespace = (IRDF_Namespace)it.next();
                    nameSpaces[i] = inamespace.getURI();
                    i++;
                }
                return nameSpaces;
                
        }
        
	/**
	 * RDFModelInterface method
	 */
	public boolean read(String model_file, String base_uri, String format, boolean fetch_all_ns){
            
		try {
                    
                    model.read(model_file,base_uri,format,fetch_all_ns,false);
                    return true;
		} catch (Exception e) {
                    System.out.println("/***********************************************************/");
                    System.out.println("/***********************************************************/");
                   e.printStackTrace();
		}
                return false;
	}//end read
	
	/**
	 * RDFModelInterface method
	 */
	public boolean read(InputStream input, String base_uri, String format, boolean fetch_all_ns){
		try {
                     System.out.println("modelREAD="+base_uri);
                    model.read(input,base_uri,format,fetch_all_ns,true);
                    return true;
                    /*if(input.markSupported()){
                        input.mark(Integer.MAX_VALUE);
                    }
                    model.read(input,null,format,fetch_all_ns,true);
                } catch (ParsingException e) {
                    try {
                        
                        if(input.markSupported()){
                            input.reset();
                        }
                        
                        
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }*/
		} catch (Exception e) {
			e.printStackTrace();
                        return false;
		}
	}//end read
        
        public boolean write(String file,String Uri){
            try{
                model.write_file(file,RDF_Model.TRIG,Uri,false,false );
                return true;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
        
        public RDF_Model getModel() { return this.model; }
        
}//end class RDFModel