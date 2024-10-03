/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TestFiles;

import gr.forth.ics.rdfsuite.swkm.model.IRDF_Class;
import gr.forth.ics.rdfsuite.swkm.model.IRDF_Individual;
import gr.forth.ics.rdfsuite.swkm.model.IRDF_Resource;
import gui.Project;

/**
 * A class to test the functionality of the project
 * @author zabetak
 */
public class ConsoleProjectSample {

    public static void main(String[] args) {
        String dbHost = "http://139.91.183.30";
        int dbPort = 3025;
        String dbWebContext = "SwkmMiddlewareWS";

        String dbName = "marketak_db";
        String dbUserName = "swtech";
        String dbPasswd = "";

        
        String localdoc = "/home/zabetak/Desktop/LOM2.rdf";
        String webdoc = "http://139.91.183.30:3026/RQLdemo/culture.rdf";
        String txtdoc = "/home/zabetak/Desktop/derivedDataNewNew.txt";

        String datadoc = "http://cidoc.ics.forth.gr/rdfs/caspar/avis_in.rdf";
        
        Project proj = new Project("MyNewProject");
//        proj.addDocument(classpathdoc,Project.LOCATION_TYPE.CLASSPATH);
//        proj.addDocument(localdoc, Project.LOCATION_TYPE.LOCAL,null);
//        proj.addDocument(webdoc, Project.LOCATION_TYPE.URL,null);
//        proj.addDocument(txtdoc, Project.LOCATION_TYPE.TXT,null);
        proj.addDocument(datadoc, Project.LOCATION_TYPE.URL,null);
//
//        DbSettings dbs = new DbSettings();
//        dbs.setDbName(dbName);
//        dbs.setUsername(dbUserName);
//        dbs.setPassword(dbPasswd);
//        Client client = null;
//        try {
//            client = ClientFactory.newClientToWebApp(dbHost, dbPort, dbWebContext).with(dbs);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            
//        }
//
//        
//        String[] namespaces = new String[1];
//        namespaces[0] = "http://cidoc.ics.forth.gr/rdfs/caspar/profile_schema.rdfs#";
//        
//
//        Set<String> defaultNameSpaces = ModelUtils.getDefaultNamespaces();
//        for (String dNSpace : defaultNameSpaces) {
//            if (dNSpace.equals(namespaces[0])) {
//                System.out.println("Cannot export default namespace");
//            }
//        }
//        Map<String, RdfDocument> results = client.exporter().fetch(Arrays.asList(namespaces), Format.RDF_XML, Deps.WITH, Data.WITHOUT);
//
//        
//        proj.addDocuments(results.values());
//        proj.removeDocument(webdoc+"#");
//        proj.removeDocument(localdoc+"#");
//        for(RdfDocument d :results.values()){
//            proj.removeDocument(d.getURI());
//        }
        for (String s : proj.getProjectNamespaces()) {
            System.out.println(s);
        }
        for(IRDF_Class c:proj.getModel().getModel().getClasses(false,false)){
            for(IRDF_Resource r:c.getInstances()){
                System.out.println(((IRDF_Individual)r));
            }
        }
    }
}
