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
public class LayoutMetrics2 {

    private DecimalFormat dformat = new DecimalFormat("##.###");
    //Layout Parameters
    private Hashtable<String,Node>  nodes = null;

    private Hashtable<String,Node>  subclassNodes = null;
    private Hashtable<String,Node>  supclassNodes = null;

    private Hashtable<String,Integer> nodesSubClasses;
    private Hashtable<String,Integer> nodesSupClasses;

    private int maxSubs = 0;
    private int maxSups = 0;

    private ArrayList<Edge>         edges = null;
    private ArrayList<Edge>         isANinstanceEdges = null;
    //Average width and height of nodes
    private double                  nodesWidth;
    private double                  nodesHeigth;
    //Average length of edges
    private double                  edgeLength;
    //Layout min and max bounds
    private int                     LminX = 1000;
    private int                     LmaxX = 0;
    private int                     LminY = 1000;
    private int                     LmaxY = 0;


    //Quality Metrics
    private double                  verticality;
    private double                  area;
    private double                  AImprove;//Area Improve
    private double                  VImprove;//Verticality Improve

    /**
     * Creates an empty instance of the metrics
     */
    public LayoutMetrics2(){

    }

    /**
     * Creates the metrics based on the given collection of nodes and edges
     *
     * @param nCollection - the collection of nodes
     * @param eCollection - the collection of edges
     */
    public  LayoutMetrics2(Collection<Node> nCollection,Collection<Edge> eCollection){
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

//    NOT NEEDED IN THE SIMPLER CALCULATION
//    private double decrementFactor_DF(int n){
//        double h = getOptimalVDistance();
//        double b = 2*getAverageNodeWidth();
//        double a1 = Math.sqrt(Math.pow(h,2)+Math.pow(b/2, 2));
//        double an = Math.sqrt(Math.pow(h,2)+Math.pow((b+(n-1)*(b/2))/2, 2));
//        double DV = 1-(h/a1);//Decrement of verticality
//
//        double DF = (an/a1)*DV;
//
//        return DF;
//    }

    private double MinimumVerticality_MV(int n){
        double h = getOptimalVDistance();
        double b = 2*getAverageNodeWidth();
        double an = Math.sqrt(Math.pow(h,2)+Math.pow((b+(n-1)*(b/2))/2, 2));
        double MV = h/an;
        return MV;
    }

    private double getOptimalVDistance(){
        //Optimal Vertical Distance between a node and its subclass,superclass
        return 150;//Ls = 150
    }

    private double getAverageNodeWidth(){
        return nodesWidth;
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
        subclassNodes = new Hashtable<String, Node>();
        supclassNodes = new Hashtable<String, Node>();

        nodesSubClasses = new Hashtable<String,Integer>();
        nodesSupClasses = new Hashtable<String,Integer>();


        isANinstanceEdges = new ArrayList<Edge>();
        edges = new ArrayList<Edge>();

        double sumLen = 0;
        for(Edge e:eCollection){
            if(e.visible){
                edges.add(e);
                if(e.rdfObjectType == SEMWEB_OBJECT_TYPE.SUBCLASSOF || e.rdfObjectType == SEMWEB_OBJECT_TYPE.INSTANCEOF){
                    nodeSeperation(subclassNodes,supclassNodes,e);
                    nodeSeperation2(nodesSubClasses,nodesSupClasses,e);
                    isANinstanceEdges.add(e);
                }
                sumLen += this.calculateEdgeL(e);
            }

        }

        for(Integer sub:nodesSubClasses.values()){
            if(sub>maxSubs){
                maxSubs = sub;
            }
        }
        for(Integer sup:nodesSupClasses.values()){
            if(sup>maxSups){
                maxSups = sup;
            }
        }
        this.edgeLength = sumLen/edges.size();
    }

    //Seperates the nodes of an edge to those belonging to sublclasses and those
    //belonging to superclasses
    private void nodeSeperation(Hashtable<String,Node> sub,Hashtable<String,Node> sup,Edge e){
        Node subN = e.getSourceNode();
        Node supN = e.getTargetNode();
        sub.put(subN.getName(), subN);
        sup.put(supN.getName(), supN);
    }

    private void nodeSeperation2(Hashtable<String,Integer> sub,Hashtable<String,Integer> sup,Edge e){
        Node supN = e.getSourceNode();
        Node subN = e.getTargetNode();
        Integer subi = sub.get(subN.getName());
        if(subi ==null){
            sub.put(subN.getName(), new Integer(1));
        }else{
            sub.put(subN.getName(),new Integer(subi+1));
        }
        Integer supi = sup.get(supN.getName());
        if(supi ==null){
            sup.put(supN.getName(),new Integer(1));
        }else{
            sup.put(supN.getName(),new Integer(supi+1));
        }
    }

    public int getHavesups(){
        return supclassNodes.size();
    }

    public int getMaxSubs(){
        return maxSubs;
    }

    public int getMaxSups(){
        return maxSups;
    }
    
    public int getHavesubs(){
        return subclassNodes.size();
    }
    
    public int getRsubs(){
        return edgeSize(isANinstanceEdges);
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

    public void calculateMetrics(){
        calculateVerticality();
        calculateVImprove();
        calculateArea();
        calculateAImprove();
    }

    /**
     * Calculates the verticality based on the layout parameters
     */
    private void calculateVerticality(){
        double S = 0;
        for(Edge e:isANinstanceEdges){
            Node a = e.getSourceNode();
            Node b = e.getTargetNode();
            double Lab = calculateEdgeL(e);
            double v = (b.y - a.y)/Lab;
            
            if(Double.isNaN(v)){
                S += 1;
            }else{
                S+= v;
            }
        }
        if(isANinstanceEdges.isEmpty()){
            verticality = 1;
        }else{
            double V = -S/isANinstanceEdges.size();
            verticality = V;
        }
//        verticality = V;

    }


    /**
     * Calculates the verticality improvement based on V and IV
     */
//    private void calculateVImprove(){
//        double IV = getIdealVerticality();
//        double p = 0.5;
//        double IV_N = 1 - (1-IV)*p;
////        double X = Math.min(getHavesups(),getHavesubs())/(double)Math.max(getHavesups(),getHavesubs());
////        double V = -S;
////        double X = Math.min(getHavesups(),getHavesubs());
//
////        if(V > 0){
////            verticality = Math.min(V/X, 1);
////        }else{
////            verticality = Math.max(V/X, -1);
////        }
//        VImprove = IV_N-verticality;
//    }

    private void calculateVImprove(){
        double IV = getIdealVerticality();
        VImprove = IV-verticality;
    }

    private void calculateArea(){
        //System.out.println("LmaxX="+LmaxX+" LminX="+LminX+"LmaxY="+LmaxY+" LminY="+LminY);
        area = (LmaxX - LminX)*(LmaxY - LminY);
    }
    /**
     * Calculates the area improvement based on the layout parameters
     * Values:[-1,1]
     */
    private void calculateAImprove(){
        
        double idealArea = getIdealArea();
        
        AImprove = (idealArea - area)/Math.max(idealArea, area);
//        double p=0.2;
//        AImprove = AImprove*p;
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
     *
     * @return the verticality of the layout
     */
    public double getVerticality(){
        return verticality;
    }

//    OLD VERSION MORE COMPLICATED
//    public double getIdealVerticality(){
////        double IV = Math.min(getHavesups(),getHavesubs())/(double)getRsubs();
////        double IV = 1 - Math.max(this.maxSubs, this.maxSups)*0.05;
//        int n = Math.max(this.maxSubs,this.maxSups);
//        double IV;
//        if(n == 1){
//            IV = 1;
//        }else{
//            IV = 1 - decrementFactor_DF(n)/2;
//        }
//        return IV;
//    }

    public double getIdealVerticality(){
        int n = Math.max(this.maxSubs,this.maxSups);
        double IV = (1+MinimumVerticality_MV(n))/2;
        return IV;
    }
    /**
     *
     * @return the verticality of the layout as string ##.###
     */
    public String getStringVImprove(){
        return dformat.format(getVImprove());
    }

    public double getArea(){
        return area;
    }

    public double getIdealArea(){
        int N = this.get_N_();
        int R = this.get_E_();

        double boxArea = this.nodesHeigth*this.nodesWidth;
        double edgeArea = this.edgeLength*this.nodesHeigth;



        double minArea = N*boxArea+R*edgeArea;
        double idealArea = 4*minArea;
        
        return idealArea;
    }
    /**
     *
     * @return the area improvement value
     */
    public double getAImprove(){
        return AImprove;
    }
    
    /**
     * 
     * @return the verticality improvement value
     */
    public double getVImprove(){
        return VImprove;
    }
    
    /**
     *
     * @return the area improvement value as string ##.###
     */
    public String getStringAImprove(){
        return dformat.format(getAImprove());
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

