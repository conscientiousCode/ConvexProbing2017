package space;
import java.util.ArrayList;

//n^2 algorithm for 2D hulls
class ConvexHull2D {
	private static final double INEQUALITY_THRESHOLD = 0.00000000000001;
	
	/*This Algorithm is n^2 unfortunately*/

	/**
	 * @param pointSet the set of 2D points for which the minimum convex set is to be returned 
	 * @return the minimum convex hull represented as an EdgeSet
	 * 
	 * Note that there is no set of edges for a single point, so an empty EdgeSet will be returned.
	 * If pointSet is empty or null, then null is returned.
	 */
	public static EdgeSet getHull(ArrayList<Point> pointSet){
		if(pointSet == null || pointSet.size() == 0){
			return null;
		}
		pointSet = removeDuplicatePoints(pointSet);
		if(pointSet.size() == 1){
			return new EdgeSet(pointSet.get(0).getDimension());
		}
		for(Point p : pointSet){
			if(p.getDimension() != 2){
				throw new IllegalArgumentException("Not all points in 'pointSet' are 2 dimensional");
			}
		}
		pointSet = new ArrayList(pointSet);//Prevent mutation of the origonal ArrayList;
		ArrayList<Point> extremeSet = getMaximalXSet(pointSet);
		Point endVertex = getPointWithMaxY(extremeSet);
		Point currentVertex = getPointWithMinY(extremeSet);
		EdgeSet hull = new EdgeSet(2);
		Point direction = new Point(0,-1);//<0,-1>, initialized to this, will hold the direction of the line connecting the last pair of points that are part of the convex hull;
		
		pointSet.removeAll(extremeSet);
		pointSet.add(endVertex);
		if(!endVertex.equals(currentVertex)){//Algorithm below requires current edge to be removed from pointSet, but then we cannot find this edge.
			hull.addEdge(endVertex, currentVertex);
		}
		
		/*The below loop will measure how closely the direction between two points aligns with 'direction'
		 * the point v that creates a direction unit vector between currentPoint to v with the largest alignment
		 * must be the point farthest out (because it is the closest to creating a straight line) */
		int iteration = 0;
		Point furthestAlignedPoint;
		do{
			ArrayList<Point> mostAlignedSet = getMostAlignedSet(pointSet, currentVertex, direction);
			furthestAlignedPoint = getVertexFarthestFrom(mostAlignedSet, currentVertex);
			hull.addEdge(currentVertex, furthestAlignedPoint);
			//Set up for next Iteration
			direction = furthestAlignedPoint.subtract(currentVertex).getUnitVector();
			pointSet.removeAll(mostAlignedSet);
			currentVertex = furthestAlignedPoint;
		}while(!currentVertex.equals(endVertex));
		
		return hull;
	}
	
	static ArrayList<Point> removeDuplicatePoints(ArrayList<Point> pointSet){
		ArrayList<Point> sanitizedSet = new ArrayList();
		for(Point p : pointSet){
			if(!sanitizedSet.contains(p)){
				sanitizedSet.add(p);
			}
		}
		return sanitizedSet;
	}
	
	static ArrayList<Point> getMaximalXSet(ArrayList<Point> pointSet){
		ArrayList<Point> biggestXSet = new ArrayList();
		biggestXSet.add(pointSet.get(0));
		for(int i = 1; i < pointSet.size(); i++){
			if(pointSet.get(i).getAxisValue(0) == biggestXSet.get(0).getAxisValue(0)){
				biggestXSet.add(pointSet.get(i));
			}else if(pointSet.get(i).getAxisValue(0) > biggestXSet.get(0).getAxisValue(0)){
				biggestXSet = new ArrayList();
				biggestXSet.add(pointSet.get(i));
			}
			
		}
		return biggestXSet;
	}
	
	static Point getPointWithMaxY(ArrayList<Point> pointSet){
		Point maxY = pointSet.get(0);
		for(int i = 1; i < pointSet.size(); i++){
			if(maxY.getAxisValue(1) < pointSet.get(i).getAxisValue(1)){
				maxY = pointSet.get(i);
			}
		}
		return maxY;
	}
	
	static Point getPointWithMinY(ArrayList<Point> pointSet){
		Point minY = pointSet.get(0);
		for(int i = 1; i < pointSet.size(); i++){
			if(minY.getAxisValue(1) > pointSet.get(i).getAxisValue(1)){
				minY = pointSet.get(i);
			}
		}
		return minY;
	}
	
	static Point getUnitVectorFrom(Point a, Point b){
		return (b.subtract(a)).getUnitVector();
	}
	
	/*The subset of the lines between points from 'pointSet' and 'currentVertex' that is most parallel to 'direction'*/
	static ArrayList<Point> getMostAlignedSet(ArrayList<Point> pointSet, Point currentVertex, Point direction){
		if(pointSet == null || pointSet.isEmpty()){
			throw new IllegalArgumentException("Set passed in has no elements (Empty or Null)");
		}
		//System.out.println("currentVertex  = " + currentVertex + " direction = " + direction);
		ArrayList<Point> mostAlignedSet = new ArrayList();
		mostAlignedSet.add(pointSet.get(0));
		double maxAlignment = getUnitVectorFrom(currentVertex, pointSet.get(0)).dot(direction);
		double alignment;
		Point unitVectorBetween;
		//System.out.println("Init Point: " + mostAlignedSet.get(0) + " INIT ALIGNMENT: " + maxAlignment);
		for(int i = 1; i < pointSet.size(); i++){
			try{
				alignment = getUnitVectorFrom(currentVertex, pointSet.get(i)).dot(direction);
			}catch(ArithmeticException e){
				continue;
			}
		//	System.out.println("Point: " + pointSet.get(i) + " Alignment: " + alignment);
			if(Math.abs(maxAlignment - alignment) < INEQUALITY_THRESHOLD){//Considered Equal
			//	System.out.println("\t Considered equal");
				mostAlignedSet.add(pointSet.get(i));
			}else if(alignment - maxAlignment > 0){//Guaranteed not equal by previous if
			//	System.out.println("\t Considered more aligned");
				mostAlignedSet = new ArrayList();
				mostAlignedSet.add(pointSet.get(i));
				maxAlignment = alignment;
			}
		}
	//	System.out.println("Set Selected: " + mostAlignedSet + " Alignment: " + maxAlignment + "\n--------");
		return mostAlignedSet;
	}
	
	
	static Point getVertexFarthestFrom(ArrayList<Point> pointSet, Point anchorPoint){
		Point furthestPoint = pointSet.get(0);
		double largestDistance = (furthestPoint.subtract(anchorPoint)).getMagnitude();
		double currentDistance;
		for(int i = 1; i < pointSet.size(); i++){
			currentDistance = pointSet.get(i).subtract(anchorPoint).getMagnitude();
			if(largestDistance < currentDistance){
				furthestPoint = pointSet.get(i);
				largestDistance = currentDistance;
			}
		}
		return furthestPoint;
	}
	
}
