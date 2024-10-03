/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphs.save;

import graphs.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * This class is responsible for save operations. Initially only nodes positions
 * are saved
 *
 * @author zabetak
 */
public class GraphState {
    private Graph g;
    private ArrayList<NodeInfo> nodePositions;
    /**
     * The constructor watches for the graph given as parameter
     *
     * @param g - the graph
     */
    public GraphState(Graph g){
        this.g = g;
        nodePositions = new ArrayList<NodeInfo>();
    }

    /* Must be added later
    public void saveNodesPosition(){
        for(Node n:g.getNodeList().values()){
            NodeInfo info = new NodeInfo(n);
            nodePositions.add(info);
        }
    }
    */
    public void saveNodesPositionInFile(File f){
        //Open the stream for writting
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f,true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LayoutMetrics2.class.getName()).log(Level.SEVERE, null, ex);
        }
        String header = "NodeName;X;Y;Width;Height;Namespace\n";
        try {
            fos.write(header.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(GraphState.class.getName()).log(Level.SEVERE, null, ex);
        }

        for(Node n:g.getNodeList().values()){
            NodeInfo info = new NodeInfo(n);
            try {
                fos.write(info.toStringCSV().getBytes());
            } catch (IOException ex) {
                Logger.getLogger(GraphState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(GraphState.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void restoreNodesPositionFromFile(File f){

        FileReader fr = null;
        try {

            fr = new FileReader(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphState.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader br = new BufferedReader(fr);
        String line = null;
        try {
            line = br.readLine();//Ignore the first line which is the title
            line = br.readLine();//Read the second
        } catch (IOException ex) {
            Logger.getLogger(GraphState.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(line != null){
            
            NodeInfo info = new NodeInfo(line);
            g.getNodeList().get(info.getName()).setX(info.getX());
            g.getNodeList().get(info.getName()).setY(info.getY());
            try {
                line = br.readLine();
            } catch (IOException ex) {
                Logger.getLogger(GraphState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(GraphState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
