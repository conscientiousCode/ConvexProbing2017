package space;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class TestConvexHull2D {

	static final double a = 0.7;
	protected Point[] star = {new Point(2,0), new Point(a, -a), new Point(0,-2), new Point(-a,-a), new Point(-2,0),
										 new Point(-a,a), new Point(0,2), new Point(a,a)};
	protected Point[] triangle = {new Point(2,0), new Point(0,0), new Point(-2,0), new Point(-1,1), new Point(0,2), new Point(1,1)};
	
	ArrayList<Point> pointSet;
	
	@Before
	public void init(){
		pointSet = makeArrayToArrayList(star);
	}
	
	
	@Test
	public void testHullEdgeSetCreation() {
		EdgeSet hull = ConvexHull2D.getHull(pointSet);
		Point s1 = new Point(2,0), s2 = new Point(0,-2), s3 = new Point(-2, 0), s4 = new Point(0,2);
		EdgeSet starHull = new EdgeSet(2);
		starHull.addEdge(s1,s2); starHull.addEdge(s2,s3); starHull.addEdge(s3,s4); starHull.addEdge(s4,s1);
		assertTrue(starHull.equals(hull));
		hull = ConvexHull2D.getHull(makeArrayToArrayList(triangle));
		Point t1 = new Point(2,0), t2 = new Point(-2,0), t3 = new Point(0,2);
		EdgeSet triangleHull = new EdgeSet(2);
		triangleHull.addEdge(t1,t2); triangleHull.addEdge(t2,t3); triangleHull.addEdge(t3,t1);
		assertTrue(triangleHull.equals(hull));
		
		pointSet = new ArrayList();
		pointSet.add(new Point(0,0));
		hull = ConvexHull2D.getHull(pointSet);
		assertTrue(hull.equals(new EdgeSet(2)));
	}
	
	@Test
	public void testRemoveDuplicatePoints(){
		java.util.function.BiFunction<ArrayList<Point>, ArrayList<Point>, Boolean> setsAreEqual = (ArrayList<Point> s1, ArrayList<Point> s2) ->{
			if(s1.size() == s2.size()){
				for(Point p: s1){
					if(!s2.contains(p)){
						return false;
					}
				}
				return true;
			}else{
				return false;
			}
		};
		
		assertTrue(setsAreEqual.apply(pointSet, ConvexHull2D.removeDuplicatePoints(pointSet)));
		ArrayList<Point>  doubleSize = new ArrayList();
		for(Point p : pointSet){
			doubleSize.add(p);
			doubleSize.add(p);
		}
		assertTrue(setsAreEqual.apply(pointSet, ConvexHull2D.removeDuplicatePoints(doubleSize)));
		
	}
	
	@Test
	public void testGetMaximalXSet(){
		pointSet = ConvexHull2D.getMaximalXSet(pointSet);
		assertTrue(pointSet.size() == 1);
		assertTrue((pointSet.get(0)).equals(new Point(2,0)));
	}
	
	@Test
	public void testGetPointWithMaxY(){
		assertTrue(ConvexHull2D.getPointWithMaxY(pointSet).equals(new Point(0,2)));
	}
	
	@Test
	public void testGetPointWithMinY(){
		assertTrue(ConvexHull2D.getPointWithMinY(pointSet).equals(new Point(0,-2)));
	}
	
	@Test
	public void testGetUnitVectorFrom(){
		assertTrue(ConvexHull2D.getUnitVectorFrom(new Point(0,0), new Point(1,0)).equals(new Point(1,0)));
		assertTrue(ConvexHull2D.getUnitVectorFrom(new Point(0,0), new Point(2,0)).equals(new Point(1,0)));
		
		assertTrue(ConvexHull2D.getUnitVectorFrom(new Point(1,0), new Point(0,0)).equals(new Point(-1,0)));
		assertTrue(ConvexHull2D.getUnitVectorFrom(new Point(2,0), new Point(0,0)).equals(new Point(-1,0)));
		
		assertTrue(ConvexHull2D.getUnitVectorFrom(new Point(0,0), new Point(1,1)).equals(new Point(1/Math.sqrt(2), 1/Math.sqrt(2))));
	}
	
	@Test
	public void testGetMostAlignedSet(){
		Point currentPoint = new Point(2,0);
		Point direction = new Point(0,-1);
		pointSet.remove(0);
		pointSet = ConvexHull2D.getMostAlignedSet(pointSet, currentPoint, direction);
		assertTrue(pointSet.size() == 1);
		assertTrue(pointSet.get(0).equals(new Point(0,-2)));
	}
	
	@Test
	public void testGetVertexFarthestFrom(){
		assertTrue(ConvexHull2D.getVertexFarthestFrom(pointSet, pointSet.get(0)).equals(new Point(-2,0)));
		assertTrue(ConvexHull2D.getVertexFarthestFrom(pointSet, pointSet.get(4)).equals(new Point(2,0)));
		assertTrue(ConvexHull2D.getVertexFarthestFrom(pointSet, pointSet.get(2)).equals(new Point(0,2)));
	}
	
	private static ArrayList<Point> makeArrayToArrayList(Point[] array){
		ArrayList<Point> arrayList = new ArrayList();
		for(Point p : array){
			arrayList.add(p);
		}
		return arrayList;
	}

}
