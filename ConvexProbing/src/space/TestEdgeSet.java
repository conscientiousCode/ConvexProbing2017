package space;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
	public void testSearchFor() {
		
	}

}
