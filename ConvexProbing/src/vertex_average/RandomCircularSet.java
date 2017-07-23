package vertex_average;

import java.util.ArrayList;
import java.util.Random;
import space.Point;

/*FOR THE CURRENT PURPOSES OF THIS CLASS, WE DO NOT CHECK IF THE SET ALREADY CONTAINS A PAIR OF POINTS DUE TO THE EXTREME improbability of this happening*/
public class RandomCircularSet {
	private static final Random gen = new Random();
	private static final int DEFAULT_RADIUS = 1;
	
	int radius;
	ArrayList<Point> setOfPoints;
	
	/*Construct a set of Random points taken from the set enclosed by the unit circle (including the edge)*/
	public RandomCircularSet(int numberOfPoints){
		radius = DEFAULT_RADIUS;
		setOfPoints =  new ArrayList(numberOfPoints);
		for(int i = 0; i < numberOfPoints; i++){
			setOfPoints.add(getRandomPointInSet());//Check if point is already in the set is not implemented due to the unlikeliness
		}
		
	}
	
	private Point getRandomPointInSet(){
		double radians = 2*Math.PI*gen.nextDouble();
		return new Point(gen.nextDouble()*radius*Math.cos(radians), gen.nextDouble()*radius*Math.sin(radians));
	}
	
	public ArrayList<Point> getPointSet(){
		return setOfPoints;
	}
	
	
	public static void main(String[] args){
		System.out.println(new RandomCircularSet(20).getPointSet());
	}

}
