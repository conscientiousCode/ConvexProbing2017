package space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;


public class EdgeSet implements EdgeSetInterface{

	
	protected static final Comparator compareByAxis = new AxisComparator();
	
	
	
	private ArrayList<RealPoint[]> edges;
	private int dimensionOfEdges;
	
	public EdgeSet(int dimensionOfEdges){
		this.dimensionOfEdges = dimensionOfEdges;
		edges = new ArrayList<RealPoint[]>(20);
	}
	
	public EdgeSet(RealPoint[][] pointPairs){
		for(RealPoint[] edge: pointPairs){
			if(edge.length != 2){
				throw new IllegalArgumentException("At least one sub array does not have exactly two elements (more or less than 2 points when trying to represent an edge)");
			}
			addEdge(edge[0], edge[1]);
		}
	}
	
	@Override
	public boolean addEdge(RealPoint p1, RealPoint p2){
		RealPoint[] edge = orderByMagnitude(p1,p2);
		int searchResult = searchFor(edge);
		if(searchResult > -1){
			return edges.add(edge);
		}
		return false;
	}
	
	@Override
	public boolean containsEdge(RealPoint p1, RealPoint p2){
		return false;
	}

	@Override
	public boolean removeEdge(RealPoint p1, RealPoint p2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*The Values of the points will always be stored in descending order of magnitude*/
	private static RealPoint[] orderByMagnitude(RealPoint p1, RealPoint p2){
		RealPoint[] ordered = new RealPoint[2];
		double magnitudeDiff = p1.getMagnitude() - p2.getMagnitude();
		if(magnitudeDiff > 0){
			ordered[0] = p1;
			ordered[1] = p2;
		}else if(magnitudeDiff < 0){
			ordered[0] = p2;
			ordered[1] = p1;
		}else{//Equivalent magnitude
			int comparison = compareByAxis.compare(p1, p2);
			if(comparison > 0){
				ordered[0] = p1;
				ordered[1] = p2;
			}else if(comparison < 0){
				ordered[0] = p2;
				ordered[1] = p1;
			}else{//Endpoint is start point
				ordered[0] = p1;
				ordered[1] = p2;
			}
		}
		return ordered;
	}
	
	protected int searchFor(RealPoint[] edge){
		edge = orderByMagnitude(edge[0], edge[1]);
		for(int i = 0; i < edges.size(); i++){
			if((compareByAxis.compare(edge[0], edges.get(i)[0]) == 0) && (compareByAxis.compare(edge[0], edges.get(i)[0]) == 0)){
				return i;
			}
		}
		return -1;
	}
	
	private static class AxisComparator implements Comparator<RealPoint>{

		@Override
		public int compare(RealPoint p1, RealPoint p2) {
			for(int i = 0; i < p1.getDimension(); i++){
				if(p1.getAxisValue(i).compareTo(p2.getAxisValue(i)) != 0){
					return p1.getAxisValue(i).compareTo(p2.getAxisValue(i));
				}
			}
			return 0;
		}
		
	}
	
}
