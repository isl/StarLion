/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphs;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TURBO_X
 */
public class MetricsEvaluation {

    private Graph g;

    public MetricsEvaluation(Graph graph){
        g = graph;
    }

    private void autoStarView(String node,int R){
            g.activateStarGraph();
            g.setSelectedNode(node);
            g.setStarViewRadius(R);
            g.executeStarView();
            String params = "1,150,500000,50,100,0,5,5,10";
            g.forceDirectedPlacement(params, true);
    }

    private String measureConstant(){
        LayoutMetrics2 lm2 = g.getLayoutMetrics2();
        lm2.calculateMetrics();
        String N = lm2.getString_N_();
        String E = lm2.getString_E_();
        String mxSubs = String.valueOf(lm2.getMaxSubs());
        String mxSups = String.valueOf(lm2.getMaxSups());
        return ";"+N+";"+E+";"+mxSubs+";"+mxSups;
    }

    private String measureDynamic(){
        LayoutMetrics2 lm2 = g.getLayoutMetrics2();
        lm2.calculateMetrics();
        String AI = lm2.getStringAImprove();
        String VI = lm2.getStringVImprove();
        return ";"+VI+";"+AI;

    }

    private void improveLayout(){
        g.applyMagicCorrection("Both");
    }


    public void determineKmKeRelation(String exportFileName,double initKm,double initKe,
                                                            int stepKm,int stepKe,
                                                            double finalKm,double finalKe){
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;

        g.setSmoothTransitions(false);
        File exFile = new File(exportFileName);
        try {
            fos = new FileOutputStream(exFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MetricsEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
        osr = new OutputStreamWriter(fos);
        bw = new BufferedWriter(osr);



        String tableHeader = "Ke;Km;V\n";
        try {
            bw.write(tableHeader);
        } catch (IOException ex) {
            Logger.getLogger(MetricsEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.setKm(initKm);
        g.setKe(initKe);
        g.forceDirectedPlacement(null, true);//Default FDPA
        LayoutMetrics2 lm2 = g.getLayoutMetrics2();
        lm2.calculateMetrics();
        double V = lm2.getVerticality();
        String output = g.getKe()+";"+g.getKm()+";"+V+"\n";
        try {
            bw.write(output);
        } catch (IOException ex) {
            Logger.getLogger(MetricsEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.getState().saveNodesPositionInFile(new File("KeKmRel.pos"));
        double threshold = 0.02;

        for(double Km = initKm+stepKm;Km < finalKm;Km += stepKm){
            g.setKm(Km);
            g.forceDirectedPlacement(null, true);
            lm2.calculateMetrics();
            double Ke = g.getKe();
            double testV = lm2.getVerticality();
            while(!(testV < V+threshold && testV > V-threshold)){
                g.getState().restoreNodesPositionFromFile(new File("KeKmRel.pos"));
                Ke += stepKe;
                g.setKe(Ke);
                g.forceDirectedPlacement(null,true);
                lm2.calculateMetrics();
                testV = lm2.getVerticality();
                
                System.out.println("Ke="+Ke+",V="+V+",TestV="+testV);
            }
            System.out.println("Km="+Km);
            output = g.getKe()+";"+g.getKm()+";"+testV+"\n";
            try {
                bw.write(output);
            } catch (IOException ex) {
                Logger.getLogger(MetricsEvaluation.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        try {
            bw.close();
            osr.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    /**
     * Execute automatic actions(based on StarView) and export the quality
     * measurements in the specified file
     *
     * @param importFileName - the name of the file that desribes the actions
     * @param exportFileName - the name of the file that the exported results
     * are written
     */
    public void automaticActions(String importFileName,String exportFileName,int magicPress) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        g.setSmoothTransitions(false);
        try {
            File exFile = new File(exportFileName);
            File imFile = new File(importFileName);
            fos = new FileOutputStream(exFile);
            fis = new FileInputStream(imFile);
            InputStreamReader isr = new InputStreamReader(fis);
            OutputStreamWriter osr = new OutputStreamWriter(fos);
            BufferedReader br = new BufferedReader(isr);
            BufferedWriter bw = new BufferedWriter(osr);

            String tableConstantPart = "Node;N;E;MaxSubs;MaxSups";
            String dColumns[] = {"VI","AI"};
//            String header = "Schema;N;E;IV-V;A;IV-V;A;IV-V;A\n";
            String header = tableHeaderCreator(tableConstantPart, dColumns, magicPress+1);
            
            bw.write(header);
//            fos.write(header.getBytes());

            String input = br.readLine();
            while(input != null){
                String tokens[] = input.split("\t");
                //Polu e3upno to apo katw mprabo mou:D
                Hashtable<String,Point> res = g.find(tokens[0],"classFind");


                if(res.size() != 0){
                    
//                    for(int i = 0;i<magicPress;i++){
                        String nodeName = res.keySet().iterator().next();
                        String R = tokens[1];
                        String row_metrics = tableRowCreator(nodeName,Integer.parseInt(R), magicPress);
                        String row = nodeName+" R="+R+row_metrics;
                        bw.write(row);
//                    }
//                    String buffer = automaticStarViewOn(res.keySet().iterator().next(),Integer.parseInt(tokens[1]),2);
//                    fos.write(buffer.getBytes());
                }
                input = br.readLine();
            }

//
            bw.close();
            br.close();
            isr.close();
            osr.close();
            fis.close();
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } 


    }

    private String tableHeaderCreator(String constantPart,String[] dynamicColumns,int repeatedTimes){
        String dynamicPart = "";
        for(int i = 0;i < repeatedTimes;i++){
            for(int j=0;j<dynamicColumns.length;j++){
                dynamicPart += ";"+dynamicColumns[j]+"("+i+")";
            }
        }
        return constantPart+dynamicPart+"\n";
    }

    private String tableRowCreator(String node,int R,int improvementTimes){
        autoStarView(node, R);
//        g.getState().restoreNodesPositionFromFile(new File("initPOS"+node+R));
        String constantPart = measureConstant();
        String dynamicPart = measureDynamic();
//        saveMetrics(new File("2_AllMetrics.csv"), "10","BeforeAACTION "+node+"+"+R);
        for(int i=0;i < improvementTimes;i++){
            improveLayout();
            dynamicPart += measureDynamic();
        }
//        saveMetrics(new File("2_AllMetrics.csv"), "10","AfterAACTION "+node+"+"+R);
        return constantPart+dynamicPart+"\n";

    }

    /**
     * For the current instance of the graph it saves the quality metrics and some
     * informations about the graph and fdpa along with a ranking which shows how
     * good is the layout
     *
     * @param f - the file to save the results
     * @param ranking - the ranking of the current instance
     */
    public void saveMetrics(File f,String ranking,String note){
        //Open the stream for writting
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f,true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LayoutMetrics2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //The current date and time are created
       Calendar c = Calendar.getInstance();
       String date = c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR)+" "
                    +c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);


       LayoutMetrics2 lm2 = g.getLayoutMetrics2();
       lm2.calculateMetrics();

       DecimalFormat df = new DecimalFormat("##.###");

       //Create the string to be saved
       String graphPart = lm2.getString_N_()+";"+lm2.getString_E_()+";"+lm2.getMaxSubs()+";"+lm2.getMaxSups();

       String verticalityPart = df.format(lm2.getVerticality())+";"
                               +df.format(lm2.getIdealVerticality())+";"+df.format(lm2.getVImprove());

       String areaPart = df.format(lm2.getArea())+";"+df.format(lm2.getIdealArea())+";"+
                         df.format(lm2.getAImprove());

       String fdpaPart = df.format(g.getKe())+";"+df.format(g.getKm())+";"+g.getMaxIterations();

       //the form of the result is:"dd/mm/yy hh:mm:ss;N;E;MaxSubs;MaxSups;V;IV;IV-V;A;IA;AI;Ke;Km;Iterations;Ranking;Note"
       String res = date+";"+graphPart+";"+verticalityPart+";"+areaPart+";"+fdpaPart+";"+ranking+";"+note+"\n";
        try {
            fos.write(res.getBytes());
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(MetricsEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
