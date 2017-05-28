package space;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.function.*;

public class TestEdgeSet {
	
	Point p1 = new Point((new double[]{1.0,0.0}));
	Point p2 = new Point((new double[]{0.0,1.0}));
	Point p3 = new Point((new double[]{1.0,1.0}));
	Point[] p1p2 = {p1, p2}, p1p3 = {p1,p3}, p2p3 = {p2,p3};
	Point[][] edgesInit = {p1p2, p1p3, p2p3};
	EdgeSet edges;
	
	@Before
	public void init(){
		edges = new EdgeSet(edgesInit);
	}

	@Test
	public void testSearchForANDAdd() {
		//Add is dependent on Search for.
		int indexOf12 = edges.searchFor(p1p2);
		int indexOf13 = edges.searchFor(p1p3);
		int indexOf23 = edges.searchFor(p2p3);
		
		//System.out.println(indexOf12 + "\t" + indexOf13 + "\t" + indexOf23);
		assertTrue(indexOf12 > -1 && indexOf13 > -1 && indexOf23 > -1); // Making sure that
		assertTrue(indexOf12 != indexOf13);
		assertTrue(indexOf12 != indexOf23);
		assertTrue(indexOf13 != indexOf23);
		
		RealPoint[] inSet12 = edges.edges.get(indexOf12);
		RealPoint[] inSet13 = edges.edges.get(indexOf13);
		RealPoint[] inSet23 = edges.edges.get(indexOf23);
		
		BiFunction<RealPoint[], RealPoint[], Boolean> compareEdges = (RealPoint[] e1, RealPoint[] e2) ->{
			return(e1[0].equals(e2[0]) && e1[1].equals(e2[1]));
		};
		
		assertTrue(compareEdges.apply(inSet12, new Point[]{p1,p2}));
		assertTrue(compareEdges.apply(inSet13, new Point[]{p3,p1}));
		assertTrue(compareEdges.apply(inSet23, new Point[]{p3,p2}));
		
	}

}
