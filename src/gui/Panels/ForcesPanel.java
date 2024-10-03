/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.Panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * A panel that shows the direction of a vector(In StarLion used to show forces direction)
 * @author zabetak
 */
public class ForcesPanel extends JPanel {

//    private Rectangle boundingBox[] = new Rectangle[11];
//    private Point           drawPoints[] = new Point[11];
    
    private BufferedImage animationFilm;
    private BufferedImage background;
//    MainMethod used for testing and debuging    
//    public static void main(String args[]){
//        JFrame f = new JFrame();
//        f.setSize(250, 250);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        f.getContentPane().add( new ForcesPanel(500,500,300,400));
////        ForcesPanel fp = new ForcesPanel(100,100,100,50);
//        ForcesPanel fp = new ForcesPanel(100,100,0,-8);
//        JPanel p = new JPanel();
//        p.setSize(200,200);
//        
//        p.add(fp);
////        p.add(p)
////        p.getCo(fp);
//        p.add(new JLabel("Test"));
//        f.add(p);
////        f.add(new JLabel("TEst"));
//        f.setVisible(true);
////        fp.go();
//        
//    }
    
    private int sx;
    private int sy;
    private int ex;
    private int ey;
    private Point sForce;
    private int quadrant = 0;
    
    private int rotation = 0;
    
    
    public ForcesPanel(int startX,int startY,int endX,int endY) {
        this.sx = startX;
        this.sy = startY;
        this.ex = endX;
        this.ey = endY;
        sForce = new Point(ex-startX,ey-startY);
        quadrant = findQuadrant(sForce);
//        Was USED for sprite moving
//        initBoundingBoxes();
//        initDrawingPoints();
        loadBitmaps();
        

        setMinimumSize(new Dimension(200,200));
        setPreferredSize(new Dimension(200,200));
        
    }
    
    //Find in which quadrant the point p belongs
    private int findQuadrant(Point p){
        int qr = 0;
        if(p.x >= 0 && p.y >= 0){
            qr = 1;
        }else if(p.x < 0 && p.y >= 0){
            qr = 2;
        }else if(p.x <= 0 && p.y <= 0){
            qr = 3;
        }else if(p.x >= 0 && p.y <= 0){
            qr = 4;
        }
        return qr;
    }
    
    
    //Transalte the point p in the symmetric point in first quadrant
    private Point translate2FstQuadrant(Point p){
        int qr = findQuadrant(p);
        Point nP = null;
        switch(qr){
            case 1:
                nP = new Point(p);
                break;
            case 2:
                nP = new Point(-p.x,p.y);
                break;
            case 3:
                nP = new Point(-p.x,-p.y);
                break;
            case 4:
                nP = new Point(p.x,-p.y);
                break;
        }
        return nP;
    }
    
    //Given a point p calculate the angle with startX'startX
    private double calculateAngle(Point p){
        Point pivot = new Point(40,0);
        Point tp = translate2FstQuadrant(p);
        int innerProduct = tp.x*pivot.x + tp.y*pivot.y;
        double ltp = length(tp);
        double lpivot = length(pivot);
        
        double res = innerProduct/(ltp*lpivot);
        
        double radians = Math.acos(res);
        System.out.println("Radians="+radians);
        double degrees = Math.toDegrees(radians);
        System.out.println("Degrees="+degrees);
        int qr = findQuadrant(p);
        System.out.println("Qr="+qr);
        switch(qr){
            case 1:
                break;
            case 2:
                degrees += 90;
                break;
            case 3:
                degrees += 180;
                break;
            case 4:
                degrees += 270;
                break;
        }
        System.out.println("Degrees="+degrees);
        return degrees;
    }
//    
//    private void initBoundingBoxes(){
//        File f = new File("bboxes.csv");
//        try {
//            FileReader fr = new FileReader(f);
//            BufferedReader br = new BufferedReader(fr);
//            int i = 0;
//            while(br.ready()){
//                String input[] = br.readLine().split(";");
//                boundingBox[i] = new Rectangle(Integer.parseInt(input[0]),Integer.parseInt(input[1]),Integer.parseInt(input[2]),Integer.parseInt(input[3]));
//                i++;
//            }
//            
//            
//            
//        } catch (Exception ex) {
//            Logger.getLogger(ForcesPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    
//    }
//    
//    private void initDrawingPoints(){
//        File f = new File("drawPoints.csv");
//        try {
//            FileReader fr = new FileReader(f);
//            BufferedReader br = new BufferedReader(fr);
//            int i = 0;
//            while(br.ready()){
//                String input[] = br.readLine().split(";");
//                drawPoints[i] = new Point(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
//
//                i++;
//            }
//            
//            
//            
//        } catch (Exception ex) {
//            Logger.getLogger(ForcesPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    //Load the bitamps(Background and arrow)
    private void loadBitmaps(){
        java.net.URL arrowURL = getClass().getResource("/config/arrow.gif");
        java.net.URL backURL = getClass().getResource("/config/back.jpg");
        try {
            animationFilm = ImageIO.read(arrowURL);
            background = ImageIO.read(backURL);
        } catch (IOException ex) {
            Logger.getLogger(ForcesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    //The length of a vector with final point p
    private double length(Point p){
        return Math.sqrt(Math.pow((p.x),2.0) + Math.pow((p.y),2.0));
        
    }
    

    
//    private int angleToBBox(double a){
//        return (int)(a/11);
//    }
//    

    @Override
    public void paintComponent(Graphics g){
        
        g.drawImage(background, 0, 0, this);
        

        AffineTransform affineTransform = new AffineTransform();
        //set the translation to the mid of the component
        affineTransform.setToTranslation(100,70); 
        //rotate with the anchor point as the mid of the image
        //affineTransform.rotate(Math.toRadians(rotation), 0, animationFilm.getHeight()/2);
        double rot = calculateAngle(sForce);
        affineTransform.rotate(Math.toRadians(rot), 0, animationFilm.getHeight()/2); 

        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(animationFilm, affineTransform, this);

    }
    
    /**
     * Shows an animated arrow which turns 360 degrees + the angle of the sForce
     * and stops there
     */
    public void animation(){
        double rot = calculateAngle(sForce);
        System.out.println("Angle ="+rot+"Qr = "+quadrant+"ForceP ="+sForce);
        while(rotation < 360+rot){
        getGraphics().drawImage(background, 0, 0, this);
        try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ForcesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
        repaint();
        rotation += 10;
        }
        
    }
}
