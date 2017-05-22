package space;

import org.apache.commons.math3.fraction.Fraction;

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

	/*
	 * Treat points as vectors and return their dot product
	 * */
	@Override
	public Fraction dot(RealPoint otherPoint) {
		if(getDimension() != otherPoint.getDimension()){
			throw new IllegalArgumentException("Point dimensions mismatch: this.getDimension =  " + this.getDimension() + " otherPoint.getDimension() = " + otherPoint.getDimension() );
		}
		Fraction dotSum = Fraction.ZERO;
		for(int i = 0; i < getDimension(); i++){
			dotSum = dotSum.add(vector[i].multiply(otherPoint.getAxisValue(i)));
		}
		return dotSum;
	}

	/*
	 * Treat this point as a vector and return a new point has each axis value scaled by 'scalar'
	 * */
	@Override
	public RealPoint scaleBy(Fraction scalar) {
		Fraction[] newVector = new Fraction[vector.length];
		for(int i = 0; i < vector.length; i++){
			newVector[i] = vector[i].multiply(scalar);
		}
		return (new Point(newVector)); // May want to optimize this call later on so that we do not copy values twice.
	}
	

}
