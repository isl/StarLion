/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphs;

/**
 *
 * @author TURBO_X
 */
public class CorrectingActions {
    private double Km;
    private double Ke;
    private double iKe;
    private double iKm;
    private LayoutMetrics2 lm2;

    public CorrectingActions(double Km,double Ke,LayoutMetrics2 layoutM2){
        this.Km = Km;
        this.Ke = Ke;
        this.lm2 = layoutM2;
    }



        public void improveArea(){

//            lm2.calculateAImprove();
            lm2.calculateMetrics();
            double AI = lm2.getAImprove();
//            double oldKe = Ke;
            iKe = Ke * Math.pow(100.0, AI);
            iKm = Km;//Remains Unchanged
//            updateLayout("forceDirected", null,null);
//            Ke = oldKe;//Revert to original value
//            lm.calculateAImprove();
//            return lm.getStringAImprove();
        }
        public void improveVerticality(){
//            LayoutMetrics2 lm = new LayoutMetrics2(nodeList.values(), edgeList.values());
//            lm2.calculateVerticality();
            lm2.calculateMetrics();
            double VI = lm2.getVImprove();
//            double oldKm = Km;
//            Km = Km * Math.pow(100.0, Math.max(1-V, 0));
            iKm = Km * Math.pow(100.0, VI);
            iKe = Ke;//Remains Unchanged
//            updateLayout("forceDirected", null,null);
//            Km = oldKm;//Revert to original value
//            lm.calculateVerticality();
//            return lm.getStringVerticality();
        }
        public void improveBoth(){
//            LayoutMetrics2 lm = new LayoutMetrics2(nodeList.values(), edgeList.values());
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
            lm2.calculateMetrics();
            double AI = lm2.getAImprove();
            double VI = lm2.getVImprove();
            double frnew;
            double tKm;
            double tKe;
            double ttKm;
//            double oldKm = Km;
            tKm = Km * Math.pow(100.0, VI);//STEP 1
            frnew = Ke/tKm;//STEP 2
            tKe = Ke * Math.pow(100.0, AI);//STEP 3
            ttKm = tKe/frnew;//STEP 4

            iKm = ttKm;
            iKe = tKe;

//            iKm = Km * Math.pow(100.0, V);
//            iKe = Ke * Math.pow(100.0, A);
//            updateLayout("forceDirected", null,null);
//            Km = oldKm;//Revert to original value
//            lm.calculateAImprove();
//            lm.calculateVerticality();
//            return lm.getStringVerticality()+";"+lm.getStringAImprove()+";";
        }
        public void improveBoth2(){
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
            lm2.calculateMetrics();
            double AI = lm2.getAImprove();
            double VI = lm2.getVImprove();

            if(Math.abs(VI)>Math.abs(AI)){
                iKm = Km*Math.pow(100.0,AI);
                iKe = Ke;
            }else{
                iKe = Ke*Math.pow(100.0, VI);
                iKm = Km;
            }

            

        }
//        public void improveBoth3(){
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
//            double A = lm2.getAImprove();
//            double V = lm2.getVerticality();
//            double frnew;
//            double tKm;
//            double tKe;
//            double ttKe;
//
//
//            tKe = Ke * Math.pow(100.0, A);//STEP 3
//            frnew = tKe/Km;
//            tKm = Km * Math.pow(100.0, V);//STEP 1
//            ttKe = frnew*tKm;
//
//            iKm = tKm;
//            iKe = ttKe;
//
//        }
//
//        public void improveBoth4(){
////            LayoutMetrics2 lm = new LayoutMetrics2(nodeList.values(), edgeList.values());
//            //IDEAL AREA = 2*MinArea
//            //p=0.5 in IV
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
//            double A = lm2.getAImprove();
//            double V = lm2.getVerticality();
//            System.out.println("A="+A+",V="+V);
//            double frnew;
//            double tKm;
//            double tKe;
//            double ttKm;
////            double oldKm = Km;
//            tKm = Km * Math.pow(100.0, V);//STEP 1
//            frnew = Ke/tKm;//STEP 2
//            tKe = Ke * Math.pow(100.0, A);//STEP 3
//            ttKm = tKe/frnew;//STEP 4
//
//            iKm = ttKm;
//            iKe = tKe;
//            System.out.println("iKm="+iKm+",iKe="+iKe);
////            iKm = Km * Math.pow(100.0, V);
////            iKe = Ke * Math.pow(100.0, A);
////            updateLayout("forceDirected", null,null);
////            Km = oldKm;//Revert to original value
////            lm.calculateAImprove();
////            lm.calculateVerticality();
////            return lm.getStringVerticality()+";"+lm.getStringAImprove()+";";
//        }
//
//        public void improveBoth5(){
////            LayoutMetrics2 lm = new LayoutMetrics2(nodeList.values(), edgeList.values());
//            //IDEAL AREA = 5*MinArea
//            //p=0.5 in IV
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
//            double A = lm2.getAImprove();
//            double V = lm2.getVerticality();
//            System.out.println("A="+A+",V="+V);
//            double frnew;
//            double tKm;
//            double tKe;
//            double ttKm;
////            double oldKm = Km;
//            tKm = Km * Math.pow(100.0, V);//STEP 1
//            frnew = Ke/tKm;//STEP 2
//            tKe = Ke * Math.pow(100.0, A);//STEP 3
//            ttKm = tKe/frnew;//STEP 4
//
//            iKm = ttKm;
//            iKe = tKe;
//            System.out.println("iKm="+iKm+",iKe="+iKe);
////            iKm = Km * Math.pow(100.0, V);
////            iKe = Ke * Math.pow(100.0, A);
////            updateLayout("forceDirected", null,null);
////            Km = oldKm;//Revert to original value
////            lm.calculateAImprove();
////            lm.calculateVerticality();
////            return lm.getStringVerticality()+";"+lm.getStringAImprove()+";";
//        }
//
//        public void improveBoth6(){
////            LayoutMetrics2 lm = new LayoutMetrics2(nodeList.values(), edgeList.values());
//            //IDEAL AREA = 2*MinArea
//            //p=0.5 in IV
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
//            double A = lm2.getAImprove();
//            double V = lm2.getVerticality();
//            System.out.println("A="+A+",V="+V);
//            double frnew;
//            double tKm;
//            double tKe;
//            double ttKm;
////            double oldKm = Km;
//            tKm = Km * Math.pow(100.0, V);//STEP 1
//            frnew = Ke/tKm;//STEP 2
//            tKe = Ke * Math.pow(100.0, A);//STEP 3
//            ttKm = tKe/frnew;//STEP 4
//
//            iKm = ttKm;
//            iKe = tKe;
//            System.out.println("iKm="+iKm+",iKe="+iKe);
////            iKm = Km * Math.pow(100.0, V);
////            iKe = Ke * Math.pow(100.0, A);
////            updateLayout("forceDirected", null,null);
////            Km = oldKm;//Revert to original value
////            lm.calculateAImprove();
////            lm.calculateVerticality();
////            return lm.getStringVerticality()+";"+lm.getStringAImprove()+";";
//        }
//public void improveBoth7(){
////            LayoutMetrics2 lm = new LayoutMetrics2(nodeList.values(), edgeList.values());
//            //IDEAL AREA = MinArea
//            //p=0.5 in IV
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
//            double A = lm2.getAImprove();
//            double V = lm2.getVerticality();
//            System.out.println("A="+A+",V="+V);
//            double frnew;
//            double tKm;
//            double tKe;
//            double ttKm;
////            double oldKm = Km;
//            tKm = Km * Math.pow(100.0, V);//STEP 1
//            frnew = Ke/tKm;//STEP 2
//            tKe = Ke * Math.pow(100.0, A);//STEP 3
//            ttKm = tKe/frnew;//STEP 4
//
//            iKm = ttKm;
//            iKe = tKe;
//            System.out.println("iKm="+iKm+",iKe="+iKe);
////            iKm = Km * Math.pow(100.0, V);
////            iKe = Ke * Math.pow(100.0, A);
////            updateLayout("forceDirected", null,null);
////            Km = oldKm;//Revert to original value
////            lm.calculateAImprove();
////            lm.calculateVerticality();
////            return lm.getStringVerticality()+";"+lm.getStringAImprove()+";";
//        }
//
//public void improveBoth8(){
////            LayoutMetrics2 lm = new LayoutMetrics2(nodeList.values(), edgeList.values());
//            //IDEAL AREA = 7*MinArea
//            //p=0.5 in IV
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
//            double A = lm2.getAImprove();
//            double V = lm2.getVerticality();
//            System.out.println("A="+A+",V="+V);
//            double frnew;
//            double tKm;
//            double tKe;
//            double ttKm;
////            double oldKm = Km;
//            tKm = Km * Math.pow(100.0, V);//STEP 1
//            frnew = Ke/tKm;//STEP 2
//            tKe = Ke * Math.pow(100.0, A);//STEP 3
//            ttKm = tKe/frnew;//STEP 4
//
//            iKm = ttKm;
//            iKe = tKe;
//            System.out.println("iKm="+iKm+",iKe="+iKe);
////            iKm = Km * Math.pow(100.0, V);
////            iKe = Ke * Math.pow(100.0, A);
////            updateLayout("forceDirected", null,null);
////            Km = oldKm;//Revert to original value
////            lm.calculateAImprove();
////            lm.calculateVerticality();
////            return lm.getStringVerticality()+";"+lm.getStringAImprove()+";";
//        }
//public void improveBoth9(){
////            LayoutMetrics2 lm = new LayoutMetrics2(nodeList.values(), edgeList.values());
//            //IDEAL AREA = 4*MinArea
//            //p=0.5 in IV
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
//            double A = lm2.getAImprove();
//            double V = lm2.getVerticality();
//            System.out.println("A="+A+",V="+V);
//            double frnew;
//            double tKm;
//            double tKe;
//            double ttKm;
////            double oldKm = Km;
//            tKm = Km * Math.pow(100.0, V);//STEP 1
//            frnew = Ke/tKm;//STEP 2
//            tKe = Ke * Math.pow(100.0, A);//STEP 3
//            ttKm = tKe/frnew;//STEP 4
//
//            iKm = ttKm;
//            iKe = tKe;
//            System.out.println("iKm="+iKm+",iKe="+iKe);
////            iKm = Km * Math.pow(100.0, V);
////            iKe = Ke * Math.pow(100.0, A);
////            updateLayout("forceDirected", null,null);
////            Km = oldKm;//Revert to original value
////            lm.calculateAImprove();
////            lm.calculateVerticality();
////            return lm.getStringVerticality()+";"+lm.getStringAImprove()+";";
//        }
//public void improveBoth10(){
////            LayoutMetrics2 lm = new LayoutMetrics2(nodeList.values(), edgeList.values());
//            //IDEAL AREA = 4*MinArea
//            //p=0.5 in IV
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
//            double A = lm2.getAImprove();
//            double V = lm2.getVerticality();
//            System.out.println("A="+A+",V="+V);
//            double frnew;
//            double tKm;
//            double tKe;
//            double ttKm;
////            double oldKm = Km;
//            tKm = Km * Math.pow(50.0, V);//STEP 1
//            frnew = Ke/tKm;//STEP 2
//            tKe = Ke * Math.pow(100.0, A);//STEP 3
//            ttKm = tKe/frnew;//STEP 4
//
//            iKm = ttKm;
//            iKe = tKe;
//            System.out.println("iKm="+iKm+",iKe="+iKe);
////            iKm = Km * Math.pow(100.0, V);
////            iKe = Ke * Math.pow(100.0, A);
////            updateLayout("forceDirected", null,null);
////            Km = oldKm;//Revert to original value
////            lm.calculateAImprove();
////            lm.calculateVerticality();
////            return lm.getStringVerticality()+";"+lm.getStringAImprove()+";";
//        }
//public void improveBoth11(){
////            LayoutMetrics2 lm = new LayoutMetrics2(nodeList.values(), edgeList.values());
//            //IDEAL AREA = 4*MinArea
//            //p=0.5 in IV
//            lm2.calculateAImprove();
//            lm2.calculateVerticality();
//            double A = lm2.getAImprove();
//            double V = lm2.getVerticality();
//            System.out.println("A="+A+",V="+V);
//            double frnew;
//            double tKm;
//            double tKe;
//            double ttKm;
////            double oldKm = Km;
//            tKm = Km * Math.pow(50.0, V);//STEP 1
//            frnew = Ke/tKm;//STEP 2
//            tKe = Ke * Math.pow(150.0, A);//STEP 3
//            ttKm = tKe/frnew;//STEP 4
//
//            iKm = ttKm;
//            iKe = tKe;
//            System.out.println("iKm="+iKm+",iKe="+iKe);
////            iKm = Km * Math.pow(100.0, V);
////            iKe = Ke * Math.pow(100.0, A);
////            updateLayout("forceDirected", null,null);
////            Km = oldKm;//Revert to original value
////            lm.calculateAImprove();
////            lm.calculateVerticality();
////            return lm.getStringVerticality()+";"+lm.getStringAImprove()+";";
//        }
//public void improveBoth12(){
//        lm2.calculateVerticality();
//        lm2.calculateAImprove();
//            double V = lm2.getVerticality();
//            double A = lm2.getAImprove();
////            double oldKm = Km;
////            Km = Km * Math.pow(100.0, Math.max(1-V, 0));
//            iKm = Km * Math.pow(50, V);
//            iKe = Ke * Math.pow(100.0, A);//Remains Unchanged
//        }
//public void improveBoth13(){
//        lm2.calculateVerticality();
//        lm2.calculateAImprove();
//            double V = lm2.getVerticality();
//            double A = lm2.getAImprove();
////            double oldKm = Km;
////            Km = Km * Math.pow(100.0, Math.max(1-V, 0));
//            iKm = Km * Math.pow(100, V);
//            iKe = Ke * Math.pow(200.0, A);//Remains Unchanged
//        }
public void improveBoth14(){
//        lm2.calculateVerticality();
//        lm2.calculateAImprove();
            lm2.calculateMetrics();
            double VI = lm2.getVImprove();
            double AI = lm2.getAImprove();
//            double oldKm = Km;
//            Km = Km * Math.pow(100.0, Math.max(1-V, 0));
            iKm = Km * Math.pow(125, VI);
            iKe = Ke * Math.pow(200.0, AI);//Remains Unchanged
        }
//DATE 26/3/20010
public void improveBoth15(){

            lm2.calculateMetrics();
            double VI = lm2.getVImprove();
            double AI = lm2.getAImprove();

            iKm = Km * Math.pow(250, VI);
            iKe = Ke * Math.pow(400, AI);
        }
        
