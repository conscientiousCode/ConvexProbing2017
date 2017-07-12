package space;

import java.util.Random;

public class RandomEllipse {
	private static final Random rdm = new Random();
	private static final int MAX_BOUND = 200;
	
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
