/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphs.save;

import graphs.Node;

/**
 * Keeps information about a node
 *
 * @author zabetak
 */
public class NodeInfo {
    private int x;
    private int y;
    private double w;
    private double h;
    private String name;
    private String ns;

    /**
     * Extract node's information from the given node
     * @param n - the node
     */
    public NodeInfo(Node n){
        x = n.getGraphNode().getXposition();
        y = n.getGraphNode().getYposition();
        w = n.getGraphNode().getNodeWidth();
        h = n.getGraphNode().getNodeHeigth();
        name = n.getName();
        ns = n.getNodeNamespace();

    }

    public NodeInfo(String csvformat){
        String info[] = csvformat.split(";");
        name = info[0];
        x = Integer.parseInt(info[1]);
        y = Integer.parseInt(info[2]);
        w = Double.parseDouble(info[3]);
        h = Double.parseDouble(info[4]);
        ns = info[5];
    }

    /**
     *
     * @return the x coordinate of the node
     */
    public int getX(){
        return x;
    }

    /**
     *
     * @return the y coordinate of the node
     */
    public int getY(){
        return y;
    }
    /**
     *
     * @return the width of the node
     */
    public double getWidth(){
        return w;
    }
    /**
     *
     * @return the heigth of the node
     */
    public double getHeight(){
        return h;
    }

    /**
     *
     * @return the name of the node
     */
    public String getName(){
        return name;
    }
    /**
     *
     * @return the namespace of the node
     */
    public String getNamespace(){
        return ns;
    }

    /**
     *
     * @return the string representation of the node's information
     */
    @Override
    public String toString(){
        return name+"("+x+","+y+") width = "+w+", height = "+h+" in "+ns+"\n";
    }

    /**
     *
     * @return the string representation of the node's information in csv form
     */
    public String toStringCSV(){
        return name+";"+x+";"+y+";"+w+";"+h+";"+ns+"\n";
    }


}
