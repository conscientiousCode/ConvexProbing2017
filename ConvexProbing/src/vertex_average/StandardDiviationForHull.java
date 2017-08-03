package vertex_average;

import java.util.ArrayList;

public class StandardDiviationForHull {
	double mean;
	double totalSumOfEdgeRatios;
	ArrayList<Double> edgeRatios;
	
	public StandardDiviationForHull(int numberOfDataPoints){
		edgeRatios = new ArrayList(numberOfDataPoints);
		totalSumOfEdgeRatios = 0;
		mean = 0;
	}
	
	public void addDataPoint(HullDataPoint data){
		if(totalSumOfEdgeRatios + data.edge_pointRatio() == totalSumOfEdgeRatios){
			throw new ArithmeticException("totalSumOfData is so large compared to edge_pointRatio that adding nolonger changes the value");
		}else{
			totalSumOfEdgeRatios += data.edge_pointRatio();
			edgeRatios.add(data.edge_pointRatio());
		}
	}
	
	private void computeMean(){
		mean = totalSumOfEdgeRatios/edgeRatios.size();
	}
	
	public double getStandardDeviation(){
		System.out.println("Calculating Standard Deviation...");
		computeMean();
		double sumOfDifferences = 0;
		
		for(double ratios : edgeRatios){
			sumOfDifferences += Math.pow(ratios-mean,2);
		}
		
		/*System.out.println(totalSumOfEdgeRatios);
		System.out.println(edgeRatios.size());*/
		
		return Math.sqrt(sumOfDifferences/(edgeRatios.size() -1));
	}
	
	public double getMean(){
		computeMean();
		return mean;
	}
	
	
	
}
