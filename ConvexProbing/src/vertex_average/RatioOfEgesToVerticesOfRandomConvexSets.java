package vertex_average;

import space.ConvexPolytope2D;

public class RatioOfEgesToVerticesOfRandomConvexSets {

	static int numberOfSets = 100000;
	static int[] pointNumStages = {100000};//{10,20,50,100,150,200,300,400,500,1000,1500,2000,2500,5000,10000,15000,20000};
	static int numOfPointsPerSet = 0;
	static int numberOfStages = pointNumStages.length;
	
	public static void main(String[] args){
		
		
		StringBuilder log = new StringBuilder();
		for (int i = 0; i < numberOfStages; i++) {
			numOfPointsPerSet = pointNumStages[i];
			StandardDeviationForHullRaw data = new StandardDeviationForHullRaw(numberOfSets);
			RandomCircularSet rdmPoints;
			ConvexPolytope2D currentHull;
			double beginTime = System.currentTimeMillis();
			System.out.println("********STAGE " + (i+1) + "/" + numberOfStages);
			System.out.println("Computing set: " + 1 + "/" + numberOfSets);
			System.out.println("TimeRemaining: Not Avaiable...");

			for (int j = 0; j < numberOfSets; j++) {
				rdmPoints = new RandomCircularSet(numOfPointsPerSet);
				currentHull = new ConvexPolytope2D(rdmPoints.getPointSet());
				data.addDataPoint(new HullDataPoint(currentHull.getNumEdges(), currentHull.getNumVertices()));
				if (j % 1000 == 0 && j != 0) {
					System.out.println("Computing set: " + (j + 1) + "/" + numberOfSets);
					System.out.println("Time Remaining of current stage (stage " + (i+1) + "/" + numberOfStages +"): "
							+ (numberOfSets - j) * (System.currentTimeMillis() - beginTime) / j / 60000.0 + " minutes");
				}
			}

			double standardDeviation = data.getStandardDeviation();
			/*Use with StandardDiviationForHull
			System.out.println(
					"The Standard deviation of the ratio between the number of edges a convex hull vs the number of points in\n"
							+ "in an set selected from the unit circle where:\n" + "number of points per set = "
							+ numOfPointsPerSet + "\nnumber of sets sampled = " + numberOfSets
							+ "\n\tMEAN NUMBER OF EDGES: " + data.getMean() * numOfPointsPerSet
							+ "\n\tMEAN RATIO OF EDGES: " + data.getMean() + "\n\tSTANDARD DEVIATION OF RATIO= "
							+ standardDeviation);

			System.out.println("TIME TO COMPLETE = " + (System.currentTimeMillis() - beginTime) / 60000.0 + "minutes");
			
			log.append("Number of Sets = " + numberOfSets + " Points Per Set = " + numOfPointsPerSet
					+ "\n\tMean number of edges = " + data.getMean() * numOfPointsPerSet
					+ "\n\tMean ratio of edges = " + data.getMean() + " (+/-) " + standardDeviation + "\n\n");
			*/
			
			System.out.println(
					"The Standard deviation of the ratio between the number of edges a convex hull vs the number of points in\n"
							+ "in an set selected from the unit circle where:\n" + "number of points per set = "
							+ numOfPointsPerSet + "\nnumber of sets sampled = " + numberOfSets
							+ "\n\tMEAN NUMBER OF EDGES: " + data.getMean()
						    + "\n\tSTANDARD DEVIATION= "
							+ standardDeviation);

			System.out.println("TIME TO COMPLETE = " + (System.currentTimeMillis() - beginTime) / 60000.0 + "minutes");
			
			log.append("Number of Sets = " + numberOfSets + " Points Per Set = " + numOfPointsPerSet
					+ "\n\tMean number of edges = " + data.getMean() + " (+/-) " + standardDeviation + "\n\n");
		}
		System.out.println(log);
	}
	
}
