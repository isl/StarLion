/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphs;

import java.util.ArrayList;
import java.util.Random;

/**
 * Performs performance checks and measurements over the graph's cache
 *      
 * @author zabetak
 */
public class CachePerformance {
    private InstanceCache cache;
    private Random rGen;
    private Graph cacheGraph;  
    //Some common information for the random generation of instances to be added
    //and retrieved
    private String instancePrefix = "Instance";
    private String instanceNeighboursPrefix = "Neighbour";
    private int     maxNeighboursNumber = 20;
    
    /**
     * Creates the performance frame for checking the cache
     * 
     * @param cache - the cache in which we are doing the experiments
     */
    public CachePerformance(InstanceCache cache){
        this.cache = cache;
        rGen = new Random();
        cacheGraph = cache.getAssociateGraph();
    }
    
    /**
     * Check the time need to add instances in cache
     * 
     * @param numberOfAdds - the number of adds in the cache
     */
    public long addingSpeed(int numberOfAdds){
        System.out.println("Start:Cache Adding Speed");

        InstanceRecord ir;
        long taTime = 0;
        for (int i = 0; i < numberOfAdds; i++) {
            String addInstanceName = instancePrefix + i;
            ir = generateRandomIRecord(addInstanceName, addInstanceName + instanceNeighboursPrefix, rGen.nextInt(maxNeighboursNumber));
            long aTime = System.nanoTime();
            cache.add(ir);
            aTime = System.nanoTime() - aTime;
            System.out.println("Instance with name =" + addInstanceName + " added in " + aTime + "ns");
            taTime += aTime;

        }
        taTime = taTime / numberOfAdds;
        System.out.println("Average Time For Inserting " + numberOfAdds + " instances is " + taTime + "ns");
        System.out.println("End:Cache Adding Speed");
        return taTime;
    }
    
    /**
     * Check the time that is needed to check if one instance is in the cache
     * 
     * @param numberOfretrieves - the number of checks that will be done
     */
    public long containsSpeed(int numberOfchecks){
        System.out.println("Start: Cache Contain Speed");
        long tcTime = 0;
        for (int i = 0; i < numberOfchecks; i++) {
            String searchInstance = instancePrefix + rGen.nextInt(maxNeighboursNumber);
            long cTime = System.nanoTime();
            cache.contains(searchInstance);
            cTime = System.nanoTime() - cTime;

            System.out.println("Instance with name =" + searchInstance + " was checked for containment in " + cTime + "ns");
            //Keep total time to extract an average
            tcTime += cTime;
        }
        tcTime = tcTime / numberOfchecks;
        System.out.println("Average Time For ContainingCheck on " + numberOfchecks + " instances is " + tcTime + "ns");
        System.out.println("End: Cache Contain Speed");
        return tcTime;
    }
    
    /**
     * Check the time that is needed to retrieve one instance if it is in the cache
     * 
     * @param numberOfretrieves - the number of retrieves that will be done
     */
    public long retrieveSpeed(int numberOfretrieves){
        System.out.println("Start: Cache Retrieve Speed");
        long trTime = 0;
        for (int i = 0; i < numberOfretrieves; i++) {
            String searchInstance = instancePrefix + rGen.nextInt(maxNeighboursNumber);
            long rTime = System.nanoTime();
            cache.get(searchInstance);
            rTime = System.nanoTime() - rTime;

            System.out.println("Instance with name =" + searchInstance + " retrieved in " + rTime + "ns");
            //Keep total time to extract an average
            trTime += rTime;
        }
        trTime = trTime / numberOfretrieves;
        System.out.println("Average Time For ContainingCheck on " + numberOfretrieves + " instances is " + trTime + "ns");
        System.out.println("End: Cache Contain Speed");
        return trTime;
    }
    
    //Generate a random instance record ( A center node and some neighbours connected to it )
    private InstanceRecord generateRandomIRecord(String instanceName, String instanceNeighboursPrefix,int numberOfConnectedNodes){
            ArrayList<Node> nodes = new ArrayList<Node>();
            ArrayList<Edge> edges = new ArrayList<Edge>();
            
            Node node = new Node(cacheGraph, null, instanceName, 0, 0, 0, cacheGraph.getGraphNodesWidth(),cacheGraph.getGraphNodesHeigth(), instanceName, SEMWEB_OBJECT_TYPE.CLASSINSTANCE);
            nodes.add(node);
            
            char dSuffix = 'A';
            for(int i = 0;i< numberOfConnectedNodes;i++){
                String nNodeName = instanceNeighboursPrefix+dSuffix;
                Node nNode = new Node(cacheGraph, null, instanceName, 0, 0, 0, cacheGraph.getGraphNodesWidth(),cacheGraph.getGraphNodesHeigth(), instanceName, SEMWEB_OBJECT_TYPE.CLASSINSTANCE);
                Edge e = new Edge(cacheGraph, node, nNode, node+nNodeName, SEMWEB_OBJECT_TYPE.PROPERTYINSTANCE, 0, true);
                node.addEdgeFrom(e);
                nNode.addEdgeTo(e);
                nodes.add(nNode);
                edges.add(e);
                dSuffix++;
            }
            
            InstanceRecord ir = new InstanceRecord(node.getName(), nodes, edges);
            
            return ir;
        }
}
