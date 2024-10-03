package model;

import java.io.InputStream;

/**
 * Public interface in order to emulate RDF_Model methods through an independent API 
 * @author leonidis
 */
public interface RDFModelInterface {
	/**
	 * Returns model's RDFNamespace object specified by namespace_uri
	 * @param namespace_uri requested namespace's uri
	 * @return requested RDFNamespace
	 */
	public RDFNamespace getNamespace(String namespace_uri);
	
	/**
	 * Populate RDF_Model by reading from file
	 * @param model_file string contains file's name
	 * @param base_uri base URI for this model
	 * @param fetch_all_ns boolean indicating whether to fetch all depending namespaces
         * @return true if the read succeed,false otherwise
	 */
	public boolean read(String model_file, String base_uri,String format,boolean fetch_all_ns);
	
	/**
	 * Populate RDF_Model by reading an input source
	 * @param input input stream that contains data
	 * @param base_uri base URI for this model
	 * @param format format of provided data
	 * @param fetch_all_ns boolean indicating whether to fetch all depending namespaces
         * @return true if the read succeed,false otherwise
	 */
	public boolean read(InputStream input, String base_uri, String format, boolean fetch_all_ns);
        
        /**
         * Write the RDF_Model in a file
         * @param file - the file that we want to write the triple
         * @param Uri - the Uri of the file
         * @return true if the write was successfull and false otherwise
         */
        public boolean write(String file,String Uri);
}//end interface RDFModelInterface