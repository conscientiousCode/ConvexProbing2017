package space;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.fraction.BigFraction;

public class Point implements RealPoint{
	
	Fraction[] vector;
	
	public Point(double[] point){
		if(point == null){
			throw new NullPointerException("It does not make sense to have a point without any dimension");
		}
		vector = new Fraction[point.length];
		for(int i = 0; i < point.length; i++){
			vector[i] = new Fraction(point[i]);
		}
	}
	
	public Point(Fraction[] point){
		if(point == null){
			throw new NullPointerException("It does not make sense to have a point without any dimension");
		}
		vector = new Fraction[point.length];
		for(int i = 0; i < point.length; i++){
			vector[i] = new Fraction(point[i].getNumerator(), point[i].getDenominator());
		}
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
			if(!vector[i].equals(otherPoint.getAxisValue(i))){
				return false;
			}
		}
		return true;
	}

	/*
	 * Returns the value of an axis
	 * */
	@Override
	public Fraction getAxisValue(int axis) {
		return vector[axis]; // May throw index out of bounds exception
	}
	
	/*adds p to this point as though they are vectors and returns the result as a new object*/
	public Point add(RealPoint p){
		Point.haveSameDimension(this, p);//If they do not have the same dimension, then an IllegalArgumentException is thrown.
		Fraction[] newPoint = new Fraction[this.getDimension()];
		for(int i = 0; i < this.getDimension(); i++){
			newPoint[i] = p.getAxisValue(i).add(this.getAxisValue(i));
		}
		return new Point(newPoint);
	}

	public Point subtract(RealPoint p){
		return add(p.scaleBy(new Fraction(-1)));
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
			dotSum += vector[i].doubleValue()*(otherPoint.getAxisValue(i).doubleValue());
		}
		return dotSum;
	}

	/*
	 * Treat this point as a vector and return a new point has each axis value scaled by 'scalar'
	 * */
	@Override
	public Point scaleBy(Fraction scalar) {
		Fraction[] newVector = new Fraction[vector.length];
		for(int i = 0; i < vector.length; i++){
			newVector[i] = vector[i].multiply(scalar);
		}
		return (new Point(newVector)); // May want to optimize this call later on so that we do not copy values twice.
	}
	
	@Override
	public double getMagnitude(){
		return Math.sqrt(dot(this));
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder(getDimension()*2 -1 + 2);
		builder.append('[');
		for(Fraction frac : vector){
			builder.append(frac.doubleValue() + ", ");
		}
		builder.append(']');
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
