package space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class ConvexPolytope2D implements ConvexShape{
	
	private static final double MINIMUM_POINT_DISTANCE = 0.0000001;
	
	private ArrayList<Point> vertices;
	private EdgeSet edges;
	private boolean isHullCurrent;
	
	public ConvexPolytope2D(){
		
	}
	
	//Create an uninitialized object
	protected ConvexPolytope2D(ArrayList<Point> vertices){
		this.vertices = vertices;
		makeEdgeSet(vertices.size());
		isHullCurrent = true;
	}
	
	/*Constructs the edge set of a ramdomly generated and ordered vertex set*/ 
	protected void makeEdgeSet(int numOfVertices){
		
		edges = new EdgeSet(2);
		
		if(vertices.size() >= 2){
			edges.addEdge( vertices.get(0), vertices.get(vertices.size()-1));
		}
		for(int i = 1;i < vertices.size(); i++){
			edges.addEdge(vertices.get(i-1), vertices.get(i));
		}
		
	}
	
	public static ConvexPolytope2D newRandomPolytope(int numOfVertices){
		if(numOfVertices <= 0){
			throw new IllegalArgumentException("When constructing a convex polytope at least one vertice is required.");
		}
		return new ConvexPolytope2D(getRandomVertices(numOfVertices));
	}
	
	/**
	 * Get a list of points that when ordered counter-clockwise by coordinate and connected to their two neighbor's, will form a convex polytope.
	 * @param numOfVertices How many vertices the convex polytope should have
	 * @return
	 */
	private static ArrayList<Point> getRandomVertices(int numOfVertices){
		ArrayList<Point> vertices;
		RandomEllipse ellipse = new RandomEllipse();
		
		java.util.function.BiFunction<double[], double[], Double> distanceBetweenPoints = (double[] p1, double[] p2) -> {
			return (Math.sqrt(Math.pow(p1[0]-p2[0],2)+ Math.pow(p1[1]-p2[1], 2)));
		};
		/*Get the points*/
		int numOfConstructedVertices = 0;
		double[][] pointSet = new double[numOfVertices][2];
		double[] newPoint;
		boolean pointFailed = false;
		/*Possibly Non-terminating by chance...*/
		while(numOfConstructedVertices != numOfVertices){
			newPoint = ellipse.getRandomPoint();
			for(int i = 0; i < numOfConstructedVertices; i++){
				if(distanceBetweenPoints.apply(pointSet[i], newPoint) <= MINIMUM_POINT_DISTANCE){
					pointFailed = true;
					break;//Try a different point
				}
			}
			if(!pointFailed){
				pointSet[numOfConstructedVertices++] = newPoint;
			}
			pointFailed = false;
		}
		
		Point[] orderedPointSet = orderPointSet(pointSet);
		vertices = new ArrayList<Point>(orderedPointSet.length);
		for(Point point : orderedPointSet){
			vertices.add(point);
		}
		
		return vertices;
	}
	

	/*Returns an iterator of RealPoints[2] over the edge set*/
	@Override
	public Iterator<RealPoint[]> iterator() {
		return edges.iterator();
	}

	@Override
	public int getNumVertices() {
		return vertices.size();
	}

	@Override
	public boolean hasVertex(Point point) {
		return vertices.contains(point);
	}

	@Override
	public boolean hasEdgeBetween(Point p1, Point p2) {
		updateHull();
		return edges.containsEdge(p1, p2);
	}

	@Override
	public boolean addVertex(Point point) {
		if(vertices.contains(point)){
			return false;
		}else{
			vertices.add(point);
			isHullCurrent = false;
			return true;
		}
		
		
	}

	@Override
	public boolean removeVertex(Point point) {
		if(vertices.remove(point)){
			isHullCurrent = false;
			return true;
		}
		return false;
	}
	
	private void updateHull(){
		if(!isHullCurrent){
			edges = ConvexHull2D.getHull(vertices);
			isHullCurrent = true;
		}
	}
	
	protected static int quadrant(Point point){
		if(point.getAxisValue(0) == 0){ // edge case of (0,y)
			return (point.getAxisValue(1) > 0)?(1):(3); // If y values is greater than zero, 1, otherwise 3
		}else if(point.getAxisValue(1) == 0){//edge case of (0,x)
			/* For consistency, (x,0) is considered to be in quadrant 4 as it is the previous quadrant assuming we arrived at
			 * this point after some number of counter-clockwise revolutions*/
			return (point.getAxisValue(0) > 0)?(4):(2);
		}else if(point.getAxisValue(0) > 0){//none-edge case
			if(point.getAxisValue(1) > 0){
				return 1;
			}else{
				return 4;
			}
		}else{
			if(point.getAxisValue(1) > 0){
				return 2;
			}else{
				return 3;
			}
		}
	}
	
	/*
	 * One point is greater than another if its value pair occurs later given f: t ->R^2 : f(t) = [cos[t], sin[t], t = [0, 2PI]
	 * */
	protected static int pointOrder(Point p1, Point p2) {
		if (quadrant(p1) == quadrant(p2)) {
			if (p1.equals(p2)) {
				return 0;
			} else {
				if (quadrant(p1) == 1 || quadrant(p1) == 2) {
					return (p1.getAxisValue(0) > p2.getAxisValue(0)) ? (-1) : (1);
				} else {
					return (p1.getAxisValue(0) < p2.getAxisValue(0)) ? (-1) : (1);
				}
			}
		} else {
			return quadrant(p1) - quadrant(p2);
		}

	}

	/*The point set is a 2 dimensional array where the first dimension represents the number of points, and the second dimension is constant and represents the dimension of the
	 * points in space*/
	protected static Point[] orderPointSet(double[][] pointSet){
		java.util.Comparator<Point> pointOrderer = (Point p1, Point p2) -> {
			 return pointOrder(p1,p2);
		};
		
		Point[] orderedPoints = new Point[pointSet.length];
		for(int i = 0; i < pointSet.length; i++){
			orderedPoints[i] = new Point(pointSet[i]);
		}
			
		Arrays.sort(orderedPoints, pointOrderer);
		return orderedPoints;
		
	}

}
