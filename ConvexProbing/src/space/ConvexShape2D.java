package space;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import org.apache.commons.math3.fraction.*;
import java.util.Arrays;
import java.util.Comparator;


public class ConvexShape2D implements ConvexShape{
	
	private static final Random rdm = new Random();
	private static final int MAX_BOUND = 200;
	private static final double MINIMUM_POINT_DISTANCE = 0.0000001;
	
	
	private Set<Point> vertices;
	private EdgeSet edges;
	private RandomEllipse ellipse;
	
	
	public ConvexShape2D(int numOfVertices){
		/*TO DO: IMPLEMENT CHECK TO ensure numOfVertices can be constructed given MINIMUM_POINT_DISTANCE*/
		//Get Points on an ellipse and ensure that they are a sufficient distance away.
		
		ellipse = new RandomEllipse(); // Create a random ellipse to sample points from
		
		selectPoints(numOfVertices); // initializes vertices
		makeEdgeSet(numOfVertices);  // initializes
	}
	
	protected void selectPoints(int numOfVertices){
		// construct points and check if they are far enough apart
				java.util.function.BiFunction<double[], double[], Double> distanceBetweenPoints = (double[] p1, double[] p2) -> {
					return (Math.sqrt(Math.pow(p1[0]-p2[0],2)+ Math.pow(p1[1]-p2[1], 2)));
				};
				
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
				vertices = new HashSet<Point>(numOfVertices);
				for(double[] point: pointSet){
					vertices.add(new Point(point));
				}
	}
	
	protected void makeEdgeSet(int numOfVertices){
				
		java.util.ArrayList<Point>[] quadrantsList = new java.util.ArrayList[4];
		for(int i = 0; i < 4; i++){
			quadrantsList[i] = new java.util.ArrayList<Point>();
		}
		
		for(Point point: vertices){
			quadrantsList[quadrant(point)-1].add(point);
		}
		
		java.util.Comparator<Point> pointOrderer = (Point p1, Point p2) -> {
			 return pointOrder(p1,p2);
		};
		
		Point[][] quadrants = new Point[4][];
		for(int i = 0; i < 4; i++){
			quadrants[i] = quadrantsList[i].toArray(new Point[0]);
			Arrays.sort(quadrants[i], pointOrderer);
		}
		
		Point[] orderedPoints = new Point[numOfVertices];
		int index = 0;
		for(int i = 0; i < 4; i++){
			for(Point value: quadrants[i]){
				orderedPoints[index++] =  value;
			}
		}
		
		if(orderedPoints.length >= 2){
			edges.addEdge(orderedPoints[0], orderedPoints[orderedPoints.length-1]);
		}
		for(int i = 1;i < orderedPoints.length; i++){
			edges.addEdge(orderedPoints[i-1], orderedPoints[i]);
		}
		
	}
	
	/*
	 * note that the edge case (0,0) never occurs because the radius of the random ellipse is > 0.
	 * returns the quadrant that Point point exists in, if the point falls on an axis line, it goes to the quadrant that is just previous to the line
	 * */
	protected static int quadrant(Point point){
		if(point.getAxisValue(0).doubleValue() == 0){ // edge case of (0,y)
			return (point.getAxisValue(1).doubleValue() > 0)?(1):(3); // If y values is greater than zero, 1, otherwise 3
		}else if(point.getAxisValue(1).doubleValue() == 0){//edge case of (0,x)
			/* For consistency, (x,0) is considered to be in quadrant 4 as it is the previous quadrant assuming we arrived at
			 * this point after some number of counter-clockwise revolutions*/
			return (point.getAxisValue(0).doubleValue() > 0)?(4):(2);
		}else if(point.getAxisValue(0).doubleValue() > 0){//none-edge case
			if(point.getAxisValue(1).doubleValue() > 0){
				return 1;
			}else{
				return 4;
			}
		}else{
			if(point.getAxisValue(1).doubleValue() > 0){
				return 2;
			}else{
				return 3;
			}
		}
	}

	@Override
	public Iterator<Point> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumVertices() {
		return vertices.size();
	}

	@Override
	public boolean hasVertex(Point point) {
		for(Point p: vertices){
			if(point.equals(p)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasEdgeBetween(Point p1, Point p2) {
		return edges.containsEdge(p1, p2);
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
					return (p1.getAxisValue(0).doubleValue() > p2.getAxisValue(0).doubleValue()) ? (-1) : (1);
				} else {
					return (p1.getAxisValue(0).doubleValue() < p2.getAxisValue(0).doubleValue()) ? (-1) : (1);
				}
			}
		} else {
			return quadrant(p1) - quadrant(p2);
		}

	}

}
