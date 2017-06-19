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
	}
	
	private void testDimension(Point p){
		if(p.getDimension() != 2){
			throw new IllegalArgumentException("p can only be in the halfspace if it has the same dimension of the halfspace");
		}
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
			double[][] A = {{this.direction.getAxisValue(0).doubleValue(), -1*otherLine.direction.getAxisValue(0).doubleValue()},
					       {this.direction.getAxisValue(1).doubleValue(), -1*otherLine.direction.getAxisValue(0).doubleValue()}};
			
			double determinant = A[0][0]*A[1][1] - A[0][1]*A[1][0];
			if(determinant < SINGULARITY_THRESHOLD){//This is bad due to double inaccuracies
				return new Double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
			}else{
				for(int i = 0; i < 2; i++){
					for(int j = 0; j < 2; j++){
						A[i][j] = (1/determinant)*A[i][j];
					}
				}
				double x = otherLine.pointOnLine.getAxisValue(0).doubleValue() - this.pointOnLine.getAxisValue(0).doubleValue();
				double y = otherLine.pointOnLine.getAxisValue(1).doubleValue() - this.pointOnLine.getAxisValue(1).doubleValue();
				
				return new Double[]{x*A[0][0] + y*A[0][1], x*A[1][0] + y*A[1][1]};
			}
		}
		
	}
	
	public static void main(String[] args){
		
	}
}
