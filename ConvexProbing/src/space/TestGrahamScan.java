package space;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestGrahamScan {
	
	static final double a = 0.7;
	protected Point[] star = {new Point(2,0), new Point(a, -a), new Point(0,-2), new Point(-a,-a), new Point(-2,0),
										 new Point(-a,a), new Point(0,2), new Point(a,a)};
	protected Point[] triangle = {new Point(2,0), new Point(0,0), new Point(-2,0), new Point(-1,1), new Point(0,2), new Point(1,1)};
	ArrayList<Point> starList, triangleList;
	
	@Before
	public void init(){
		starList = new ArrayList();
		for(Point p : star){
			starList.add(p);
		}
		triangleList = new ArrayList();
		for(Point p : triangle){
			triangleList.add(p);
		}
	}

	@Test
	public void testGetBottomLeftPoint() {
		ArrayList<Point> points = new ArrayList();
		points.add(new Point(1,1));
		points.add(new Point(1,-3));
		points.add(new Point(0,1));
		points.add(new Point(5,5));
		points.add(new Point(-7,-2));
		points.add(new Point(0,-3));
		points.add(new Point(0,0));
		
		assertTrue(new Point(0,-3).equals(GrahamScan.getBottomLeftPoint(points)));
	}
	
	@Test
	public void testHullConstruction(){
		EdgeSet gHull = GrahamScan.getHull(starList);
		EdgeSet cHull = ConvexHull2D.getHull(starList);
		assertTrue(gHull.equals(cHull));
		
		gHull = GrahamScan.getHull(triangleList);
		cHull = ConvexHull2D.getHull(triangleList);
		assertTrue(gHull.equals(cHull));
		
		ArrayList<Point> rdmSet = new ArrayList();
		RandomEllipse rdm = new RandomEllipse();
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 100 ; j++){
				rdmSet.add(new Point(rdm.getRandomPoint()));
			}
		gHull = GrahamScan.getHull(rdmSet);
		cHull = ConvexHull2D.getHull(rdmSet);
		assertTrue(gHull.equals(cHull));
		rdm = new RandomEllipse();
	}

}
