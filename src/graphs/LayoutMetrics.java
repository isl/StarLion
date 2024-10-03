package graphs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Contains the quality metrics of the layout
 * @author zabetak
 */
public class LayoutMetrics {

    private DecimalFormat dformat = new DecimalFormat("##.###");
    //Layout Parameters
    private Hashtable<String,Node>  nodes = null;
    private ArrayList<Edge>         edges = null;
    private ArrayList<Edge>         isANinstanceEdges = null;
    //Average width and height of nodes
    private double                  nodesWidth;
    private double                  nodesHeigth;
    //Layout min and max bounds
    private int                     LminX = 1000;
    private int                     LmaxX = 0;
    private int                     LminY = 1000;
    private int                     LmaxY = 0;
    
    
    //Quality Metrics
    private double                  verticality;
    private double                  nodeDensity;
    private double                  edgeDensity;
    
    /**
     * Creates an empty instance of the metrics
     */ 
    public LayoutMetrics(){
        
    }
    
    /**
     * Creates the metrics based on the given collection of nodes and edges
     * 
     * @param nCollection - the collection of nodes
     * @param eCollection - the collection of edges
     */
    public  LayoutMetrics(Collection<Node> nCollection,Collection<Edge> eCollection){  
        addNodes(nCollection);
        addEdges(eCollection);
    }
    
    /**
     * Adds and replaces the collection of nodes
     * @param nCollection - the new node collection
     */
    public void addNodes(Collection<Node> nCollection){
        double totalNodeWidth = 0;
        double totalNodeHeigth = 0;
        
        nodes = new Hashtable<String, Node>();
        for(Node n:nCollection){
            if(n.isVisible()){
                double nodeWidth = n.getGraphNode().getNodeWidth();
                double nodeHeigth = n.getGraphNode().getNodeHeigth();
                
                nodes.put(n.getName(), n);
                updateMaxLayoutBounds(n.x+(int)nodeWidth, n.y+(int)nodeHeigth);
                updateMinLayoutBounds(n.x, n.y);
                totalNodeWidth += nodeWidth;
                totalNodeHeigth += nodeHeigth;
            }
        }
        nodesWidth = totalNodeWidth/nodes.size();
        nodesHeigth = totalNodeHeigth/nodes.size();
    }
    
    /**
     * 
     * @return the collection of nodes for the current layout metrics
     */
    public Collection<Node> getNodes(){
        return nodes.values();
    }
    
    /**
     * Adds and replaces the collection of edges
     * @param eCollection - the new edge collection
     */
    public void addEdges(Collection<Edge> eCollection){
        isANinstanceEdges = new ArrayList<Edge>();
        edges = new ArrayList<Edge>();
        
        for(Edge e:eCollection){
            if(e.visible){
                edges.add(e);
                if(e.rdfObjectType == SEMWEB_OBJECT_TYPE.SUBCLASSOF || e.rdfObjectType == SEMWEB_OBJECT_TYPE.INSTANCEOF){
                    isANinstanceEdges.add(e);
                }
            }
            
        }
    }
    
    /**
     * 
     * @return the collection of edges for the current layout metrics
     */
    public Collection<Edge> getEdges(){
        return edges;
    }
    
    private int edgeSize(ArrayList<Edge> edg){
        HashMap <String, Integer> map = new HashMap<String, Integer>();
        
        for(Edge e:edg){
            String srcName = e.getSourceNode().getName();
            String trgName = e.getTargetNode().getName();
            if(srcName.compareToIgnoreCase(trgName) <= 0)
                map.put(srcName+trgName, 0);
            else
                map.put(trgName+srcName, 0);
        }
            
        
        return map.size();
    }
    
    private void updateMaxLayoutBounds(int x,int y){
        
        if(x > LmaxX){
            LmaxX = x;
        }
        
        if(y > LmaxY){
            LmaxY = y;
        }
    }
    
    private void updateMinLayoutBounds(int x,int y){
        if(x < LminX){
            LminX = x;
        }
        if(y < LminY){
            LminY = y;
        }
    }
    
   
    /**
     * Calculates the verticality based on the layout parameters
     */
    public void calculateVerticality(){
        double S = 0;
        for(Edge e:isANinstanceEdges){
            Node a = e.getSourceNode();
            Node b = e.getTargetNode();
            double Lab = calculateEdgeL(e);
            S += (b.y - a.y)/Lab;
            
        }
        verticality = -S/isANinstanceEdges.size();
        System.out.println("isA and instanceOF Edges:"+isANinstanceEdges.size());
    }
    
    private double calculateEdgeL(Edge e){
        Node a = e.getSourceNode();
        Node b = e.getTargetNode();
        
        double h = Math.abs(b.y - a.y);
        double w = Math.abs(b.x - a.x);
        double L = Math.sqrt(Math.pow(h,2.0) +Math.pow(w,2.0));
        return L;
    }
    
    
    /**
     * Calculated the node density based on the layout parameters
     */
    public void calculateNodeDensity(){
        int N = nodes.size();
//        int R = 2;
        int L = 0;
        double areaSize = (LmaxX - LminX)*(LmaxY - LminY);
        double idealSize;// = (R*2*(L+nodesHeigth))*(R*2*(L+nodesWidth)) ;
        if(N % 2 == 0){
            idealSize = Math.pow((N/2), 2.0)*(nodesHeigth+L)*(nodesWidth+L);
        }else{
            idealSize = Math.pow((N/2)+1, 2.0)*(nodesHeigth+L)*(nodesWidth+L);
        }
        
        nodeDensity = areaSize/idealSize;
    }
    
    /**
     * Calculate the edge density based on the layout parameters
     */
    public void calculateEdgeDensity(){
        int N = nodes.size();
        double S = 2*edgeSize(edges);//edges.size();
//        if(S > N*(N-1)){
//            S = N*(N-1);
//        }

        edgeDensity = 1 - S/(N*(N-1));
        System.out.println("ED="+edgeDensity);
    }
    
    /**
     * 
     * @return the verticality of the layout
     */
    public double getVerticality(){
        return verticality;
    }
    
    /**
     * 
     * @return the verticality of the layout as string ##.###
     */
    public String getStringVerticality(){
        return dformat.format(getVerticality());
    }
    
    /**
     * 
     * @return the node density of the layout
     */
    public double getNodeDensity(){
        return nodeDensity;
    }
    
    /**
     * 
     * @return the node density of the layout as string ##.###
     */
    public String getStringNodeDensity(){
        return dformat.format(getNodeDensity());
    }
    
    /**
     * Calculated and return the edge density of the layout
     * 
     * @return the edge density of the layout
     */    
    public double getEdgeDensity(){
        return edgeDensity;
    }
    
    /**
     * 
     * @return the edge density of the layout as string ##.###
     */
    public String getStringEdgeDensity(){
        return dformat.format(getEdgeDensity());
    }
    
    /**
     * 
     * @return the number of nodes that the layout contains
     */
    public int get_N_(){
        return nodes.size();
    }
    
    /**
     * 
     * @return number of nodes that the layout contains as string ##.###
     */
    public String getString_N_(){
        return dformat.format(get_N_());
    }
    /**
     * 
     * @return the number of edges that the layout contains
     */
    public int get_E_(){
        return edgeSize(edges);//edges.size();
    }
    
    /**
     * 
     * @return the number of edges that the layout contains as string ##.###
     */
    public String getString_E_(){
        return dformat.format(get_E_());
    }
}
