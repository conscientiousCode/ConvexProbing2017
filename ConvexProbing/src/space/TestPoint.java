package space;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.math3.fraction.*;

public class TestPoint {

	static final double[] p1Init = {1.0,2.0,3.0};
	static final double[] p2Init = {3.0,2.0,1.0};
	static final double[] zero = {0,0,0};
	static final double[] one = {1,1, 1};
	static final Point ORIGIN = new Point(zero);
	static final Point ONE = new Point(one);
	
	Point p1;
	Point p2;
	Point p1Copy;
	Point p2Copy;
	
	@Before
	public void Initialization(){
		p1 = new Point(p1Init);
		p1Copy = new Point(1,2,3);
		p2 = new Point(p2Init);
		p2Copy = new Point(p2Init);
		equals();
	}
	
	//Tested at each initialization and the end of each test, Points should be immutable
	@Test
	public void equals(){
		assertTrue(p1.equals(p1));
		assertTrue(p1.equals(p1Copy));
		assertTrue(p1Copy.equals(p1));
		
		assertFalse(p1.equals(p2));
		assertFalse(p2.equals(p1));
	}
	
	@Test
	public void testGetDimension() {
		assertTrue(p1.getDimension() == 3);
		
		Point tPoint  = new Point(new double[]{1.0});
		assertTrue(tPoint.getDimension() == 1);
		
		equals();
	}
	
	@Test
	public void testGetAxisValue(){
		for(int i = 0; i < p1Init.length; i++){
			assertTrue(ORIGIN.getAxisValue(i) == 0.0);
			assertTrue(p1.getAxisValue(i) == p1Init[i]);
			assertTrue(p2.getAxisValue(i) == p2Init[i]);
		}
		equals();
	}
	
	@Test
	public void testDot(){
		Fraction dotValueP1P2 = Fraction.ZERO;
		for(int i = 0; i < p1Init.length; i++){
			dotValueP1P2 = dotValueP1P2.add((new Fraction(p1Init[i])).multiply((new Fraction(p2Init[i]))));
		}
		
		assertTrue(isClose(dotValueP1P2.doubleValue(),10));// Computed dot product by hand
		assertTrue(isClose(dotValueP1P2.doubleValue(),p1.dot(p2)));
		assertTrue(isClose(dotValueP1P2.doubleValue(),p2.dot(p1)));
		
		assertTrue(p1.dot(ORIGIN) == 0.0);
		assertTrue(isClose(p1.dot(ONE), 6)); // Computed by hand, if p1 changes expect this test to fail
		
		equals();
	}
	
	private boolean isClose(double p1, double p2){
		return Math.abs(p1-p2)< 0.00000000000001;
	}

	@Test
	public void testHaveSameDimension(){
		final int dimension = 3;
		final int MAX_MAGNITUDE = 100;
		Point[] points = new Point[10];
		java.util.Random rdm = new java.util.Random();
		double[] generatedPoint;
		for(int i = 0; i< points.length; i++){
			generatedPoint = new double[dimension];
			for(int j = 0; j < dimension; j++){
				generatedPoint[j] = rdm.nextDouble()*MAX_MAGNITUDE;
			}
			points[i] = new Point(generatedPoint);
		}
		
		assertTrue(Point.haveSameDimension(points));
		
		points[0] = new Point(new double[]{1});
		assertFalse(Point.haveSameDimension(points));
		
		points = null;
		assertTrue(Point.haveSameDimension(points));//Empty set is true by default
		
		points = new Point[0];
		assertTrue(Point.haveSameDimension(points));
	}

	@Test
	public void testAdd(){
		assertTrue(p1.add(ORIGIN).equals(p1));
		assertTrue(ORIGIN.add(p1).equals(p1));
		
		assertFalse(p1.add(p1).equals(p1));
		assertTrue(p1.add(p2).equals(p2.add(p1)));
		assertTrue(p1.add(p1).equals(p1.scaleBy(2)));
		
	}
	
	@Test
	public void testSubtract(){
		assertTrue(p1.subtract(p1).equals(ORIGIN));
		assertTrue(p1.subtract(ONE).equals(new Point(0,1,2)));
		assertTrue(ONE.subtract(p1).equals(new Point(0,-1,-2)));
	}
	
	@Test
	public void testGetUnitVector(){
		//System.out.println(p1.getUnitVector().getMagnitude());
		assertTrue(isClose(p1.getUnitVector().getMagnitude(), 1.0));
		assertTrue(isClose(p2.getUnitVector().getMagnitude(), 1.0));
		assertTrue(isClose(ONE.getUnitVector().getMagnitude(), 1.0));
		try{
			ORIGIN.getUnitVector().getMagnitude();
			fail();
		}catch(ArithmeticException e){}
	}
	
}
