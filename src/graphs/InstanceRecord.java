/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphs;

import java.util.ArrayList;
import java.util.Collection;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author zabetak
 */
public class InstanceRecord {
    private ArrayList<Node> nodeList;
    private ArrayList<Edge> edgeList;
    private String          name;

    public InstanceRecord(String instanceName,Collection<Node> nodes,Collection<Edge> edges){
        name = instanceName;
        nodeList = new ArrayList<Node>(nodes);
        edgeList = new ArrayList<Edge>(edges);
    }

    public Collection<Node> getNodes(){
        return nodeList;
    }

    public Node getNode(String nodeName){
        for(Node n:nodeList){
            if(n.getName().equals(nodeName)){
                return n;
            }
        }
        return null;
    }
    
    public Edge getEdge(String edgeName){
        for(Edge e:edgeList){
            if(e.toString().equals(edgeName)){
                return e;
            }
        }
        return null;
    }
    
    public Collection<Edge> getEdges(){
        return edgeList;
    }

    public Collection<DefaultGraphCell> getCells(){
        ArrayList<DefaultGraphCell> asCells = new ArrayList<DefaultGraphCell>();
        for(Node n:nodeList){
            asCells.add(n.getGraphNode());
        }
        for(Edge e:edgeList){
            asCells.add(e.getGraphEdge());
        }
        return asCells;
    }
    public String getName(){
        return name;
    }


    @Override
    public String toString(){
        return name;
    }
}
