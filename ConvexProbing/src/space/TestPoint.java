package space;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.math3.fraction.*;

public class TestPoint {

	static final double[] p1Init = {1.0,2.0,3.0};
	static final double[] p2Init = {3.0,2.0,1.0};
	static final Fraction[] zero = {Fraction.ZERO, Fraction.ZERO, Fraction.ZERO};
	static final Fraction[] one = {Fraction.ONE,Fraction.ONE,Fraction.ONE};
	static final Point ORIGIN = new Point(zero);
	static final Point ONE = new Point(one);
	
	Point p1;
	Point p2;
	Point p1Copy;
	Point p2Copy;
	
	@Before
	public void Initialization(){
		p1 = new Point(p1Init);
		p1Copy = new Point(p1Init);
		p2 = new Point(p2Init);
		p2Copy = new Point(p2Init);
		equals();
	}
	
	//Tested at each initialization and the end of each test, Points should be immutable
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
			assertTrue(ORIGIN.getAxisValue(i).equals(Fraction.ZERO));
			assertTrue(p1.getAxisValue(i).equals(new Fraction(p1Init[i])));
			assertTrue(p2.getAxisValue(i).equals(new Fraction(p2Init[i])));
		}
		equals();
	}
	
	@Test
	public void testDot(){
		Fraction dotValueP1P2 = Fraction.ZERO;
		for(int i = 0; i < p1Init.length; i++){
			dotValueP1P2 = dotValueP1P2.add((new Fraction(p1Init[i])).multiply((new Fraction(p2Init[i]))));
		}
		
		assertTrue(dotValueP1P2.equals(new Fraction(10)));// Computed dot product by hand
		assertTrue(dotValueP1P2.equals(p1.dot(p2)));
		assertTrue(dotValueP1P2.equals(p2.dot(p1)));
		
		assertTrue(p1.dot(ORIGIN).equals(Fraction.ZERO));
		
		assertTrue(p1.dot(ONE).equals(new Fraction(6))); // Computed by hand, if p1 changes expect this test to fail
		
		equals();
	}
	

}
