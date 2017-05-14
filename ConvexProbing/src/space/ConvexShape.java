package space;

public interface ConvexShape extends Iterable<Point>{
	
	public int getNumVertices();
	public boolean hasVertex(Point point);
	public boolean hasEdgeBetween(Point p1, Point p2);
	
	
}
