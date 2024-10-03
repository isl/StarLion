package TestFiles;

import gr.forth.ics.rdfsuite.swkm.model.impl.RDF_Model;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.RDFModel;
import graphs.Graph;

public class ConsoleUsageSample {

	public static void main(String[] args) throws FileNotFoundException, IOException{
		String rdfFile = "SampleRDFFiles/cidoc_crm_v3.4.9.rdfs";
		String namespaceURI = rdfFile + "#";
		String params = "1,150,500000,50,100,0,5,5,10";
		
		RDFModel model = new RDFModel();
		Graph graph = new Graph();
		
		//*****************************************************
//		File f = new File("data.txt");
//                FileInputStream fis = new FileInputStream(f);
                
		//Populate both RDFModel and graph
		model.read(rdfFile, namespaceURI,RDF_Model.RDF_XML, false);
                String nsArray[] = new String[1];
                nsArray[0] = namespaceURI;
                graph.setGraphNameSpaces(nsArray);
		graph.populateGraph(model/*, namespaceURI,*/);
        graph.createGraphRanker();
        graph.getGraphRanker().generateScoreFile("file1","", "method0", "CIDOC", namespaceURI);
//		graph.populateGraph(fis);
		
		//*****************************************************
      //          graph.TEST();
		//Visualize graph, update layout and save it
		graph.visualizeGraphElements();
		//graph.updateLayout("random", "");
		//graph.updateLayout("forceDirected", params);
		//graph.saveGraph("lala.rdf");
		
		//*****************************************************
		
		//Rank graph and select Top-K nodes and groups respectively
		//graph.rank("method0", "", model, namespaceURI);
		//graph.topKNodes("4");
		//graph.topKGroups("4");
	}//end main
}//end class ConsoleUsageSample