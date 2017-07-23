package space;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Arrays;

//nlogn 2D hull finder
public class GrahamScan {
	
	/*Highest alignment is considered lowest for Arrays.sort(), to make the highest alignment the first index.*/
	private static final Comparator<PointAlignment> compareAlignment = (PointAlignment a1, PointAlignment a2 ) ->{
		double alignmentDifference = a1.alignment - a2.alignment;
		if(alignmentDifference < 0){
			return 1;
		}else if(alignmentDifference == 0){
			return 0;
		}else{
			return -1;
		}
	};

	public static EdgeSet getHull(ArrayList<Point> pointSet){
		if(pointSet == null){return null;}
		if(pointSet.size() < 2){return new EdgeSet(2);}
		while(pointSet.remove(null)){};//remove all null references if there are any
		
		//Get bottom left most point
		Point anchor = getBottomLeftPoint(pointSet);		
		//Sanitize data and Prepare it for sorting
		PointAlignment[] pointAlignment = getSetOfPointsAndAlignment(pointSet, anchor);
		//sort points in nlogn
		Arrays.sort(pointAlignment, compareAlignment);//Organize the line anchor P in descending order of alignment (with the x axis)

		//implement the rest of Grahams algorithm
		ArrayList<Point> vertices = new ArrayList<>();
		//Initialize the first two points
		vertices.add(anchor);
		vertices.add(pointAlignment[0].point);
		
		double turnDirection;
		Point a,b,c;
		for(int i = 1; i < pointAlignment.length; i++){
			a = vertices.get(vertices.size()-2);
			b = vertices.get(vertices.size()-1);
			c = pointAlignment[i].point;
			
			//DEBUG::System.out.println("a: " + a + "\tb: " + b + "\tc: " + c);
			turnDirection = getTurnDirection(a,b,c);
			//DEBUG::System.out.println(turnDirection);
			if(turnDirection > 0){//Counter clockwise
				vertices.add(c);
			}else if(turnDirection == 0){
				if(b.subtract(a).getMagnitude() < c.subtract(a).getMagnitude()){//if c is further from 'a' than b
					vertices.remove(vertices.size()-1);
					vertices.add(c);
				}
			}else{//clockwise
				while(getTurnDirection(a,b,c) < 0){/*Honestly it is best to just draw it out, but basically
				*if the current state is illegal, then we need to remove the point that is certainly causing the illegal state.
				*/
					//DEBUG::System.out.println("running loop");
					vertices.remove(vertices.size()-1);
					a = vertices.get(vertices.size()-2);
					b = vertices.get(vertices.size()-1);
					//DEBUG::System.out.println("a: " + a + "\tb: " + b);
				}
				vertices.add(c);
			}
		}
		
		//vertices.add(anchor);//anchor is now at both ends
		EdgeSet edges = new EdgeSet(2);
		for(int i = 1; i < vertices.size(); i++){
			edges.addEdge(vertices.get(i-1), vertices.get(i));
		}
		
		
		return edges;
	}
	
	protected static Point getBottomLeftPoint(ArrayList<Point> pointSet){
		Point bottomPoint = pointSet.get(0);
		for(int i = 1; i < pointSet.size(); i++){//for each point
			if(bottomPoint.getAxisValue(1) >= pointSet.get(i).getAxisValue(1)){//if the current bottomPoints y value is more than or equal to the prospective y value
				if(bottomPoint.getAxisValue(1) == pointSet.get(i).getAxisValue(1) && bottomPoint.getAxisValue(0) > pointSet.get(i).getAxisValue(0)){
					//If y values are equal and the prospective point has a lower x value, update the point
					bottomPoint = pointSet.get(i);
				}else{
					bottomPoint = pointSet.get(i);
				}
			}
		}
		return bottomPoint;
	}
	
	/*Removes duplicate points and returns an array of PointAlignment objects that have been initialized with the alignemnt between the anchor and this point, and e1*/
	protected static PointAlignment[] getSetOfPointsAndAlignment(ArrayList<Point> pointSet, Point anchor){
		HashSet<PointAlignment> set = new HashSet(pointSet.size());
		Point xAxis = new Point(1,0);
		PointAlignment current;
		for(Point p : pointSet){
			if(p.equals(anchor)){continue;}
			current = new PointAlignment(p, (p.subtract(anchor)).getUnitVector().dot(xAxis));
			if(!set.contains(current)){
				set.add(current);
			}
		}
		set.add(new PointAlignment(anchor,-1));
		return set.toArray(new PointAlignment[0]);
	}
	
	/*Gotten from the wikipedia page on the GrahamScan
	 * p1,p2,p3 specifies a directed triangle
	 * returns > 0, if triangle is counter clockwise
	 * returns == 0, if triangle is degenerate (a line)
	 * returns < 0, if the triangle is clockwise
	*/
	private static double getTurnDirection(Point p1, Point p2, Point p3){
		return (p2.getAxisValue(0) - p1.getAxisValue(0))*(p3.getAxisValue(1) - p1.getAxisValue(1)) - (p2.getAxisValue(1) - p1.getAxisValue(1))*(p3.getAxisValue(0)- p1.getAxisValue(0));
	}
	
	
	/*Structure used for convenient sorting of data with the relevant index so that recalculation is kept to a minimum*/
	public static class PointAlignment{
		public Point point;
		public double alignment;
		
		public PointAlignment(Point p, double a){
			point = p;
			alignment = a;
		}
		
		public boolean equals(Object obj){
			if(obj instanceof PointAlignment){
				return point.equals(((PointAlignment)obj).point);
			}else{
				return false;
			}
		}
		
		public boolean equals(PointAlignment otherPoint){
			return point.equals(otherPoint.point);
		}
	}

}