        //DATE 11/4/2010
public void improveBoth16(){
    lm2.calculateMetrics();
    double VI = lm2.getVImprove();
    double AI = lm2.getAImprove();

    double sKm = Km * Math.pow(100, VI)-Km;//start Km
    iKe = Ke * Math.pow(100, AI);
    double pKm = (iKe + 350000)/17000;
//    if(iKe - Ke > 0){
//        iKm = Math.max(sKm + pKm,50);
//    }else{
//        iKm = Math.max( pKm - sKm,50);
//    }
    iKm = sKm + pKm;
}

//DATE 20/5/2010
public void improveBoth17(){
    lm2.calculateMetrics();
    double VI = lm2.getVImprove();
    double AI = lm2.getAImprove();

    double DKm = Km * Math.pow(100, VI)-Km;//start Km
    double sRatio = (DKm + Km)/Ke;

    iKe = Ke * Math.pow(100, AI);
    double pKm = (iKe + 350000)/17000;
    double sDKm = (iKe*sRatio)/pKm;
//    if(iKe - Ke > 0){
//        iKm = Math.max(sKm + pKm,50);
//    }else{
//        iKm = Math.max( pKm - sKm,50);
//    }
    iKm = pKm + sDKm;
}

//DATE 20/5/2010
public void improveBoth18(){
    lm2.calculateMetrics();
    double VI = lm2.getVImprove();
    double AI = lm2.getAImprove();

    double DKm = Km * Math.pow(250, VI)-Km;//start Km
    double sRatio = (DKm + Km)/Ke;

    iKe = Ke * Math.pow(400, AI);
    double pKm = (iKe + 350000)/17000;
    double sDKm = (iKe*sRatio)/pKm;
//    if(iKe - Ke > 0){
//        iKm = Math.max(sKm + pKm,50);
//    }else{
//        iKm = Math.max( pKm - sKm,50);
//    }
    iKm = pKm + sDKm;
}

        public double getImprovedKm(){
            return iKm;
        }
        public double getImprovedKe(){
            return iKe;
        }
}

