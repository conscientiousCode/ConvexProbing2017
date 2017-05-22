package space;

public interface EdgeSetInterface {

	/**
	 * Adds an undirected edge with the end points p1, p2 to the set, and returns true if successful and the edge does not already exist in the set
	 * @param p1 the fist end point
	 * @param p2 the second end point
	 * @return true if this edge is not already in the set and is successfully added
	 */
	public boolean addEdge(RealPoint p1, RealPoint p2);
	
	/**
	 * Checks in the undirected edge specified by the end points p1, p2 exists in the set
	 * 
	 * @param p1 the first end point
	 * @param p2 the second end point
	 * @return true if the edge is already a part of the set
	 */
	public boolean containsEdge(RealPoint p1, RealPoint p2);
	
	/**
	 * removes the undirected edge with the end points p1, p2 from the set
	 * 
	 * @param p1 the first end point
	 * @param p2 the second end point
	 * @return true if the edge existed in the set and was successfully removed, false otherwise
	 */
	public boolean removeEdge(RealPoint p1, RealPoint p2);
}
