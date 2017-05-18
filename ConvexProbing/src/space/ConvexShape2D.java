package space;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


public class ConvexShape2D implements ConvexShape{
	
	private static final Random rdm = new Random();
	
	
	private Set<Point> vertices = new HashSet<Point>();
	

	@Override
	public Iterator<Point> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumVertices() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasVertex(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasEdgeBetween(Point p1, Point p2) {
		// TODO Auto-generated method stub
		return false;
	}

}
