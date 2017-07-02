package space;

public interface ConvexShape extends Iterable<RealPoint[]>{
	
	public int getNumVertices();
	public boolean hasVertex(Point point);
	public boolean hasEdgeBetween(Point p1, Point p2);
	
	public boolean addVertex(Point point);
	public boolean removeVertex(Point point);
	
	
}
