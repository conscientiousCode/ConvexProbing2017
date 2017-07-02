package space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class ConvexPolytope2D implements ConvexShape{
	private static final Random rdm = new Random();
	private static final int MAX_BOUND = 200;
	private static final double MINIMUM_POINT_DISTANCE = 0.0000001;
	
	private ArrayList<Point> vertices;
	private EdgeSet edges;
	
	
	@Override
	public Iterator<RealPoint[]> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumVertices() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasVertex(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasEdgeBetween(Point p1, Point p2) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static ConvexPolytope2D randomPolytope(int numOfVertices){
		if(numOfVertices <= 0){
			throw new IllegalArgumentException("When constructing a convex polytope at least one vertice is required.");
		}
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
	}
	
	private class RandomEllipse{
		private double xBound;
		private double yBound;
		
		public RandomEllipse(){
			//Set Max width and height
			xBound = 1 + rdm.nextDouble()*(MAX_BOUND-1);//Ensure that x width is at least the size of 1
			yBound = 1 + rdm.nextDouble()*(MAX_BOUND-1);
		}
		
		public double[] getRandomPoint(){
			double radians = Math.PI*2*rdm.nextDouble();
			return new double[]{xBound*Math.cos(radians), yBound*Math.sin(radians)};
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
