package space;

public class HalfSpace2D {
	private Point direction;
	private Point pointOnLine;
	private Point pointInHalfSpace;
	
	public HalfSpace2D(Point dir, Point onLine, Point inSpace){
		if(!Point.haveSameDimension(dir, onLine, inSpace)){
			throw new IllegalArgumentException("Half spaces must be defined by points within the same dimension");
		}
		direction = dir;
		pointOnLine = onLine;
		pointInHalfSpace = inSpace;
	}
	
	/*Returns true if the point is in the halfspace */
	public boolean contains(Point p){
		testDimension(p);
		//line between  p and pointInHalfSpace , say q, is: (q-p)*t + p
	}
	
	private void testDimension(Point p){
		if(p.getDimension() != direction.getDimension()){
			throw new IllegalArgumentException("p can only be in the halfspace if it has the same dimension of the halfspace");
		}
	}
}
