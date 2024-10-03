package graphs;

import java.awt.geom.Point2D;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

/**
 * GraphEdge extends the custom graphic's library edge element and implements the GraphElementInterface in order to provide portability 
 * @author leonidis
 * 
 */
public class GraphEdge extends DefaultEdge implements GraphElementInterface{

        private VisualGraph visGraph = null;
	/**
	 * Create a new graphical edge
	 * @param source graphical source node
	 * @param target graphical target node
	 * @param label edge's label
	 * @param nedges total edges connecting source and target nodes
	 */
	public GraphEdge(DefaultGraphCell source, DefaultGraphCell target, EdgeLabel label, int nedges,VisualGraph visG){
		super(label);
                this.visGraph = visG;
		GraphConstants.setLabelAlongEdge(this.getAttributes(), true);
                //The name of the edge must not change
                GraphConstants.setEditable(this.getAttributes(), false);
                GraphConstants.setSelectable(this.getAttributes(), false);
                
		this.setSource(source.getChildAt(0));
		this.setTarget(target.getChildAt(0));
                
//		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		//Insert extra points to direct edges and avoid overlap
//		if(nedges!=0 && nedges%2 == 1){
//			int x1 = ((Rectangle)GraphConstants.getBounds(source.getAttributes()).getBounds()).x;
//			int x2 = ((Rectangle)GraphConstants.getBounds(target.getAttributes()).getBounds()).x;
//			int y1 = ((Rectangle)GraphConstants.getBounds(source.getAttributes()).getBounds()).y;
//			int y2 = ((Rectangle)GraphConstants.getBounds(target.getAttributes()).getBounds()).y;
//			
//			int Mx,My;
//	
//			Mx=(x1+x2)/2;
//			My=(y1+y2)/2;
//			
//			double L = (double)(y2-y1)/(double)(x2-x1);
//			
//			double new_x1=0 ,new_y1=0;
//			
//			int A_M_tetr = (x1-Mx)*(x1-Mx) + (y1-My)*(y1-My);
//			System.out.println("A_M: " +A_M_tetr);
//	
//			int d = (nedges+1)/2;
//			int dtetr= A_M_tetr + d*d;
//			double myL = -1/L;
//			
//			double a = myL*myL + 1;
//			double b = 2*myL*(My - myL*Mx - y1) - 2*x1;
//			double c = x1*x1 +myL*Mx*(myL*Mx - 2*My + 2*y1) + My*My - 2*My*y1 + y1*y1 - dtetr;
//	
//			long D =(long)(b*b - 4*a*c);
//			double rizaD = java.lang.Math.sqrt(D);
//			double paranom = 2.0*a;
//			double arithm = (-1)*b+rizaD;
//			new_x1 = java.lang.Math.round(arithm / paranom);
//			new_y1 = java.lang.Math.round((myL*(new_x1 - Mx) + My));
//			
//			ArrayList<Point2D> list = new ArrayList<Point2D>();
//			Point2D p1 = new Point2D.Double(0, 0);
//			Point2D p2 = new Point2D.Double(new_x1,new_y1);
//			Point2D p3 = new Point2D.Double(0, 0);
//			list.add(p1);
//			list.add(p2);
//			list.add(p3);
//			GraphConstants.setPoints(this.getAttributes(), list);
//	
//	//		System.out.println(x1 +","+ y1 +","+ Mx +","+My);		
//	//		System.out.println("myL: " +myL);
//	//		System.out.println("dtetr: " +dtetr);
//	//		System.out.println("a: " +a);
//	//		System.out.println("b: " +b);
//	//		System.out.println("c: " +c);
//	//		System.out.println("D: " +D);
//	//		System.out.println("rizaD = " + rizaD);		
//	//		System.out.println("Paranom = " + paranom);
//	//		System.out.println("Arithm = " + arithm);
//	//		System.out.println("Res = " + new_x1);
//			
//				
//		
//		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		}//end if => n%2 == 1
	}//end GraphEdge
	
	//TODO change setArrowType* to one single function 
	
	/**
	 * Set Arrow Type as simple (used for properties)
	 */
	public void setArrowTypeSimple(){
		int arrow = GraphConstants.ARROW_SIMPLE;
		GraphConstants.setLineEnd(this.getAttributes(), arrow);
		GraphConstants.setEndFill(this.getAttributes(), false);
	}//end setArrowTypeSimple

    /**
     * Set Arrow Type as none (used for not-directed graphs)
     */
    public void setArrowTypeNone(){
        int arrow = GraphConstants.ARROW_NONE;
        GraphConstants.setLineEnd(this.getAttributes(), arrow);
		GraphConstants.setEndFill(this.getAttributes(), false);
    }

	/**
	 * Set Arrow Type as technical (used for subclasses)
	 */
	public void setArrowTypeTechnical(){
		int arrow = GraphConstants.ARROW_TECHNICAL;
		GraphConstants.setLineEnd(this.getAttributes(), arrow);
		GraphConstants.setEndFill(this.getAttributes(), false);
		GraphConstants.setLineWidth(this.getAttributes(),2);
	}//end setArrowTypeTechnical
	
        public void setArrowTypeTechnicalDashed(){
                float offset[] = {10,2,2,2};
                
                int arrow = GraphConstants.ARROW_TECHNICAL;
                GraphConstants.setLineEnd(this.getAttributes(), arrow);
		GraphConstants.setEndFill(this.getAttributes(), false);
		GraphConstants.setLineWidth(this.getAttributes(),2);
                GraphConstants.setDashPattern(this.getAttributes(),offset);
        }
	/**
	 * Set label's position according to the number of edges that connect two specific nodes
	 * Number of edges is the one that specify the point where the label should be placed
	 * If there are many edges between two nodes then they will overlap
	 * To save space, let the edges overlap and eventually display a single line but place labels in position above and under the midpoint
	 * of the edge to make them clearly visible 
	 * @param nedges : total edges between two nodes
	 */
	public void setLabelPosition(int nedges){
		Point2D p;
		
		if(this.source == this.target){
			p = new Point2D.Double(GraphConstants.PERMILLE/2,(nedges*(-15))-20);
			GraphConstants.setLabelPosition(this.getAttributes(), p);
			return;
		}
		
		if(nedges%2==1)
			p = new Point2D.Double(GraphConstants.PERMILLE/2,((nedges+1)/2)*(-10));
		else
			//10 is added because the default label's position is on the edge, so in order to be placed lower and
			//leave some free space should be placed 10 point below line and then place it accordingly to nedges
			p = new Point2D.Double(GraphConstants.PERMILLE/2,((nedges+2)/2)*10+10);
		
		GraphConstants.setLabelPosition(this.getAttributes(), p);
	}//end setLabelPosition
	
	/**
	 * Not used in graph edges. GraphElementInterface method
	 */
	public int getXposition() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Not used in graph edges. GraphElementInterface method
	 */
	public int getYposition() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Not used in graph edges. GraphElementInterface method
	 */
	public void setXposition(int x) {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used in graph edges. GraphElementInterface method
	 */
	public void setYposition(int y) {
		// TODO Auto-generated method stub
	}

	/**
	 * Set Nailed status fot this edge
	 * @param nailedStatus
	 */
	public void setNailed(boolean nailedStatus) {
//		GraphConstants.setMoveable(this.getAttributes(),false);
	}

	/**
	 * Not used in graph edges. GraphElementInterface method
	 */
	public void setVisible(boolean visibility) {
		// TODO Auto-generated method stub
            visGraph.getGraphLayoutCache().setVisible(this, visibility);
	}
}//end class GraphEdge