package vertex_average;

import space.ConvexPolytope2D;

public class RatioOfEgesToVerticesOfRandomConvexSets {

	static int numberOfSets = 100000;
	static int numOfPointsPerSet = 5000;
	
	public static void main(String[] args){
		
		StandardDiviationForHull data = new StandardDiviationForHull();
		RandomCircularSet rdmPoints;
		ConvexPolytope2D currentHull;
		double beginTime = System.currentTimeMillis();
		
		System.out.println("Computing set: " + 1 + "/" + numberOfSets);
		System.out.println("TimeRemaining: Not Avaiable...");
		
		
		for(int i = 0; i < numberOfSets; i++){
			rdmPoints = new RandomCircularSet(numOfPointsPerSet);
			currentHull = new ConvexPolytope2D(rdmPoints.getPointSet());
			data.addDataPoint(new HullDataPoint(currentHull.getNumEdges(), currentHull.getNumVertices()));
			if(i%100 == 0 && i != 0){
				System.out.println("Computing set: " + (i +1) + "/" + numberOfSets);
				System.out.println("TimeRemaining: " + (numberOfSets-i)*(System.currentTimeMillis()-beginTime)/i/1000.0 + " seconds");
			}
		}
		
		double standardDeviation = data.getStandardDeviation();
		
		System.out.println("The Standard deviation of the ratio between the number of edges a convex hull vs the number of points in\n"
				+ "in an set selected from the unit circle where:\n"
				+ "number of points per set = " + numOfPointsPerSet 
				+ "\nnumber of sets sampled = " + numberOfSets
				+ "\n\tMEAN NUMBER OF EDGES: " + data.getMean()*numOfPointsPerSet
				+ "\n\tMEAN RATIO OF EDGES: " + data.getMean()
				+"\n\tSTANDARD DEVIATION OF RATIO= " + standardDeviation);
		
		System.out.println("TIME TO COMPLETE = " + (System.currentTimeMillis()-beginTime)/1000.0);
	}
	
}
