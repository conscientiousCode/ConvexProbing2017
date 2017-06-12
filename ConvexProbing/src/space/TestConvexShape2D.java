package space;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestConvexShape2D {
	
	public Point p1 = new Point(new double[]{1,0}), p2 = new Point(new double[]{0,1}), 
			     p3 = new Point(new double[]{-1, 0}), p4 = new Point(new double[]{0,-1});
	
	Point q1 = new Point(new double[]{1,1}), q2 = new Point(new double[]{-1,1}),
		  q3 = new Point(new double[]{-1,-1}), q4 = new Point(new double[]{1,-1});

	@Test
	public void TestQuadrant(){
		
		assertTrue(ConvexShape2D.quadrant(p1) == 4);
		assertTrue(ConvexShape2D.quadrant(p2) == 1);
		assertTrue(ConvexShape2D.quadrant(p3) == 2);
		assertTrue(ConvexShape2D.quadrant(p4) == 3);
		
		
		assertTrue(ConvexShape2D.quadrant(q1) == 1);
		assertTrue(ConvexShape2D.quadrant(q2) == 2);
		assertTrue(ConvexShape2D.quadrant(q3) == 3);
		assertTrue(ConvexShape2D.quadrant(q4) == 4);
	}
	
	@Test
	public void TestPointOrder(){
		TestQuadrant();
		
		assertTrue(ConvexShape2D.pointOrder(q1, q2) < 0);
		assertTrue(ConvexShape2D.pointOrder(q2, q1) > 0);
		
		assertTrue(ConvexShape2D.pointOrder(q2, q3) < 0);
		assertTrue(ConvexShape2D.pointOrder(q3, q2) > 0);
		
		assertTrue(ConvexShape2D.pointOrder(q3, q4) < 0);
		assertTrue(ConvexShape2D.pointOrder(q4, q3) > 0);
		
		assertTrue(ConvexShape2D.pointOrder(q1, q3) < 0);
		assertTrue(ConvexShape2D.pointOrder(q1, q4) < 0);
		assertTrue(ConvexShape2D.pointOrder(q2, q4) < 0);
		
		
		
	}
	
}