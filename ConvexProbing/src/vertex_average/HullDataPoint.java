package vertex_average;

public class HullDataPoint {

	public int numOfEdges;
	public int numVertices;
	
	public HullDataPoint(int numOfEdges, int numVertices){
		if(numOfEdges< 0 || numVertices < 0){
			throw new IllegalArgumentException("ERROR NEGATIVE!  numOfEdges: " + numOfEdges + "\tnumVertices: " + numVertices);
		}
		
		this.numOfEdges = numOfEdges;
		this.numVertices = numVertices;
	}
	
	
	public double edge_pointRatio(){
		return ((double)numOfEdges)/numVertices;
	}
}
