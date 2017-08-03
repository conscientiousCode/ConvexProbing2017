package vertex_average;

import java.util.ArrayList;

public class StandardDeviationForHullRaw {
	long sumOfEdges;
	ArrayList<Integer> edgeNumbers;
	
	public StandardDeviationForHullRaw(int numberOfDataPoints){
		edgeNumbers = new ArrayList(numberOfDataPoints);
		sumOfEdges = 0;
	}
	
	public void addDataPoint(HullDataPoint data){
		sumOfEdges += data.numOfEdges;
		edgeNumbers.add(data.numOfEdges);
	}
	
	public double getMean(){
		return ((double)sumOfEdges)/edgeNumbers.size();
	}
	
	public double getStandardDeviation(){
		double mean = getMean();
		double sumOfDifferencesSquared = 0;
		for(Integer v : edgeNumbers){
			sumOfDifferencesSquared += Math.pow(v - mean, 2);
		}
		
		return Math.sqrt(sumOfDifferencesSquared/(edgeNumbers.size()-1));
	}
}
