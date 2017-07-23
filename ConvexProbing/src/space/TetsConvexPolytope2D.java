package space;

import static java.lang.Math.PI;
import static org.junit.Assert.*;

import org.junit.Test;

public class TetsConvexPolytope2D {

	public Point p1 = new Point(new double[] { 1, 0 }), p2 = new Point(new double[] { 0, 1 }),
			p3 = new Point(new double[] { -1, 0 }), p4 = new Point(new double[] { 0, -1 });

	Point q1 = getUnitCirclePoint(PI * 1.0 / 4), q2 = getUnitCirclePoint(PI * 3.0 / 4),
			q3 = getUnitCirclePoint(PI * 5.0 / 4), q4 = getUnitCirclePoint(PI * 7.0 / 4);

	private static Point getUnitCirclePoint(double t) {
		return new Point(new double[] { Math.cos(t), Math.sin(t) });
	}

	@Test
	public void TestQuadrant() {

		assertTrue(ConvexPolytope2D.quadrant(p1) == 4);
		assertTrue(ConvexPolytope2D.quadrant(p2) == 1);
		assertTrue(ConvexPolytope2D.quadrant(p3) == 2);
		assertTrue(ConvexPolytope2D.quadrant(p4) == 3);

		assertTrue(ConvexPolytope2D.quadrant(q1) == 1);
		assertTrue(ConvexPolytope2D.quadrant(q2) == 2);
		assertTrue(ConvexPolytope2D.quadrant(q3) == 3);
		assertTrue(ConvexPolytope2D.quadrant(q4) == 4);
	}

	@Test
	public void TestPointOrder() {
		TestQuadrant();

		assertTrue(ConvexPolytope2D.pointOrder(q1, q2) < 0);
		assertTrue(ConvexPolytope2D.pointOrder(q2, q1) > 0);

		assertTrue(ConvexPolytope2D.pointOrder(q2, q3) < 0);
		assertTrue(ConvexPolytope2D.pointOrder(q3, q2) > 0);

		assertTrue(ConvexPolytope2D.pointOrder(q3, q4) < 0);
		assertTrue(ConvexPolytope2D.pointOrder(q4, q3) > 0);

		assertTrue(ConvexPolytope2D.pointOrder(q1, q3) < 0);
		assertTrue(ConvexPolytope2D.pointOrder(q1, q4) < 0);
		assertTrue(ConvexPolytope2D.pointOrder(q2, q4) < 0);

		assertTrue(ConvexPolytope2D.pointOrder(q1, getUnitCirclePoint(PI * (3.0 / 8.0))) < 0);
		assertTrue(ConvexPolytope2D.pointOrder(getUnitCirclePoint(PI * (3.0 / 8.0)), q1) > 0);

		assertTrue(ConvexPolytope2D.pointOrder(q2, getUnitCirclePoint(PI * (7.0 / 8.0))) < 0);
		assertTrue(ConvexPolytope2D.pointOrder(getUnitCirclePoint(PI * (7.0 / 8.0)), q2) > 0);

		assertTrue(ConvexPolytope2D.pointOrder(q3, getUnitCirclePoint(PI * (11.0 / 8.0))) < 0);
		assertTrue(ConvexPolytope2D.pointOrder(getUnitCirclePoint(PI * (11.0 / 8.0)), q3) > 0);

		assertTrue(ConvexPolytope2D.pointOrder(q4, getUnitCirclePoint(PI * (15.0 / 8.0))) < 0);
		assertTrue(ConvexPolytope2D.pointOrder(getUnitCirclePoint(PI * (15.0 / 8.0)), q4) > 0);
	}

	@Test
	public void TestContructorOfRandomConvexShape() {
		ConvexPolytope2D shape = ConvexPolytope2D.newRandomPolytope(10);
		for(RealPoint[] edge : shape.edges){
			assertTrue(shape.vertices.contains(edge[0]));
			assertTrue(shape.vertices.contains(edge[1]));
		}

	}
	@Test
	public void testContainsPoint(){
		ConvexPolytope2D ellipsePolytope = ConvexPolytope2D.newRandomPolytope(20);
		
		assertFalse(ellipsePolytope.containsPoint(new Point(201,201)));
		Point dir = ellipsePolytope.vertices.get(1).subtract(ellipsePolytope.vertices.get(0));
		Point pointInPolytope = ellipsePolytope.vertices.get(0).add(dir.scaleBy(0.5));
		assertTrue(ellipsePolytope.containsPoint(pointInPolytope));
	}

}
