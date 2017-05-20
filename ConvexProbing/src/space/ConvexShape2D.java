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
	
	
	public ConvexShape2D(int numOfVertices){
		/*TO DO: IMPLEMENT CHECK TO ensure numOFVertices can be constructed given MINIMUM_POINT_DISTANCE*/
		//Get Points on an ellipse and ensure that they are a sufficient distance away.
		
		selectPoints(numOfVertices); // initializes vertices
		makeEdgeSet(numOfVertices);  // initializes 
		
			
	}
	
	protected void selectPoints(int numOfVertices){
		// construct ellipse generation
				double xBound = 1 + rdm.nextDouble()*(MAX_BOUND-1);//Ensure that x width is at least the size of 1
				double yBound = 1 + rdm.nextDouble()*(MAX_BOUND-1);
				java.util.function.Supplier<double[]> ellipsePoint = () -> { // This is a lambda function
					double radians = Math.PI*2*rdm.nextDouble();
					return new double[]{xBound*Math.cos(radians), yBound*Math.sin(radians)};
				};
				
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
					newPoint = ellipsePoint.get();
					for(int i = 0; i < numOfConstructedVertices; i++){
						if(distanceBetweenPoints.apply(pointSet[i], newPoint) <= MINIMUM_POINT_DISTANCE){
							pointFailed = true;
							break;//Try a different point
						}
					}
					if(!pointFailed){
						pointSet[numOfConstructedVertices] = newPoint;
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
		
		/*Compares points where the point associated with pi = 0 is the lowest on the ellipse,
		 * and 2*pi-epsilon is the lowest 
		 * */
		java.util.Comparator<Point> counterClockwiseOrientation = (Point p1, Point p2) -> {
			/*int comparison = quadrant(p1) - quadrant(p2);
			if(comparison != 0){
				return comparison;
			}else*/ if(p1.equals(p2)){
				return 0;
				
			}else if(quadrant(p1) == 1 || quadrant(p1) == 2){
				return(p1.getCoordinateValue(0).doubleValue() > p2.getCoordinateValue(0).doubleValue())?(-1):(1);
			}else{ // quadrant 3 or 4
				return(p1.getCoordinateValue(0).doubleValue() > p2.getCoordinateValue(0).doubleValue())?(1):(-1);
			}
		};
		
		Point[][] quadrants = new Point[4][];
		for(int i = 0; i < 4; i++){
			quadrants[i] = quadrantsList[i].toArray(new Point[0]);
			Arrays.sort(quadrants[i], counterClockwiseOrientation);
		}
		
		Point[] orderedPoints = new Point[numOfVertices];
		int index = 0;
		for(int i = 0; i < 4; i++){
			for(Point value: quadrants[i]){
				orderedPoints[index++] =  value;
			}
		}
		
		
		
		
	}
	
	protected static int quadrant(Point point){
		if(point.getCoordinateValue(0).doubleValue() > 0){
			if(point.getCoordinateValue(1).doubleValue() > 0){
				return 1;
			}else{
				return 4;
			}
		}else{
			if(point.getCoordinateValue(1).doubleValue() > 0){
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

}
