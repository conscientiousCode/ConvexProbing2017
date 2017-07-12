package space;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.fraction.BigFraction;

public class Point implements RealPoint{
	
	private static final double INEQUALITY_THRESHOLD = 0.0000000000001;
	
	double[] vector;
	
	public Point(double... point){
		if(point == null){
			throw new NullPointerException("It does not make sense to have a point without any dimension");
		}
		vector = point;
	}

	/*
	 * How many different axis there are in this space
	 * */
	@Override
	public int getDimension() {
		return vector.length;
	}
	
	/*
	 * Two points are equal iff they have the same dimension, and the same value for each axis
	 * */
	@Override
	public boolean equals(RealPoint otherPoint) {
		if((getDimension() != otherPoint.getDimension())){
			return false;
		}
		for(int i = 0; i < vector.length; i++){
			if(Math.abs(vector[i] - otherPoint.getAxisValue(i)) > INEQUALITY_THRESHOLD){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Point)){
			return false;
		}else{
			return this.equals((Point)obj);
		}
	}

	/*
	 * Returns the value of an axis
	 * */
	@Override
	public double getAxisValue(int axis) {
		return vector[axis]; // May throw index out of bounds exception
	}
	
	/*adds p to this point as though they are vectors and returns the result as a new object*/
	public Point add(RealPoint p){
		Point.haveSameDimension(this, p);//If they do not have the same dimension, then an IllegalArgumentException is thrown.
		double [] newPoint = new double[this.getDimension()];
		for(int i = 0; i < this.getDimension(); i++){
			newPoint[i] = p.getAxisValue(i) + this.getAxisValue(i);
		}
		return new Point(newPoint);
	}

	
	public Point subtract(RealPoint p){
		return add(p.negate());
	}
	
	
	public Point negate(){
		double[] newVector = new double[vector.length];
		for(int i = 0; i < vector.length; i++){
			newVector[i] = -vector[i];
		}
		return new Point(newVector);
	}
	
	/*
	 * Treat points as vectors and return their dot product
	 * */
	@Override
	public double dot(RealPoint otherPoint) {
		if(getDimension() != otherPoint.getDimension()){
			throw new IllegalArgumentException("Point dimensions mismatch: this.getDimension =  " + this.getDimension() + " otherPoint.getDimension() = " + otherPoint.getDimension());
		}
		/*Had to use a big fraction here because of denominator overflow causes an exception...*/
		double dotSum = 0;
		BigFraction f1, f2;
		for(int i = 0; i < getDimension(); i++){
			dotSum += vector[i]*(otherPoint.getAxisValue(i));
		}
		return dotSum;
	}

	@Override
	public Point scaleBy(double scalar){
		double[] newVector = new double[vector.length];
		for(int i = 0; i < vector.length; i++){
			newVector[i] = vector[i]*scalar;
		}
		return (new Point(newVector)); // May want to optimize this call later on so that we do not copy values twice.
	}
	
	@Override
	public double getMagnitude(){
		return Math.sqrt(dot(this));
	}
	/*This method is broken by java rounding errors*/
	public Point getUnitVector(){
		double magnitude = this.getMagnitude();
		if(magnitude == 0.0){
			throw new ArithmeticException("Vector is the zero vector");
		}
		return this.scaleBy(1/this.getMagnitude());
	}
	
	public boolean isZeroVector(){
		for(double v : vector){
			if(v != 0.0){
				return false;
			}
		}
		return true;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder(getDimension()*2 -1 + 2);
		builder.append('[');
		for(int i = 0; i < vector.length; i++){
			if(i != vector.length -1)
				builder.append(vector[i] + ", ");
			else
				builder.append(vector[i] + "]");
		}
		return builder.toString();
	}
	
	/*The empty set is true by default*/
	public static boolean haveSameDimension(RealPoint... points){
		if(points == null || points.length == 0){//if it is an empty set
			return true;
		}else{
			int dimension = points[0].getDimension();
			for(RealPoint p : points){
				if(p.getDimension() != dimension){
					return false;
				}
			}
		}
		return true;
	}

}
