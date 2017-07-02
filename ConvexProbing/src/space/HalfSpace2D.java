package space;
import org.apache.commons.math3.linear.*;

public class HalfSpace2D {
	
	private Line division;
	private Point pointInHalfSpace;
	
	public HalfSpace2D(Point dir, Point onLine, Point inSpace){
		if(!Point.haveSameDimension(dir, onLine, inSpace, new Point(new double[]{1,1}))){
			throw new IllegalArgumentException("Half spaces must be defined by points within the same dimension");
		}
		division = new Line(dir, onLine);
		pointInHalfSpace = inSpace;
	}
	
	/*Returns true if the point is in the halfspace */
	public boolean contains(Point p){
		testDimension(p);
		//line between  p and pointInHalfSpace , say q, is: (q-p)*t + p, for t in [0,1]
		//line between 
		return false;
	}
	
	private void testDimension(Point p){
		if(p.getDimension() != 2){
			throw new IllegalArgumentException("p can only be in the halfspace if it has the same dimension of the halfspace");
		}
	}
	
	public Point getIntersection(HalfSpace2D otherHalf){
		Double[] intersection = division.pointOfIntersection(otherHalf.division);
		double[] dIntersection = new double[intersection.length];
		if(intersection != null && intersection[0] != Double.POSITIVE_INFINITY){
			for(int i = 0; i < intersection.length; i++){
				dIntersection[i] = intersection[i];
			}
		}
		return new Point(dIntersection);
	}
	
	class Line{
		public final static double SINGULARITY_THRESHOLD = 0.000000000001;
		public Point direction;
		public Point pointOnLine;
		
		public Line(Point direction, Point onLine){
			this.direction = direction;
			this.pointOnLine = onLine;
		}
		
		public Double[] pointOfIntersection(Line otherLine){
			double[][] A = {{this.direction.getAxisValue(0), -1*otherLine.direction.getAxisValue(0)},
					       {this.direction.getAxisValue(1), -1*otherLine.direction.getAxisValue(0)}};
			
			double determinant = A[0][0]*A[1][1] - A[0][1]*A[1][0];
			if(determinant < SINGULARITY_THRESHOLD){//This is bad due to double inaccuracies
				return new Double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
			}else{
				for(int i = 0; i < 2; i++){
					for(int j = 0; j < 2; j++){
						A[i][j] = (1/determinant)*A[i][j];
					}
				}
				double x = otherLine.pointOnLine.getAxisValue(0) - this.pointOnLine.getAxisValue(0);
				double y = otherLine.pointOnLine.getAxisValue(1) - this.pointOnLine.getAxisValue(1);
				
				return new Double[]{x*A[0][0] + y*A[0][1], x*A[1][0] + y*A[1][1]};
			}
		}
		
	}
	
	public static void main(String[] args){
		Point dir = new Point(1,1), onLine = new Point(1,0), inSpace = new Point(0,0); 
		HalfSpace2D h1 = new HalfSpace2D(dir, onLine, inSpace);
		HalfSpace2D h2 = new HalfSpace2D(new Point(-1,-1),new Point(1,0), new Point(0,0));
		System.out.println(h1.getIntersection(h2));
	}
}
